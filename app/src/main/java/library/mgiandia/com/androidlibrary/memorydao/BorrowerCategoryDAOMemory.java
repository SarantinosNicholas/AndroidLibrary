package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.BorrowerCategoryDAO;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;

/**
 * @author Νίκος Διαμαντίδης
 */

public class BorrowerCategoryDAOMemory implements BorrowerCategoryDAO
{
    protected static List<BorrowerCategory> entities = new ArrayList<>();

    public void delete(BorrowerCategory entity)
    {
        entities.remove(entity);
    }

    public void save(BorrowerCategory entity)
    {
        if (! entities.contains(entity))
            entities.add(entity);
    }

    public BorrowerCategory find(int uid)
    {
        for(BorrowerCategory borrowerCategory : entities) {
            if (borrowerCategory.getId() == uid ) {
                return borrowerCategory;
            }
        }

        return null;
    }

    public List<BorrowerCategory> findAll()
    {
        return new ArrayList(entities);
    }

    public int nextId()
    {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getId()+1 : 1);
    }
}
