# **SpringBoot_REST_API_Blueprints**

## **Description**

This lab focuses on designing and implementing the logical layer of an architectural blueprint management system for a prestigious design company.

## **Authors**

- **Santiago Hurtado Martínez** [SantiagoHM20](https://github.com/SantiagoHM20)

- **Mayerlly Suárez Correa** [mayerllyyo](https://github.com/mayerllyyo)


![image](img/ClassDiagram1.png)
  
To configure the application to work under a dependency injection scheme, as shown in the previous diagram:

This dependency was added to define and manage the beans and to handle IoC.

```xml

<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-beans</artifactId>
	<version>4.2.4.RELEASE</version>
</dependency>

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
