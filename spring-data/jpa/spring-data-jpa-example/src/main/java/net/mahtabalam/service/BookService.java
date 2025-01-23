package net.mahtabalam.service;

import net.mahtabalam.model.Book;
import net.mahtabalam.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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