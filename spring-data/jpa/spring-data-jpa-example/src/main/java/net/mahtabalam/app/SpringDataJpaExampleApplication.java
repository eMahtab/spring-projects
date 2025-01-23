package net.mahtabalam.app;

import net.mahtabalam.model.Book;
import net.mahtabalam.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Optional;

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
