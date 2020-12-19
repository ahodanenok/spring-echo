package ahodanenok.echo.pokemon;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("pokemon")
@Configuration
@ComponentScan(basePackages = "ahodanenok.echo.pokemon")
public class PokemonConfig {

}
