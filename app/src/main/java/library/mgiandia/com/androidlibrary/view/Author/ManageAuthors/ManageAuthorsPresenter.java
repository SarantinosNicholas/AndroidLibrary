package library.mgiandia.com.androidlibrary.view.Author.ManageAuthors;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.AuthorDAO;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.util.Quadruple;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class ManageAuthorsPresenter
{
    private ManageAuthorsView view;
    private AuthorDAO authors;

    private List<Quadruple> createDataSource(List<Author> authors)
    {
        List<Quadruple> triplets = new ArrayList<>();

        for(Author author : authors)
            triplets.add(new Quadruple(author.getId(), author.getFirstName(), author.getLastName(), "Σύνολο βιβλίων "+author.getBooks().size()));

        return triplets;
    }

    public ManageAuthorsPresenter(ManageAuthorsView view, AuthorDAO authors)
    {
        this.view = view;
        this.authors = authors;

        onLoadSource();
    }

    void onClickItem(int uid)
    {
        view.clickItem(uid);
    }

    void onStartAddNew()
    {
        view.startAddNew();
    }

    void onShowToast(String value)
    {
        view.showToast(value);
    }

    void onLoadSource()
    {
        view.loadSource(createDataSource(authors.findAll()));
    }
}
