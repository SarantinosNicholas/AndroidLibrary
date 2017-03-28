package library.mgiandia.com.androidlibrary.dao;

import library.mgiandia.com.androidlibrary.contacts.Address;
import library.mgiandia.com.androidlibrary.contacts.EmailAddress;
import library.mgiandia.com.androidlibrary.contacts.TelephoneNumber;
import library.mgiandia.com.androidlibrary.contacts.ZipCode;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerCategoryDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerDAOMemory;
import library.mgiandia.com.androidlibrary.util.Money;

/**
 * Created by Shadow on 22/3/2017.
 */

public class InitializeData
{
    public static void initialize_borrower_categories()
    {
        BorrowerCategoryDAO borrowerCategoryDao = new BorrowerCategoryDAOMemory();

        borrowerCategoryDao.save(new BorrowerCategory("Προπτυχιακός Φοιτητής", 7 , 2, Money.euros(10)));
        borrowerCategoryDao.save(new BorrowerCategory("Μεταπτυχιακός Φοιτητής", 14 , 3, Money.euros(7)));
        borrowerCategoryDao.save(new BorrowerCategory("Διδακτορικός Φοιτητής", 21 , 4, Money.euros(5)));
        borrowerCategoryDao.save(new BorrowerCategory("Μέλος Δ.Ε.Π.", 21 , 4, Money.euros(5)));
        borrowerCategoryDao.save(new BorrowerCategory("Καθηγητής", 180 , 6, Money.euros(0)));
    }

    public static void initialize_borrowers()
    {
        //all country names should be from our list, see string.xml
        BorrowerDAO borrowerDao = new BorrowerDAOMemory();

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Αγαμέμνων", "Ακρίδας",
                new Address("Αιόλου", "10", "Αθήνα", new ZipCode("100"), "Ελλάδα"),
                new EmailAddress("akr.agam@gmail.com"), new TelephoneNumber("2101070741"), new BorrowerCategoryDAOMemory().find("Προπτυχιακός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Μανόλης", "Γιακουμάκης",
                new Address("Αιόλου", "11", "Αθήνα", new ZipCode("119"), "Ελλάδα"),
                new EmailAddress("mgia@aueb.gr"), new TelephoneNumber("2108203292"), new BorrowerCategoryDAOMemory().find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Άγγελος", "Γιαννόπουλος",
                new Address("Ακαδημίας", "22", "Αθήνα", new ZipCode("129"), "Ελλάδα"),
                new EmailAddress("gain.agg@gmail.com"), new TelephoneNumber("2102070991"), new BorrowerCategoryDAOMemory().find("Μεταπτυχιακός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Βασίλης", "Δραγούμης",
                new Address("Ερμού", "100", "Αθήνα", new ZipCode("150"), "Ελλάδα"),
                new EmailAddress("bal.drag@gmail.com"), new TelephoneNumber("2101070911"), new BorrowerCategoryDAOMemory().find("Διδακτορικός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Άρτεμις", "Λύτρου",
                new Address("Ερμού", "101", "Αθήνα", new ZipCode("150"), "Ελλάδα"),
                new EmailAddress("art.lutou@gmail.com"), new TelephoneNumber("2102071912"), new BorrowerCategoryDAOMemory().find("Διδακτορικός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Γερακίνα", "Λούπη",
                new Address("Πανεπιστημίου", "122", "Αθήνα", new ZipCode("170"), "Ελλάδα"),
                new EmailAddress("ger.louph@gmail.com"), new TelephoneNumber("2101271123"), new BorrowerCategoryDAOMemory().find("Διδακτορικός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Mario", "Cox",
                new Address("Glenwood St. NE", "1807", "Palm Bay FL", new ZipCode("32907"), "Ηνωμένο Βασίλειο"),
                new EmailAddress("mario.lee@gmail.com"), new TelephoneNumber("5417543010"), new BorrowerCategoryDAOMemory().find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Αντρέας", "Βυζάντιος",
                new Address("Πανεπιστημίου", "123", "Αθήνα", new ZipCode("170"), "Ελλάδα"),
                new EmailAddress("adre.bizant@gmail.com"), new TelephoneNumber("2101271131"), new BorrowerCategoryDAOMemory().find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Νίκος", "Διαμαντίδης",
                new Address("Πανεπιστημίου", "27", "Αθήνα", new ZipCode("1229"), "Ελλάδα"),
                new EmailAddress("nad@aueb.gr"), new TelephoneNumber("2108203292"), new BorrowerCategoryDAOMemory().find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Ασήμω", "Παπαδοπούλου",
                new Address("Ακαδημίας", "27", "Αθήνα", new ZipCode("129"), "Ελλάδα"),
                new EmailAddress("simo.papadop@gmail.com"), new TelephoneNumber("2102392110"), new BorrowerCategoryDAOMemory().find("Καθηγητής")));
    }
}
