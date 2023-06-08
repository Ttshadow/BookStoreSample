package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.BookRepository;
import data.OrderRepository;
import data.UserRepository;
import exception.ItemNotFoundException;
import exception.NotFoundException;
import exception.UserNotFoundException;
import model.Book;
import model.Order;
import model.User;

public class OrderingService {
	private OrderRepository orderRepository;
	private AuthenticationService authenticationService;
	private BookService bookService;
	private int orderId = 1;

	public OrderingService(OrderRepository orderRepository, BookRepository bookRepository,
			UserRepository userRepository) {
		super();
		this.orderRepository = orderRepository;
		this.authenticationService = new AuthenticationService(userRepository);
		this.bookService = new BookService(bookRepository);
	}

	public Order placeOrder(Book book, User customer) throws NotFoundException {
		try {
			bookService.findById(book.getItemId());
		} catch (ItemNotFoundException e) {
			throw new NotFoundException("Book Not Found");
		}

		try {
			authenticationService.findById(customer.getUserId());
		} catch (UserNotFoundException e) {
			throw new NotFoundException("User Not Found");
		}

		Order o = new Order(orderId++, book, customer.getUserId(), LocalDateTime.now());

		List<Order> orders = customer.getOrders();
		orders.add(o);
		customer.setOrders(orders);
		return orderRepository.save(o);
	}

	public List<Order> placeOrders(List<Book> books, User customer) throws NotFoundException {
		List<Order> orders = new ArrayList<>();
		for (Book x : books) {
			try {
				Order o = placeOrder(x, customer);
				orders.add(o);
			} catch (NotFoundException e) {
				throw new NotFoundException(e.getMessage());
			}
		}
		return orders;
	}

	public List<Order> getOrdersForUser(User user) {
		return user.getOrders();
	}

	public List<Order> getOrdersForBooks(Book book) {
		List<Order> orders = orderRepository.findAll();
		List<Order> output = new ArrayList<>();
		for (Order order : orders) {
			if (order.getBookOrdered().getItemId() == book.getItemId()) {
				output.add(order);
			}
		}
		return output;
	}

	public int getOrderId() {
		return orderId;
	}
}
