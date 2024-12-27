# Using stereotype annotations

Spring provides stereotype annotations: @Component, @Service, and @Controller etc.

**@Component** is a generic stereotype for any Spring-managed component. 

**@Repository, @Service, and @Controller are specializations of @Component** for more specific use cases (in the persistence, service, and presentation layers, respectively). Therefore, you can annotate your component classes with @Component, but, by annotating them with @Repository, @Service, or @Controller instead, your classes are more properly suited for processing by tools or associating with aspects. 

For example, these stereotype annotations make ideal targets for pointcuts. @Repository, @Service, and @Controller may also carry additional semantics in future releases of the Spring Framework. Thus, if you are choosing between using @Component or @Service for your service layer, @Service is clearly the better choice. Similarly, as stated earlier, @Repository is already supported as a marker for automatic exception translation in your persistence layer.

**@Component :** Indicates that the annotated class is a component. Such classes are considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.

# References :

https://docs.spring.io/spring-framework/reference/core/beans/classpath-scanning.html
