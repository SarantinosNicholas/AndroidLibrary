package library.mgiandia.com.androidlibrary.view.Publisher.ManagePublishers;

import java.util.List;

import library.mgiandia.com.androidlibrary.util.Quadruple;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public interface ManagePublishersView
{
    void clickItem(int uid);
    void startAddNew();
    void loadSource(List<Quadruple> input);

    void showToast(String value);
}
