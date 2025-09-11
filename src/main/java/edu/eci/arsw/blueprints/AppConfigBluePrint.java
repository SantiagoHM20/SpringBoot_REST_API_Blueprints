package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints")
public class AppConfigBluePrint {
    public static void main(String[] args) throws BlueprintNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfigBluePrint.class);

        BlueprintsServices blueprintServices = context.getBean(BlueprintsServices.class);

        // Post new plan
        Point[] points = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint blueprint = new Blueprint("john", "mypaint", points);
        blueprintServices.addNewBlueprint(blueprint);

        // Get specific plan
        Blueprint retrievedBlueprint = blueprintServices.getBlueprint("john", "mypaint");
        System.out.println("Retrieved Blueprint: " + retrievedBlueprint);

        // Get plan by author
        System.out.println("Blueprints by author 'john': " + blueprintServices.getBlueprintsByAuthor("john"));

        context.close();
    }
}