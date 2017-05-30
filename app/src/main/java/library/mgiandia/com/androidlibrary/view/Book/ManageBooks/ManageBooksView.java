package library.mgiandia.com.androidlibrary.view.Book.ManageBooks;

import java.util.List;

import library.mgiandia.com.androidlibrary.util.Quadruple;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public interface ManageBooksView
{
    void clickItem(int uid);
    void clickItemList(int uid);

    void startAddNew();
    void loadSource(List<Quadruple> input);

    void setPageName(String value);

    void showToast(String value);

    boolean shouldLoadItemsOnClick();

    Integer getAttachedAuthorID();
    Integer getAttachedPublisherID();
}
