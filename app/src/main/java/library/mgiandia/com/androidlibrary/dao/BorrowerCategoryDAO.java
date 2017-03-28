package library.mgiandia.com.androidlibrary.dao;

import java.util.List;

import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;

public interface BorrowerCategoryDAO
{
    void save(BorrowerCategory entity);

    void delete(BorrowerCategory entity);

    List<String> findAllNames();

    BorrowerCategory find(String name);

    List<BorrowerCategory> findAll();
}
