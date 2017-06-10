package library.mgiandia.com.androidlibrary.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import library.mgiandia.com.androidlibrary.util.LibraryException;
import library.mgiandia.com.androidlibrary.dao.Initializer;
import library.mgiandia.com.androidlibrary.dao.LoanDAO;
import library.mgiandia.com.androidlibrary.domain.ItemState;
import library.mgiandia.com.androidlibrary.domain.Loan;
import library.mgiandia.com.androidlibrary.memorydao.LoanDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.MemoryInitializer;
import library.mgiandia.com.androidlibrary.util.Money;
import library.mgiandia.com.androidlibrary.util.SimpleCalendar;
import library.mgiandia.com.androidlibrary.util.SystemDateStub;

public class ReturnServiceTest {

    private LoanDAO loan;

    @Before
    public void setUp() {
        Initializer dataHelper = new MemoryInitializer();
        dataHelper.prepareData();
        loan = new LoanDAOMemory();
    }

    @After
    public void restoreSystemDate() {
        SystemDateStub.reset();
    }
    
    @Test(expected=LibraryException.class)
    public void returnWhenNoLoanExist() {
        ReturnService service = new ReturnService();
        service.returnItem(2);
    }

    @Test
    public void confirmReturnedItem() {
        setSystemDateTo1stMarch2007();
        borrowUMLUserGuideToDiamantidis();
        setSystemDateTo2ndMarch2007();        
        ReturnService service = new ReturnService();
        service.returnItem(Initializer.UML_USER_GUIDE_ID1); 
        LoanDAO loanDao = new LoanDAOMemory();
        
        List<Loan> loanList = loanDao.findAll();                
        Loan loan = loanList.get(loanList.size()-1);
        Assert.assertEquals(new SimpleCalendar(2007,3,1), loan.getLoanDate());
        Assert.assertEquals(new SimpleCalendar(2007,3,2), loan.getReturnDate());
        Assert.assertEquals(Initializer.UML_USER_GUIDE_ID1, loan.getItem().getItemNumber());
        Assert.assertEquals(ItemState.AVAILABLE, loan.getItem().getState());     
    }

    @Test
    public void returnNoFine(){
        setSystemDateTo1stMarch2007();
        borrowUMLUserGuideToDiamantidis();
        setSystemDateTo2ndMarch2007();
        ReturnService service = new ReturnService();
        Money fine = service.returnItem(Initializer.UML_USER_GUIDE_ID1);
        Assert.assertNull(fine);
    }

    @Test
    public void returnWithFine(){
        setSystemDateTo1stMarch2007();
        borrowUMLUserGuideToDiamantidis();
        setSystemDateTo30thMarch2008();
        ReturnService service = new ReturnService();
        Money fine = service.returnItem(Initializer.UML_USER_GUIDE_ID1);
        Assert.assertNotNull(fine);
    }

    private void borrowUMLUserGuideToDiamantidis() {
        LoanService service = new LoanService();
        service.findBorrower(8);
        service.borrow(3, loan.nextId());
    }
    
    private void setSystemDateTo1stMarch2007() {        
        SystemDateStub.setStub(new SimpleCalendar(2007, 3, 1));
    }

    private void setSystemDateTo2ndMarch2007() {        
        SystemDateStub.setStub(new SimpleCalendar(2007, 3, 2));
    }

    private void setSystemDateTo30thMarch2008() {
        SystemDateStub.setStub(new SimpleCalendar(2008, 3, 30));
    }
}
