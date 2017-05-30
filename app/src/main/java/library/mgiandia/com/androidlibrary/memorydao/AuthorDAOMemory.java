package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.AuthorDAO;
import library.mgiandia.com.androidlibrary.domain.Author;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AuthorDAOMemory implements AuthorDAO {
    protected static ArrayList<Author> authors = new ArrayList<Author>();

    public List<Author> findAll() {
        ArrayList<Author> result = new ArrayList<Author>();
        result.addAll(authors);
        return result;
    }

    public void save(Author entity) {
        authors.add(entity);
    }

    public Author find(int author_id)
    {
        for(Author author : authors)
            if(author.getId() == author_id)
                return author;

        return null;
    }

    @Override
    public int nextId()
    {
        return (authors.size() > 0 ? authors.get(authors.size()-1).getId()+1 : 1);
    }
}
