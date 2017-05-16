package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.BookDAO;
import library.mgiandia.com.androidlibrary.domain.Book;

public class BookDAOMemory implements BookDAO {
    protected static ArrayList<Book> books = new ArrayList<Book>();

    public List<Book> findAll() {
        ArrayList<Book> result = new ArrayList<Book>();
        result.addAll(books);
        return result;
    }

    public void save(Book book) {
        books.add(book);
    }

    public Book find(int uid)
    {
        for(Book now : books)
            if(now.getID() == uid)
                return now;

        return null;
    }

    @Override
    public int next_id()
    {
        return (books.size() > 0 ? books.get(books.size()-1).getID()+1 : 1);//start from 1, not 0
    }
}
