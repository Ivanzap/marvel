package com.ivanzap.marvel;

import com.ivanzap.marvel.web.CharacterRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.ivanzap.marvel.model.Character;

import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

            CharacterRestController characterRestController = context.getBean(CharacterRestController.class);
            characterRestController.save(new Character(null, "Vasya", "10 goals per 3 minute"));
            System.out.println(characterRestController.getAll());

        }
    }
}
