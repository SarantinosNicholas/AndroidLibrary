package library.mgiandia.com.androidlibrary.memorydao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.ItemState;
import library.mgiandia.com.androidlibrary.domain.Loan;

public class BorrowerDAOMemory implements BorrowerDAO {


    protected static List<Borrower> entities = new ArrayList<Borrower>();
    
    public void delete(Borrower entity) {
        entities.remove(entity);    
    }

    public List<Borrower> findAll() {
        return new ArrayList<Borrower>(entities);
    }


    public void save(Borrower entity) {
        if (! entities.contains(entity)) {
            entities.add(entity);    
        }        
    }
    
    public Borrower find(int borrowerNo) {
        for(Borrower borrower : entities) {
            if (borrower.getBorrowerNo() == borrowerNo ) {
                return borrower;
            }
        }
        return null;
    }

    public int next_id()
    {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getBorrowerNo()+1 : 1);//start from 1, not 0
    }
}
