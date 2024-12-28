# Simple Spring MVC project


!["Simple Spring MVC project"](images/spring-mvc-project.png?raw=true)


## Controller
```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
```

## View
```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<p th:text="|Heyyo, ${name}!|" />
</body>
</html>
```


## References :
1. https://spring.io/guides/gs/serving-web-content

2. https://github.com/spring-guides/gs-serving-web-content
