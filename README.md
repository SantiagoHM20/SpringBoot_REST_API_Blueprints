# **SpringBoot_REST_API_Blueprints**

## **Description**

This lab focuses on designing and implementing the logical layer of an architectural blueprint management system for a prestigious design company.

## **Authors**

- **Santiago Hurtado Martínez** [SantiagoHM20](https://github.com/SantiagoHM20)

- **Mayerlly Suárez Correa** [mayerllyyo](https://github.com/mayerllyyo)

## Part I Dependency Inversion Principle, Lightweight Containers, and Dependency Injection.

#### Using Spring's annotation-based configuration, use the @Autowired and @Service annotations to mark the dependencies to be injected, and the candidate beans to be injected, respectively:

	-GrammarChecker will be a bean with a dependency of type 'SpellChecker'.

	-EnglishSpellChecker and SpanishSpellChecker are the two possible candidates for injection. You should select one or the other, but NOT both (this would cause a dependency resolution conflict). For now, use EnglishSpellChecker.

```java
@Component
public class GrammarChecker {

    private final SpellChecker spellChecker;

    @Autowired
    public GrammarChecker(@Qualifier("spanishSpellChecker") SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }
```

```java
@Component("spanishSpellChecker")
public class SpanishSpellChecker implements SpellChecker {
```

```java
@Component("englishSpellChecker")
public class EnglishSpellChecker implements SpellChecker {
```


#### Make a test program, where an instance of GrammarChecker is created using Spring, and it is used:
```java

public static void main(String[] args) {
	ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	GrammarChecker gc=ac.getBean(GrammarChecker.class);
	System.out.println(gc.check("la la la "));
}

```

Output: 

la la la 
true

#### Modify the configuration with annotations so that the ‘GrammarChecker‘ Bean now uses the SpanishSpellChecker class (so that GrammarChecker is injected with EnglishSpellChecker instead of SpanishSpellChecker). Check the new result.

```java

@Component
public class GrammarChecker {

    private final SpellChecker spellChecker;

    @Autowired
    public GrammarChecker(@Qualifier("spanishSpellChecker") SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public boolean check(String text) {
        return spellChecker.check(text);
    }
}

```

Output:

Checking Spanish spelling...
true

#### Part II Components and connectors

![image](img/ClassDiagram1.png)
  
To configure the application to work under a dependency injection scheme, as shown in the previous diagram:

This dependency was added to define and manage the beans and to handle IoC.

```xml

<dependencies>
    <!-- Dependencias de Spring clásico -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.9</version>
    </dependency>

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
        <version>5.3.9</version>
    </dependency>

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>5.3.9</version>
    </dependency>

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-expression</artifactId>
        <version>5.3.9</version>
    </dependency>
</dependencies>

```

And this is how the dependency injection was carried out in the service class:

```java

@Service
public class BlueprintsServices {

	private final BlueprintsPersistence bpp;

	@Autowired
	Public BlueprintsServices(BlueprintsPersistence bpp){
		this.bpp = bpp;
	}
}
```

#### Complete the getBluePrint() and getBlueprintsByAuthor() operations. Implement everything required from the lower layers (for now, the available 'InMemoryBlueprintPersistence' persistence scheme) by adding the corresponding tests in 'InMemoryPersistenceTest'.

```java

/**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) {
        try {
            return bpp.getBlueprint(author, name);
        } catch (BlueprintNotFoundException e) {
            System.out.println("Blueprint not found: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        try{
        return bpp.getBlueprintsByAuthor(author);
        }
        catch(BlueprintNotFoundException e){
            System.out.println("Blueprint not found: " + e.getMessage());
            return null;
        }
    }

```

Test:

```java

@Test
    public void getBlueprintTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
    
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
    
        ibpp.saveBlueprint(bp);
    
        Blueprint retrievedBp = ibpp.getBlueprint("john", "thepaint");
    
        assertNotNull(retrievedBp, "The retrieved blueprint should not be null.");
        assertEquals(bp, retrievedBp, "The retrieved blueprint does not match the expected blueprint.");
    }
    
    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
    
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "thepaint1", pts1);
    
        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts2);
    
        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
    
        Set<Blueprint> blueprints = ibpp.getBlueprintsByAuthor("john");
    
        assertNotNull(blueprints, "The retrieved blueprints set should not be null.");
        assertEquals(2, blueprints.size(), "The number of blueprints retrieved does not match the expected count.");
        assertTrue(blueprints.contains(bp1), "The retrieved blueprints set does not contain the expected blueprint.");
        assertTrue(blueprints.contains(bp2), "The retrieved blueprints set does not contain the expected blueprint.");
    }
```

#### Make a program in which you create (using Spring) an instance of BlueprintServices, and rectify its functionality: register plans, query plans, register specific plans, etc.

```java

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
```

#### It is required that blueprint query operations perform a filtering process before returning the requested blueprints. These filters aim to reduce the size of the blueprints by removing redundant data or simply subsampling them before they are returned.  

#### Adjust the application (adding the necessary abstractions and implementations) so that the `BlueprintServices` class is injected with one of two possible 'filters' (or potential future filters). The use of more than one filter at a time is not considered:

(A) **Redundancy Filtering**: suppresses consecutive duplicate points in the blueprint.  
(B) **Subsampling Filtering**: suppresses 1 out of every 2 points in the blueprint, in an alternating manner.


```java

@Component
public class RedundancyFiltering implements BlueprintFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> filteredPoints = new ArrayList<>();
        Point prev = null;
        for (Point point : blueprint.getPoints()) {
            if (prev == null || !point.equals(prev)) {
                filteredPoints.add(point);
            }
            prev = point;
        }
        blueprint.setPoints(filteredPoints);
        return blueprint;
    }
}

```
```java
@Component
public class SubsamplingFiltering implements BlueprintFilter {
    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> filteredPoints = new ArrayList<>();
        List<Point> points = blueprint.getPoints();
        for (int i = 0; i < points.size(); i += 2) {
            filteredPoints.add(points.get(i));
        }
        blueprint.setPoints(filteredPoints);
        return blueprint;
    }
}
```

#### Add the corresponding tests for each of these filters, and test their functionality in the test program, verifying that by only changing the position of the annotations —without modifying anything else— the program returns the filtered blueprints either in manner (A) or in manner (B).



```java
    @Test
    public void testRedundancyFiltering() {
        RedundancyFiltering redundancyFiltering = new RedundancyFiltering();

        Point[] points = {new Point(0, 0), new Point(0, 0), new Point(1, 1), new Point(1, 1)};
        Blueprint blueprint = new Blueprint("author", "test", points);

        Blueprint filteredBlueprint = redundancyFiltering.filter(blueprint);

        assertEquals(2, filteredBlueprint.getPoints().size());
        assertEquals(Arrays.asList(new Point(0, 0), new Point(1, 1)), filteredBlueprint.getPoints());
    }
```

```java

    @Test
    public void testSubsamplingFiltering() {
        SubsamplingFiltering subsamplingFiltering = new SubsamplingFiltering();

        Point[] points = {new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)};
        Blueprint blueprint = new Blueprint("author", "test", points);

        Blueprint filteredBlueprint = subsamplingFiltering.filter(blueprint);

        assertEquals(2, filteredBlueprint.getPoints().size());
        assertEquals(Arrays.asList(new Point(0, 0), new Point(2, 2)), filteredBlueprint.getPoints());
    }
```