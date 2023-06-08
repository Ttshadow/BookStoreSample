package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
	private int orderId;
	private Book bookOrdered;
	private int userId;
	private LocalDateTime localDateTime;

	public Order() {

	}

	public Order(int orderId, Book bookOrdered, int userId, LocalDateTime localDateTime) {
		super();
		this.orderId = orderId;
		this.bookOrdered = bookOrdered;
		this.userId = userId;
		this.localDateTime = localDateTime;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Book getBookOrdered() {
		return bookOrdered;
	}

	public void setBookOrdered(Book bookOrdered) {
		this.bookOrdered = bookOrdered;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookOrdered, localDateTime, orderId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(bookOrdered, other.bookOrdered) && orderId == other.orderId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", bookOrdered=" + bookOrdered + ", userId=" + userId + ", localDateTime="
				+ localDateTime + "]";
	}

}
