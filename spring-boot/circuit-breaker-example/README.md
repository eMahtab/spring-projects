## Implementing Circuit Breaker in Spring Boot Microservices using Resilience4j

This demo is based on [Java Brains video](https://www.youtube.com/watch?v=o8RO38KbWvA&list=PLqq-6Pq4lTTbXZY_elyGv7IkKrfkSrX5e), with some modifications and it uses Resilience4j rather than Hysterix to implement Circuit breaker functionality. But the overall gist is same.


## All the microservices registered with Eureka server

**This demo creates three microservices, movie-catalog-service calls the movie-info-service (to fetch the info for a movie) and movie-review-service (to fetch the reviews for a movie).**

!["Microservices registered with Eureka server"](images/eureka-server.png?raw=true)


## Movie Info Service

!["Movie Info Service"](images/movie-info-service.png?raw=true)

## Movie Review Service

!["Movie Review Service"](images/movie-review-service.png?raw=true)


## Movie Catalog Service

!["Movie Catalog Service"](images/movie-catalog-service.png?raw=true)
