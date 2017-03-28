package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.BorrowerCategoryDAO;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;

public class BorrowerCategoryDAOMemory implements BorrowerCategoryDAO {
    //keep the items in the order they were put to map AND allow searching by name
    protected static LinkedHashMap<String, BorrowerCategory> data = new LinkedHashMap<String, BorrowerCategory>();

    public void delete(BorrowerCategory entity) {
        data.remove(entity.getDescription());
    }

    public void save(BorrowerCategory entity) {
        if (!data.containsKey(entity.getDescription()))
            data.put(entity.getDescription(), entity);
    }

    public BorrowerCategory find(String name)
    {
        return data.get(name);
    }

    public List<String> findAllNames()
    {
        List<String> result = new ArrayList<String>();
        result.addAll(data.keySet());
        return result;
    }

    public List<BorrowerCategory> findAll()
    {
        List<BorrowerCategory> result = new ArrayList<BorrowerCategory>();
        result.addAll(data.values());
        return result;
    }
}
