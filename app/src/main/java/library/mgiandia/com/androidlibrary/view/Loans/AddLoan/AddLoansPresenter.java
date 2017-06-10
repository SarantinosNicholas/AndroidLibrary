package library.mgiandia.com.androidlibrary.view.Loans.AddLoan;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.dao.BookDAO;
import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.dao.LoanDAO;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.Item;
import library.mgiandia.com.androidlibrary.domain.ItemState;
import library.mgiandia.com.androidlibrary.util.ItemStateString;


/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AddLoansPresenter {

    private AddLoansView view;
    private BookDAO books;
    private LoanDAO loans;
    Borrower attachedBorrower;

    public AddLoansPresenter(AddLoansView view, BookDAO books, BorrowerDAO borrowers, LoanDAO loans)
    {
        this.view = view;
        this.books = books;
        this.loans = loans;

        attachedBorrower = borrowers.find(view.getAttachedBorrowerID());

        view.setBorrowerId("#"+attachedBorrower.getBorrowerNo());
        view.setPageName("Πραγματοποίηση Δανεισμού");

        List<String> booksNames = new ArrayList<>();

        for(Book book : books.findAll())
            booksNames.add(book.getTitle());

        view.setBookList(booksNames);
    }

    public void onSaveLoan()
    {
        Book selectedBook = books.find(view.getSelectedBookId());

        Item availableItem = null;

        for(Item item : selectedBook.getItems())
            if(item.getState() == ItemState.AVAILABLE)
            {
                availableItem = item;
                break;
            }

        if(availableItem == null)
            view.showAlert("Ανέφικτος ο Δανεισμός", "Το επιλεγμένο βιβλίο δεν έχει αντίτυπα με κατάσταση ("+ ItemStateString.convert(ItemState.AVAILABLE)+").");
        else if(!attachedBorrower.canBorrow())
            view.showAlert("Ανέφικτος ο Δανεισμός", "Ο συγκεκριμένος Δανειζόμενος δεν μπορεί να δανειστεί βιβλία.");
        else
        {
            loans.save(availableItem.borrow(attachedBorrower, loans.nextId()));
            view.successfullyAddLoanAndFinishActivity("Ο Δανειζόμενος #"+attachedBorrower.getBorrowerNo()+" δανείστηκε το αντίτυπο #"+availableItem.getItemNumber());
        }
    }
}
