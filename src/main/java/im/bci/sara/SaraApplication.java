package im.bci.sara;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SaraApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sara? Non c'est à côté!");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SaraApplication.class, args);
    }
}
