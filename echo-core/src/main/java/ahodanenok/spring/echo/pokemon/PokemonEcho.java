package ahodanenok.spring.echo.pokemon;

import ahodanenok.spring.echo.Echo;

public class PokemonEcho implements Echo {

    private PokemonRepository repository;

    public PokemonEcho(PokemonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object echo() throws Exception {
        return repository.getRandom();
    }
}
