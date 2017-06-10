package library.mgiandia.com.androidlibrary.view.Author.AuthorDetails;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import library.mgiandia.com.androidlibrary.dao.Initializer;
import library.mgiandia.com.androidlibrary.memorydao.MemoryInitializer;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AuthorDetailsPresenterTest
{
    private Initializer dataHelper;
    private AuthorDetailsPresenter presenter;
    private AuthorDetailsViewStub view;

    @Before
    public void setUp()
    {
        dataHelper = new MemoryInitializer();
        dataHelper.prepareData();
        view = new AuthorDetailsViewStub();
        view.setAttachedAuthorID(1);
        view.setPageName("page");
        presenter = new AuthorDetailsPresenter(view, dataHelper.getAuthorDAO());
    }

    @Test
    public void toastTest()
    {
        presenter.onShowToast("test");
        Assert.assertEquals("test", view.getToast());
    }

    @Test
    public void editButton()
    {
        presenter.onStartEditButtonClick();
        Assert.assertEquals(view.getEditAuthor(), 1);
    }

    @Test
    public void showBooksButton()
    {
        presenter.onStartShowBooksButtonClick();
        Assert.assertEquals(view.getShowBooks(), 1);
    }
}
