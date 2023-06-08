package test_OrderingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import data.BookRepository;
import data.OrderRepository;
import data.UserRepository;
import exception.NotFoundException;
import model.Book;
import model.BookGenre;
import model.Order;
import model.User;
import service.OrderingService;

@ExtendWith(MockitoExtension.class)
class Test_OrderingService {
	OrderingService orderingService;
	@Mock
	OrderRepository mockOrderRepo;
	@Mock
	UserRepository mockUserRepo;
	@Mock
	BookRepository mockBookRepo;
	Book book1;
	Book book2;
	User user1;
	User user2;

	@BeforeEach
	void init() {
		orderingService = new OrderingService(mockOrderRepo, mockBookRepo, mockUserRepo);
		book1 = new Book(1, 19.99, "title1", "author1", BookGenre.ACTION);
		book2 = new Book(2, 9.99, "title2", "author2", BookGenre.CRIME);
		user1 = new User(1, "Stephen", "Star", "u1", "p1", "e1@email.com", new ArrayList<>());
		user2 = new User(2, "Yvonne", "Stone", "u2", "p2", "e2@email.com", new ArrayList<>());
	}

	@Test
	void test_placeOrder() throws NotFoundException {
		Order order = new Order(1, book1, user1.getUserId(), LocalDateTime.now());
		when(mockBookRepo.findById(1)).thenReturn(book1);
		when(mockUserRepo.findById(1)).thenReturn(user1);
		when(mockOrderRepo.save(order)).thenReturn(order);
		assertEquals(order, orderingService.placeOrder(book1, user1));
	}

	@Test
	void test_placeOrders() throws NotFoundException {
		List<Book> books = Arrays.asList(book1, book2);
		Order o1 = new Order(1, book1, user1.getUserId(), LocalDateTime.now());
		Order o2 = new Order(2, book2, user1.getUserId(), LocalDateTime.now());

		when(mockBookRepo.findById(1)).thenReturn(book1);
		when(mockBookRepo.findById(2)).thenReturn(book2);
		when(mockUserRepo.findById(1)).thenReturn(user1);

		when(mockOrderRepo.save(o1)).thenReturn(o1);
		when(mockOrderRepo.save(o2)).thenReturn(o2);

		List<Order> orders = orderingService.placeOrders(books, user1);
		assertEquals(orders.size(), 2);
		assertEquals(orders.get(0), o1);
		assertEquals(orders.get(1), o2);
		assertEquals(2, user1.getOrders().size());
	}

	@Test
    void test_getOrdersForUser() throws NotFoundException {
        when(mockBookRepo.findById(1)).thenReturn(book1);
        when(mockBookRepo.findById(2)).thenReturn(book2);
        when(mockUserRepo.findById(1)).thenReturn(user1);
        when(mockUserRepo.findById(2)).thenReturn(user2);

        orderingService.placeOrder(book1,user1);
        orderingService.placeOrder(book1,user2);
        orderingService.placeOrder(book2,user2);

        List<Order> ordersByUser = orderingService.getOrdersForUser(user2);
        assertEquals(ordersByUser.size(),2);
        assertEquals(book1, ordersByUser.get(0).getBookOrdered());
        assertEquals(book2, ordersByUser.get(1).getBookOrdered());
    }

	@Test
	void test_getOrdersForBook() {
		Order order1 = new Order(1, book1, user1.getUserId(), LocalDateTime.now());
		Order order2 = new Order(2, book2, user2.getUserId(), LocalDateTime.now());
		Order order3 = new Order(3, book1, user2.getUserId(), LocalDateTime.now());

		when(mockOrderRepo.findAll()).thenReturn(Arrays.asList(order1, order2, order3));

		List<Order> ordersForBook = orderingService.getOrdersForBooks(book1);

		assertEquals(ordersForBook.size(), 2);
		assertEquals(user1.getUserId(), ordersForBook.get(0).getUserId());
		assertEquals(user2.getUserId(), ordersForBook.get(1).getUserId());
	}

}
