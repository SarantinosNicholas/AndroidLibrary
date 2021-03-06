package library.mgiandia.com.androidlibrary.view.Publisher.ManagePublishers;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Publisher;
import library.mgiandia.com.androidlibrary.util.Quadruple;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class ManagePublishersPresenter
{
    private ManagePublishersView view;
    private PublisherDAO publishers;

    private List<Quadruple> createDataSource(List<Publisher> publishers)
    {
        List<Quadruple> triplets = new ArrayList<>();

        for(Publisher publisher : publishers)
            triplets.add(new Quadruple(publisher.getId(), publisher.getName(), null, "Σύνολο βιβλίων "+publisher.getBooks().size()));

        return triplets;
    }

    public ManagePublishersPresenter(ManagePublishersView view, PublisherDAO publishers)
    {
        this.view = view;
        this.publishers = publishers;

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
        view.loadSource(createDataSource(publishers.findAll()));
    }
}
