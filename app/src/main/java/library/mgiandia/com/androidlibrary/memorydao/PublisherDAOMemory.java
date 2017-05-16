package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Publisher;

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
            if(publisher.getID() == publisher_id)
                return publisher;

        return null;
    }

    @Override
    public int next_id()
    {
        return (publishers.size() > 0 ? publishers.get(publishers.size()-1).getID()+1 : 1);//start from 1, not 0
    }
}
