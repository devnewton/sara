package im.bci.sara;

import java.util.concurrent.Executor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

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
