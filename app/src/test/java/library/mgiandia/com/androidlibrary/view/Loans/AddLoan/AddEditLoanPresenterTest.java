package library.mgiandia.com.androidlibrary.view.Loans.AddLoan;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import library.mgiandia.com.androidlibrary.dao.Initializer;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.ItemState;
import library.mgiandia.com.androidlibrary.memorydao.MemoryInitializer;
import library.mgiandia.com.androidlibrary.util.ItemStateString;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AddEditLoanPresenterTest
{
    private Initializer dataHelper;
    private AddLoansPresenter presenter;
    private AddLoansViewStub view;

    @Before
    public void setUp()
    {
        dataHelper = new MemoryInitializer();
        dataHelper.prepareData();
        view = new AddLoansViewStub();
    }

    @Test
    public void testAddNewNoAvailableItems() {
        view.setAttachedBorrowerID(2);
        view.setSelectedBookId(1);
        presenter = new AddLoansPresenter(view, dataHelper.getBookDAO(), dataHelper.getBorrowerDAO(), dataHelper.getLoanDAO());

        presenter.onSaveLoan();

        Assert.assertEquals(view.getErrorTitle(), "Ανέφικτος ο Δανεισμός");
        Assert.assertEquals(view.getErrorMessage(), "Το επιλεγμένο βιβλίο δεν έχει αντίτυπα με κατάσταση (" + ItemStateString.convert(ItemState.AVAILABLE) + ").");
    }

    @Test
    public void testAddNewBorrowerCantAccept() {
        Borrower borrower = new Borrower(dataHelper.getBorrowerDAO().nextId(), null, null, null, null, null, null);

        borrower.setCategory(null);
        dataHelper.getBorrowerDAO().save(borrower);

        view.setAttachedBorrowerID(9);
        view.setSelectedBookId(3);
        presenter = new AddLoansPresenter(view, dataHelper.getBookDAO(), dataHelper.getBorrowerDAO(), dataHelper.getLoanDAO());

        presenter.onSaveLoan();

        Assert.assertEquals(view.getErrorTitle(), "Ανέφικτος ο Δανεισμός");
        Assert.assertEquals(view.getErrorMessage(), "Ο συγκεκριμένος Δανειζόμενος δεν μπορεί να δανειστεί βιβλία.");
    }

    @Test
    public void testAddNewOK() {
        view.setAttachedBorrowerID(2);
        view.setSelectedBookId(3);
        presenter = new AddLoansPresenter(view, dataHelper.getBookDAO(), dataHelper.getBorrowerDAO(), dataHelper.getLoanDAO());

        presenter.onSaveLoan();

        Assert.assertTrue(!view.getFinishMessage().equals(""));
    }
}
