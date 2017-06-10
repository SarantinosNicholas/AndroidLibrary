package library.mgiandia.com.androidlibrary.view.Publisher.PublisherDetails;

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

public class PublisherDetailsPresenterTest
{
    private Initializer dataHelper;
    private PublisherDetailsPresenter presenter;
    private PublisherDetailsViewStub view;

    @Before
    public void setUp()
    {
        dataHelper = new MemoryInitializer();
        dataHelper.prepareData();
        view = new PublisherDetailsViewStub();
        view.setAttachedPublisherID(1);
        view.setPageName("page");
        presenter = new PublisherDetailsPresenter(view, dataHelper.getPublisherDAO());
    }

    @Test
    public void toastTest()
    {
        presenter.onShowToast("test");
        Assert.assertEquals("test", view.getToast());
    }

    @Test
    public void onStartEditButton()
    {
        presenter.onStartEditButtonClick();
        Assert.assertEquals(view.getEditId(), 1);
    }

    @Test
    public void onStartShowBooksButton()
    {
        presenter.onStartShowBooksButtonClick();
        Assert.assertEquals(view.getShowBooks(), 1);
    }
}
