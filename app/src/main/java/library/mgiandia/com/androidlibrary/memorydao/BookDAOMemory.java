package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.BookDAO;
import library.mgiandia.com.androidlibrary.domain.Book;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

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
            if(now.getId() == uid)
                return now;

        return null;
    }

    @Override
    public int nextId()
    {
        return (books.size() > 0 ? books.get(books.size()-1).getId()+1 : 1);
    }
}
