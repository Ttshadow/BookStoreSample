package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.BookRepository;
import exception.ItemNotFoundException;
import model.Book;
import model.BookGenre;

public class BookService {
	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public List<Book> getBooksOfGenre(BookGenre bookGenre) {
		List<Book> list = new ArrayList<>();
		for (Book book : getAllBooks()) {
			if (book.getBookGenre() == bookGenre) {
				list.add(book);
			}
		}
		return list;
	}

	public List<Book> searchBooksByTitle(String title) {
		List<Book> list = new ArrayList<>();
		for (Book book : getAllBooks()) {
			if (book.getTitle().equals(title)) {
				list.add(book);
			}
		}
		return list;
	}

	public List<Book> searchBooksByAuthorName(String bookAuthorNameToSearch) {
		List<Book> list = new ArrayList<>();
		for (Book book : getAllBooks()) {
			if (book.getAuthor().toLowerCase().contains(bookAuthorNameToSearch.toLowerCase())) {
				list.add(book);
			}
		}
		return list;
	}

	public Book findById(int itemId) throws ItemNotFoundException {
		Book findById = bookRepository.findById(itemId);
		if (findById != null) {
			return findById;
		} else {
			throw new ItemNotFoundException("Book Not Found.");
		}
	}

	public List<Book> findHightestBookPrice() {
		List<Book> output = new ArrayList<>();
//		double max = 0.0;
		List<Book> findAll = bookRepository.findAll();

		Collections.sort(findAll, (a, b) -> Double.compare(b.getPrice(), a.getPrice()));
		double max = findAll.get(0).getPrice();
		for (int i = 0; i < findAll.size(); i++) {
			if (findAll.get(i).getPrice() == max) {
				output.add(findAll.get(i));
			} else {
				break;
			}

		}
		return output;
	}

}