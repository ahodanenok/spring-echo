package ahodanenok.echo.pokemon;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Repository
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PokemonLoader implements InitializingBean {

    @Autowired
    private MessageSource messageSource;

    private JsonNode node;

    @Override
    public void afterPropertiesSet() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.readTree(new ClassPathResource("ahodanenok/echo/pokemons/pokemons.json").getInputStream());
    }

    public List<Pokemon> load() {
        List<Pokemon> pokemons = new ArrayList<>();

        Iterator<JsonNode> iterator = node.elements();
        while (iterator.hasNext()) {
            JsonNode obj = iterator.next();

            Pokemon pokemon = new Pokemon();
            DataBinder binder = new DataBinder(pokemon);

            MutablePropertyValues pvs = new MutablePropertyValues();
            if (obj.get("Number") == null) {
                continue;
            }

            pvs.add("number", obj.get("Number").textValue());
            pvs.add("name", obj.get("Name").textValue());
            binder.bind(pvs);

            BindingResult bindingResult = binder.getBindingResult();
            if (!bindingResult.hasErrors()) {
                pokemons.add(pokemon);
            } else {
                System.out.println();
                System.out.println("Errors:");
                for (ObjectError error : bindingResult.getAllErrors()) {
                    System.out.println(messageSource.getMessage(error, Locale.ENGLISH));
                }
            }
        }

        return pokemons;
    }
}
