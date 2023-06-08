package test_bookService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import data.BookRepository;
import exception.ItemNotFoundException;
import model.Book;
import model.BookGenre;
import service.BookService;

@ExtendWith(MockitoExtension.class)
class Test_BookService {
	BookService bookService;

	@Mock
	BookRepository mockBookRepository;

	Book b1;
	Book b2;
	Book b3;
	Book b4;
	Book b5;
	List<Book> testBooks;

	@BeforeEach
	void init() {
		bookService = new BookService(mockBookRepository);
		b1 = new Book(1, 19.99, "title1", "author1", BookGenre.ACTION);
		b2 = new Book(2, 9.99, "title2", "author2", BookGenre.CRIME);
		b3 = new Book(3, 14.29, "title3", "author3", BookGenre.ACTION);
		b4 = new Book(4, 19.99, "title4", "author4", BookGenre.THRILLER);
		b5 = new Book(5, 19.99, "title1", "author4", BookGenre.THRILLER);
		testBooks = new ArrayList<>();
		Collections.addAll(testBooks, b1, b2, b3, b4, b5);
	}

	@Test
	void test_getAllBooks() {
		when(mockBookRepository.findAll()).thenReturn(testBooks);
		assertEquals(testBooks, bookService.getAllBooks());
		assertEquals(5, bookService.getAllBooks().size());
	}

	@Test
	void test_getBooksOfGenre() {
		when(mockBookRepository.findAll()).thenReturn(testBooks);
		List<Book> booksFound = bookService.getBooksOfGenre(BookGenre.ACTION);
		assertEquals(booksFound.size(), 2);
		assertEquals(booksFound.get(0), b1);
		assertEquals(booksFound.get(1), b3);
	}

	@Test
	void test_searchBooksByTitle() {
		when(mockBookRepository.findAll()).thenReturn(testBooks);
		List<Book> booksFound = bookService.searchBooksByTitle("title1");
		assertEquals(booksFound.size(), 2);
		assertEquals(booksFound.get(0), b1);
		assertEquals(booksFound.get(1), b5);
	}

	@Test
	void test_searchBooksByAuthorName() {
		when(mockBookRepository.findAll()).thenReturn(testBooks);
		List<Book> booksFound = bookService.searchBooksByAuthorName("author4");
		assertEquals(booksFound.size(), 2);
		assertEquals(booksFound.get(0), b4);
		assertEquals(booksFound.get(1), b5);
	}

	@Test
	void test_findById() throws ItemNotFoundException {
		when(mockBookRepository.findById(1)).thenReturn(b1);
		Book bookFound = bookService.findById(1);
		assertEquals(b1, bookFound);
	}

	@Test
	void test_highestPrice() {
		when(mockBookRepository.findAll()).thenReturn(testBooks);
		List<Book> list = bookService.findHightestBookPrice();
		assertEquals(3, list.size());
		assertEquals(b1, list.get(0));
		assertEquals(b4, list.get(1));
		assertEquals(b5, list.get(2));
	}
}
