# Spring Data JPA example


## Model/Entity
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String authorName;
    private LocalDate publishDate;

    // for JPA only, no use
    public Book() {
    }

    public Book(String title, String authorName, LocalDate publishDate) {
        this.title = title;
        this.authorName = authorName;
        this.publishDate = publishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
```

## Repository interface
```java
import net.mahtabalam.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookRepository extends JpaRepository<Book, Long> {

    // it works if it matches the book field name
    List<Book> findByTitle(String title);

    // Custom Query
    @Query("SELECT b FROM Book b WHERE b.publishDate > :date")
    List<Book> findByPublishedDateAfter(@Param("date") LocalDate date);

}
```

## Service
```java
@Service
public class BookService {

    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByPublishedDateAfter(LocalDate date) {
        return bookRepository.findByPublishedDateAfter(date);
    }
}
```

## AppConfig
```java
package net.mahtabalam.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "net.mahtabalam.repository")
@EntityScan(basePackages = "net.mahtabalam.model")
@ComponentScan(basePackages = "net.mahtabalam.service")
public class AppConfig {

}
```

## Main class
```java
@SpringBootApplication
public class SpringDataJpaExampleApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringDataJpaExampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaExampleApplication.class, args);
	}

	// Spring runs CommandLineRunner bean when Spring Boot App starts
	@Bean
	public CommandLineRunner demo(BookService bookService) {
		return (args) -> {

			Book b1 = new Book("Book A", "Author A", LocalDate.of(2025, 1, 20));
			Book b2 = new Book("Book B", "Author B", LocalDate.of(2025, 1, 8));
			Book b3 = new Book("Book C", "Author C", LocalDate.of(2024, 6, 10));
			Book b4 = new Book("Book D", "Author D", LocalDate.of(2023, 5, 5));

			// save a few books, ID auto increase, expect 1, 2, 3, 4
			bookService.save(b1);
			bookService.save(b2);
			bookService.save(b3);
			bookService.save(b4);

			// find all books
			log.info("findAll()");
			log.info("-------------------------------");
			for (Book book : bookService.findAll()) {
				log.info(book.toString());
			}
			log.info("\n");

			// find book by ID
			Optional<Book> optionalBook = bookService.findById(1L);
			optionalBook.ifPresent(obj -> {
				log.info("Find book with id 1");
				log.info("--------------------------------");
				log.info(obj.toString());
				log.info("\n");
			});

			// find book by title
			log.info("Find book with Title `Book B`");
			log.info("--------------------------------------------");
			bookService.findByTitle("Book B").forEach(b -> {
				log.info(b.toString());
				log.info("\n");
			});

			// find book by published date after
			log.info("Book found with findByPublishedDateAfter(), after 2023/7/1");
			log.info("--------------------------------------------");
			bookService.findByPublishedDateAfter(LocalDate.of(2023, 7, 1)).forEach(b -> {
				log.info(b.toString());
				log.info("\n");
			});

			// delete a book
			bookService.deleteById(2L);
			log.info("Book delete where ID = 2L");
			log.info("--------------------------------------------");

			// find all books
			log.info("findAll()");
			log.info("-------------------------------");
			for (Book book : bookService.findAll()) {
				log.info(book.toString());
			}
			log.info("\n");

		};
	}
}
```

## Output :
```
 findAll()
 -------------------------------
 Book{id=1, title='Book A', authorName='Author A', publishDate=2025-01-20}
 Book{id=2, title='Book B', authorName='Author B', publishDate=2025-01-08}
 Book{id=3, title='Book C', authorName='Author C', publishDate=2024-06-10}
 Book{id=4, title='Book D', authorName='Author D', publishDate=2023-05-05}

 Find book with id 1
--------------------------------
 Book{id=1, title='Book A', authorName='Author A', publishDate=2025-01-20}

 Find book with Title `Book B`
--------------------------------------------
 Book{id=2, title='Book B', authorName='Author B', publishDate=2025-01-08}

 Book found with findByPublishedDateAfter(), after 2023/7/1
 --------------------------------------------
 Book{id=1, title='Book A', authorName='Author A', publishDate=2025-01-20}
 Book{id=2, title='Book B', authorName='Author B', publishDate=2025-01-08}
 Book{id=3, title='Book C', authorName='Author C', publishDate=2024-06-10}


 Book delete where ID = 2L
--------------------------------------------
 findAll()
 -------------------------------
 Book{id=1, title='Book A', authorName='Author A', publishDate=2025-01-20}
 Book{id=3, title='Book C', authorName='Author C', publishDate=2024-06-10}
 Book{id=4, title='Book D', authorName='Author D', publishDate=2023-05-05}

 Closing JPA EntityManagerFactory for persistence unit 'default'
 HikariPool-1 - Shutdown initiated...
 HikariPool-1 - Shutdown completed.
```

# References :
1. https://spring.io/guides/gs/accessing-data-jpa

2. https://mkyong.com/spring-boot/spring-boot-spring-data-jpa
