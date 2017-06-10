package library.mgiandia.com.androidlibrary.service;


import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.dao.ItemDAO;
import library.mgiandia.com.androidlibrary.dao.LoanDAO;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.Item;
import library.mgiandia.com.androidlibrary.domain.Loan;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.ItemDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.LoanDAOMemory;
import library.mgiandia.com.androidlibrary.util.LibraryException;
import library.mgiandia.com.androidlibrary.util.SimpleCalendar;

/**
 * Η υπηρεσία του δανεισμού. Αναλαμβάνει την αναζήτηση
 * δανειζομένων και αντιτύπων και καταγράφει τους δανεισμούς
 * @author Νίκος Διαμαντίδης
 *
 */
public class LoanService {
    private Borrower borrower;

    /**
     * Αναζητά το δανειζόμενο με βάση τον αριθμό δανειζομένου.
     * @param borrowerNo Ο αριθμός δανειζομένου
     * @return {@code true} αν βρεθεί ο δανειζόμενος
     */
    public Boolean findBorrower(int borrowerNo) {
        BorrowerDAO borrowerDao = new BorrowerDAOMemory();
        borrower = borrowerDao.find(borrowerNo);
        return borrower != null;
    }

    /**
     * Πραγματοποιεί το δανεισμό.
     * @param itemNo Ο αριθμός εισαγωγής του αντιτύπου
     * @param uid Ο κωδικός του δανείου
     * @return Την προθεσμία επιστροφής.
     * Επιστρέφει {@code null} εάν ο δανειζόμενος δεν δικαιούται
     * να δανειστεί αντίτυπο.
     * @throws LibraryException Εάν δεν υπάρχει δανειζόμενος
     */
    public SimpleCalendar borrow(int itemNo, int uid) {
        if (borrower == null) {
            throw new LibraryException();
        }
        if (!borrower.canBorrow()) {
            return null;
        }
        ItemDAO itemDao = new ItemDAOMemory();
        Item item = itemDao.find(itemNo);
        Loan loan = item.borrow(borrower, uid);
        LoanDAO loanDao = new LoanDAOMemory();
        loanDao.save(loan);
        return loan.getDue();
    }
}
