package data;

import model.Book;

public interface BookRepository extends Removeable<Book>, Persistable<Book>, Searchable<Book> {

}
