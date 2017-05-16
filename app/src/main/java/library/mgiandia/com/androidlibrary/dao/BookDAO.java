package library.mgiandia.com.androidlibrary.dao;

import java.util.List;

import library.mgiandia.com.androidlibrary.domain.Book;

public interface BookDAO
{
    Book find(int author_id);
    List<Book> findAll();
    void save(Book entity);

    int next_id();//returns the next id
}
