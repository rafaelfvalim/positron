package br.com.octa.positron;

import java.io.File;
import java.nio.file.WatchService;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PositronApplication {

    public static void main(String[] args) {
        SpringApplication.run(PositronApplication.class, args);
    }

  

}
