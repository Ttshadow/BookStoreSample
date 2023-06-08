package data;

public interface Persistable<T> {
	T save(T t);
}
