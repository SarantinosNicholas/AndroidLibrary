package library.mgiandia.com.androidlibrary.dao;

import java.util.List;

import library.mgiandia.com.androidlibrary.domain.Author;

public interface AuthorDAO
{
    Author find(int author_id);
    List<Author> findAll();
    void save(Author entity);

    int next_id();//returns the next id
}
