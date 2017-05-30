package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Publisher;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class PublisherDAOMemory implements PublisherDAO {
    protected static ArrayList<Publisher> publishers = new ArrayList<Publisher>();

    public List<Publisher> findAll() {
        ArrayList<Publisher> result = new ArrayList<Publisher>();
        result.addAll(publishers);
        return result;
    }

    public void save(Publisher entity) {
        publishers.add(entity);
    }

    public Publisher find(int publisher_id)
    {
        for(Publisher publisher : publishers)
            if(publisher.getId() == publisher_id)
                return publisher;

        return null;
    }

    @Override
    public int nextId()
    {
        return (publishers.size() > 0 ? publishers.get(publishers.size()-1).getId()+1 : 1);
    }
}
