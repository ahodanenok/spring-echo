package ahodanenok.echo.pokemon;

import ahodanenok.echo.Echo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("echo")
@Lazy
public class PokemonEcho implements Echo {

    @Autowired
    private PokemonLoader loader;

    private List<Pokemon> pokemons;

    @PostConstruct
    private void init() {
        this.pokemons = loader.load();
    }

    @Override
    public String say() {
        return pokemons.get((int) (Math.random() * pokemons.size())).getName();
    }
}
