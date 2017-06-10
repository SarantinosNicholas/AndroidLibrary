package library.mgiandia.com.androidlibrary.dao;

import java.util.List;

import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.domain.Borrower;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public interface AuthorDAO
{
    Author find(int author_id);
    List<Author> findAll();
    void save(Author entity);
    void delete(Author entity);

    int nextId();
}
