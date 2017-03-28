package library.mgiandia.com.androidlibrary.ui.HomePage;

import android.widget.EditText;
import android.widget.Spinner;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.contacts.Address;
import library.mgiandia.com.androidlibrary.contacts.EmailAddress;
import library.mgiandia.com.androidlibrary.contacts.TelephoneNumber;
import library.mgiandia.com.androidlibrary.contacts.ZipCode;
import library.mgiandia.com.androidlibrary.dao.BorrowerCategoryDAO;
import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
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
        //TODO
        BorrowerDAO borrowerDao = new BorrowerDAOMemory();



        for(int i = 0; i < 10; i++)
        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Nicholas"+i, "Sarantinos"+i,
                new Address("Street Name", "10", "Kifissia"+i, new ZipCode("10000"), "Ελλάδα"),
                new EmailAddress("user@mail.com"), new TelephoneNumber("1000000000"), new BorrowerCategoryDAOMemory().find("Καθηγητής")));





/*

        Borrower it = new Borrower();
        it.setFirstName("Παπαδύπουλος");
        it.setLastName("Γιώργος");
        it.setBorrowerNo(0);
        borrowerDao.save(it);

        Borrower it2 = new Borrower();
        it2.setFirstName("Γιανακόπουλος");
        it2.setLastName("Ιωάννης");
        it2.setBorrowerNo(1);
        borrowerDao.save(it2);

        for(int i = 0; i < 100; i++)
        {
            Borrower it3 = new Borrower();
            it3.setFirstName("Δημητρακόπουλος");
            it3.setLastName((2+i)+" Χρίστος");
            it3.setBorrowerNo(2+i);
            borrowerDao.save(it3);
        } */
    }


}
