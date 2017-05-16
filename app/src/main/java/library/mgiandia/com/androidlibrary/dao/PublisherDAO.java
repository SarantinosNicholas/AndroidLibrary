package library.mgiandia.com.androidlibrary.dao;

import java.util.List;

import library.mgiandia.com.androidlibrary.domain.Publisher;

public interface PublisherDAO
{
    Publisher find(int author_id);
    List<Publisher> findAll();
    void save(Publisher entity);

    int next_id();//returns the next id
}
