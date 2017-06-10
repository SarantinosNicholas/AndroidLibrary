package library.mgiandia.com.androidlibrary.service;

import java.util.List;

import org.junit.*;

import library.mgiandia.com.androidlibrary.util.LibraryException;
import library.mgiandia.com.androidlibrary.dao.*;
import library.mgiandia.com.androidlibrary.domain.ItemState;
import library.mgiandia.com.androidlibrary.domain.Loan;
import library.mgiandia.com.androidlibrary.memorydao.LoanDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.MemoryInitializer;

public class LoanServiceTest {

    private LoanDAO loanDao;

    @Before
    public void setUp()  {
        Initializer dataHelper = new MemoryInitializer();
        dataHelper.prepareData();
        loanDao = new LoanDAOMemory();
    }


    @Test(expected=LibraryException.class)
    public void noBorrower() {
        LoanService loanService = new LoanService();
        loanService.findBorrower(99999);
        loanService.borrow(Initializer.UML_DISTILLED_ID1, loanDao.nextId());
    }

    @Test
    public void testBorrow() {
        LoanService loanService = new LoanService();
        loanService.findBorrower(Initializer.DIAMANTIDIS_ID);

        Assert.assertNotNull(loanService.borrow(Initializer.UML_DISTILLED_ID1, loanDao.nextId()));
        
        LoanDAO loanDao = new LoanDAOMemory();
        List<Loan> loanList= loanDao.findAll();
        Loan loan = loanList.get(loanList.size()-1);
        
        Assert.assertTrue(loan.isPending());
        Assert.assertEquals(Initializer.UML_DISTILLED_ID1, loan.getItem().getItemNumber());
        Assert.assertEquals(ItemState.LOANED, loan.getItem().getState());
    }
    
    @Test
    public void borrowMemoryDataBase() {        
        LoanService loanService = new LoanService();
        loanService.findBorrower(Initializer.DIAMANTIDIS_ID);
        
        Assert.assertNotNull(loanService.borrow(Initializer.UML_USER_GUIDE_ID1, loanDao.nextId()));
        Assert.assertNotNull(loanService.borrow(Initializer.UML_DISTILLED_ID1, loanDao.nextId()));
        Assert.assertNotNull(loanService.borrow(Initializer.UML_REFACTORING_ID, loanDao.nextId()));
        Assert.assertNotNull(loanService.borrow(Initializer.UML_USER_GUIDE_ID2, loanDao.nextId()));
        Assert.assertNull(loanService.borrow(Initializer.UML_DISTILLED_ID2, loanDao.nextId()));
    }
}
