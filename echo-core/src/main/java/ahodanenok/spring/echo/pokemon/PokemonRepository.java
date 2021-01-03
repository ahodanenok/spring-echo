package ahodanenok.spring.echo.pokemon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.validation.DataBinder;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PokemonRepository implements ResourceLoaderAware, EnvironmentAware {

    private List<Pokemon> pokemons;

    private Environment environment;
    private ResourceLoader resourceLoader;
    private PropertyPlaceholderHelper placeholderHelper;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private void init() {
        this.placeholderHelper = new PropertyPlaceholderHelper("@", "@");
        load();
    }

    public Pokemon getRandom() {
        if (pokemons.isEmpty()) {
            throw new IllegalStateException("No pokemons!");
        }

        int idx = (int) Math.floor(Math.random() * pokemons.size());
        return pokemons.get(idx);
    }

    public void add(Pokemon pokemon) {

    }

    private void load() {
        pokemons = new ArrayList<>();
        Resource resource = getDataResource();
        if (!resource.exists()) {
            resource = getInitialDataResource();
        }

        if (!resource.exists()) {
            throw new IllegalStateException("Not found: " + resource.getFilename());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Iterator<JsonNode> iterator = node.elements();
        while (iterator.hasNext()) {
            JsonNode pokemonNode = iterator.next();
            if (pokemonNode == null || pokemonNode.isEmpty()) {
                continue;
            }

            MutablePropertyValues pvs = new MutablePropertyValues();
            pvs.add("number", pokemonNode.get("Number").asText());
            pvs.add("name", pokemonNode.get("Name").asText());
            pvs.add("generation", pokemonNode.get("Generation").asText());

            JsonNode maxCpNode = pokemonNode.get("MaxCP");
            if (maxCpNode != null) {
                pvs.add("maxCP", maxCpNode.asText());
            } else {
                pvs.add("maxCP", -1);
            }

            JsonNode maxHpNode = pokemonNode.get("MaxHP");
            if (maxHpNode != null) {
                pvs.add("maxHP", maxHpNode.asText());
            } else {
                pvs.add("maxHP", -1);
            }

            List<String> types = new ArrayList<>();
            for (JsonNode type : pokemonNode.get("Types")) {
                types.add(type.asText());
            }
            pvs.add("types", types);

            Pokemon pokemon = new Pokemon();
            createDataBinder(pokemon).bind(pvs);
            pokemons.add(pokemon);
        }
    }

    private void save() {

    }

    private DataBinder createDataBinder(Pokemon pokemon) {
        DataBinder binder = new DataBinder(pokemon);
        binder.registerCustomEditor(PokemonType.class, new PokemonTypeEditor());

        return binder;
    }

    private static class PokemonTypeEditor extends PropertyEditorSupport {
        @Override
        public String getAsText() {
            return getValue() != null ? ((PokemonType) getValue()).name() : null;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            setValue(text != null ? PokemonType.valueOf(text.toUpperCase()) : null);
        }
    }

    private Resource getDataResource() {
        return resourceLoader.getResource(placeholderHelper.replacePlaceholders(
                "file:///@user.home@/echo/pokemons.json", environment::getProperty));
    }

    private Resource getInitialDataResource() {
        return resourceLoader.getResource("classpath:ahodanenok/spring/echo/pokemon/pokemons.json");
    }
}
