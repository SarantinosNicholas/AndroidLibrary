package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.AuthorDAO;
import library.mgiandia.com.androidlibrary.domain.Author;

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
            if(author.getID() == author_id)
                return author;

        return null;
    }

    @Override
    public int next_id()
    {
        return (authors.size() > 0 ? authors.get(authors.size()-1).getID()+1 : 1);//start from 1, not 0
    }
}
