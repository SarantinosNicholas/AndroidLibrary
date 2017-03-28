package library.mgiandia.com.androidlibrary.memorydao;

import java.util.List;

import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.dao.Initializer;
import library.mgiandia.com.androidlibrary.dao.ItemDAO;
import library.mgiandia.com.androidlibrary.dao.LoanDAO;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.Item;
import library.mgiandia.com.androidlibrary.domain.Loan;

public class MemoryInitializer extends Initializer {

    @Override
    protected void eraseData() {
                
        List<Borrower> allBorrowers = getBorrowerDAO().findAll();
        for(Borrower borrower : allBorrowers) {
            getBorrowerDAO().delete(borrower);
        }
            
        List<Item> allItems = getItemDAO().findAll();        
        for(Item item : allItems) {
            getItemDAO().delete(item);
        }
        
        List<Loan> allLoans = getLoanDAO().findAll(); 
        for(Loan loan : allLoans) {
            getLoanDAO().delete(loan);
        }    
    }

	@Override
	protected BorrowerDAO getBorrowerDAO() {
		return new BorrowerDAOMemory();
	}

	@Override
	protected ItemDAO getItemDAO() {
		return new ItemDAOMemory();
	}

	@Override
	protected LoanDAO getLoanDAO() {
		return new LoanDAOMemory();
	}
    
}
