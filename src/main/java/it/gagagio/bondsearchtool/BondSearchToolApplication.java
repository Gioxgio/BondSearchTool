package it.gagagio.bondsearchtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BondSearchToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(BondSearchToolApplication.class, args);
    }
}
