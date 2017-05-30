package library.mgiandia.com.androidlibrary.view.Loans.ManageLoans;

import java.util.List;

import library.mgiandia.com.androidlibrary.util.Quadruple;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public interface ManageLoansView
{
    void loadSource(List<Quadruple> input);

    void startAddNew(int uid);

    void showAlert(String title, String message);
    void showToast(String value);

    int getAttachedBorrowerID();
    void setPageName(String value);
}
