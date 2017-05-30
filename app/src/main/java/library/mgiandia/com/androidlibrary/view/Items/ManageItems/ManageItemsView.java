package library.mgiandia.com.androidlibrary.view.Items.ManageItems;

import java.util.List;

import library.mgiandia.com.androidlibrary.util.Quadruple;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public interface ManageItemsView
{
    void loadSource(List<Quadruple> input);

    void newItemStateSelectAlert(int uid, String title, String message);
    void newItemAddAlert(String title, String message);

    void showAlert(String title, String message);
    void showToast(String value);

    void refresh();

    int getAttachedBookID();
    void setPageName(String value);
}
