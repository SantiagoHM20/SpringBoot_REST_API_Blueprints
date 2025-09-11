

 package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.services.GrammarChecker;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints")
public class AppConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GrammarChecker gc = context.getBean(GrammarChecker.class);
        System.out.println(gc.check("la la la "));
        context.close();
    }
}
