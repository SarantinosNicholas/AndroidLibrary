package library.mgiandia.com.androidlibrary.service;

import library.mgiandia.com.androidlibrary.dao.LoanDAO;
import library.mgiandia.com.androidlibrary.domain.Loan;
import library.mgiandia.com.androidlibrary.memorydao.LoanDAOMemory;
import library.mgiandia.com.androidlibrary.util.LibraryException;
import library.mgiandia.com.androidlibrary.util.Money;

/**
 * Η υπηρεσία επιστροφής αντιτύπου.
 * @author Νίκος Διαμαντίδης
 *
 */
public class ReturnService {

    /**
     * Πραγματοποιεί την επιστροφή ενός αντιτύπου και
     * επιστρέφει το τυχόν πρόστιμο που πρέπει να καταβληθεί.
     * @param itemNo Ο αριθμός εισαγωγής του αντιτύπου που επιστρέφεται.
     * @return Το πρόστιμο που πρέπει να πληρωθεί ή {@code null}
     * αν δεν υπάρχει πρόστιμο.
     */
    public Money returnItem(int itemNo) {
        LoanDAO loanDAO = new LoanDAOMemory();
        Money fine = null;

        Loan loan = loanDAO.findPending(itemNo);
        if (loan == null) {
            throw new LibraryException();
        }

        loan.returnItem();
        if (loan.getOverdue() > 0) {
            fine = loan.getFine();
        }

        loanDAO.save(loan);
        return fine;
    }
}
