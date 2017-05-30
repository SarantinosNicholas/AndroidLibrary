package library.mgiandia.com.androidlibrary.memorydao;

import library.mgiandia.com.androidlibrary.contacts.Address;
import library.mgiandia.com.androidlibrary.contacts.EmailAddress;
import library.mgiandia.com.androidlibrary.contacts.TelephoneNumber;
import library.mgiandia.com.androidlibrary.contacts.ZipCode;
import library.mgiandia.com.androidlibrary.dao.AuthorDAO;
import library.mgiandia.com.androidlibrary.dao.BookDAO;
import library.mgiandia.com.androidlibrary.dao.BorrowerCategoryDAO;
import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.dao.ItemDAO;
import library.mgiandia.com.androidlibrary.dao.LoanDAO;
import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;
import library.mgiandia.com.androidlibrary.domain.ISBN;
import library.mgiandia.com.androidlibrary.domain.Item;
import library.mgiandia.com.androidlibrary.domain.ItemState;
import library.mgiandia.com.androidlibrary.domain.Loan;
import library.mgiandia.com.androidlibrary.domain.Publisher;
import library.mgiandia.com.androidlibrary.service.LoanService;
import library.mgiandia.com.androidlibrary.util.Money;
import library.mgiandia.com.androidlibrary.util.SimpleCalendar;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class InitializeData
{
    private static boolean initialized = false;

    private static Item addOneBookCopy(Book book)
    {
        ItemDAO item = new ItemDAOMemory();
        Item itemTmp = new Item(item.nextId());
        itemTmp.setBook(book);
        item.save(itemTmp);
        return itemTmp;
    }

    public static Item makeAvailable(Item item)
    {
        if(item.getState() == ItemState.NEW)
            item.available();

        return item;
    }

    public static void init()
    {
        if(initialized) return;
        else initialized = true;

        PublisherDAO publisherDAO = new PublisherDAOMemory();

        publisherDAO.save(new Publisher(publisherDAO.nextId(), "McGraw-Hill Education", new Address("Αιόλου", "10", "Αθήνα",
                new ZipCode("100"), "Ελλάδα"), new EmailAddress("akr.agam@gmail.com"), new TelephoneNumber("2101070741")));

        publisherDAO.save(new Publisher(publisherDAO.nextId(), "Wiley", new Address("Ακαδημίας", "22", "Αθήνα",
                new ZipCode("129"), "Ελλάδα"), new EmailAddress("gain.agg@gmail.com"), new TelephoneNumber("2102070991")));


        AuthorDAO authorDAO = new AuthorDAOMemory();

        authorDAO.save(new Author(authorDAO.nextId(), "Ευάγγελος", "Αβέρωφ"));
        authorDAO.save(new Author(authorDAO.nextId(), "Χρήστος", "Βυζάντιος"));
        authorDAO.save(new Author(authorDAO.nextId(), "Απόστολος", "Γεωργιάδης"));
        authorDAO.save(new Author(authorDAO.nextId(), "Άλκης", "Θρύλος"));
        authorDAO.save(new Author(authorDAO.nextId(), "Βαγγέλης", "Ηλιόπουλος"));


        BorrowerCategoryDAO borrowerCategoryDao = new BorrowerCategoryDAOMemory();

        borrowerCategoryDao.save(new BorrowerCategory(borrowerCategoryDao.nextId(), "Προπτυχιακός Φοιτητής", 7 , 2, Money.euros(10)));
        borrowerCategoryDao.save(new BorrowerCategory(borrowerCategoryDao.nextId(), "Μεταπτυχιακός Φοιτητής", 14 , 3, Money.euros(7)));
        borrowerCategoryDao.save(new BorrowerCategory(borrowerCategoryDao.nextId(), "Διδακτορικός Φοιτητής", 21 , 4, Money.euros(5)));
        borrowerCategoryDao.save(new BorrowerCategory(borrowerCategoryDao.nextId(), "Μέλος Δ.Ε.Π.", 21 , 4, Money.euros(5)));
        borrowerCategoryDao.save(new BorrowerCategory(borrowerCategoryDao.nextId(), "Καθηγητής", 180 , 6, Money.euros(0)));


        BorrowerDAO borrowerDao = new BorrowerDAOMemory();
        //all country names should be from our list, see string.xml

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Αγαμέμνων", "Ακρίδας",
                new Address("Αιόλου", "10", "Αθήνα", new ZipCode("100"), "Ελλάδα"),
                new EmailAddress("akr.agam@gmail.com"), new TelephoneNumber("2101070741"), borrowerCategoryDao.find(1/*"Προπτυχιακός Φοιτητής"*/)));

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Μανόλης", "Γιακουμάκης",
                new Address("Αιόλου", "11", "Αθήνα", new ZipCode("119"), "Ελλάδα"),
                new EmailAddress("mgia@aueb.gr"), new TelephoneNumber("2108203292"), borrowerCategoryDao.find(5/*"Καθηγητής"*/)));

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Βασίλης", "Δραγούμης",
                new Address("Ερμού", "100", "Αθήνα", new ZipCode("150"), "Ελλάδα"),
                new EmailAddress("bal.drag@gmail.com"), new TelephoneNumber("2101070911"), borrowerCategoryDao.find(3/*"Διδακτορικός Φοιτητής"*/)));

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Άρτεμις", "Λύτρου",
                new Address("Ερμού", "101", "Αθήνα", new ZipCode("150"), "Ελλάδα"),
                new EmailAddress("art.lutou@gmail.com"), new TelephoneNumber("2102071912"), borrowerCategoryDao.find(3/*"Διδακτορικός Φοιτητής"*/)));

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Γερακίνα", "Λούπη",
                new Address("Πανεπιστημίου", "122", "Αθήνα", new ZipCode("170"), "Ελλάδα"),
                new EmailAddress("ger.louph@gmail.com"), new TelephoneNumber("2101271123"), borrowerCategoryDao.find(3/*"Διδακτορικός Φοιτητής"*/)));

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Mario", "Cox",
                new Address("Glenwood St. NE", "1807", "Palm Bay FL", new ZipCode("32907"), "Ηνωμένο Βασίλειο"),
                new EmailAddress("mario.lee@gmail.com"), new TelephoneNumber("5417543010"), borrowerCategoryDao.find(5/*"Καθηγητής"*/)));

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Αντρέας", "Βυζάντιος",
                new Address("Πανεπιστημίου", "123", "Αθήνα", new ZipCode("170"), "Ελλάδα"),
                new EmailAddress("adre.bizant@gmail.com"), new TelephoneNumber("2101271131"), borrowerCategoryDao.find(5/*"Καθηγητής"*/)));

        borrowerDao.save(new Borrower(borrowerDao.nextId(), "Νίκος", "Διαμαντίδης",
                new Address("Πανεπιστημίου", "27", "Αθήνα", new ZipCode("1229"), "Ελλάδα"),
                new EmailAddress("nad@aueb.gr"), new TelephoneNumber("2108203292"), borrowerCategoryDao.find(5/*"Καθηγητής"*/)));

        BookDAO bookDao = new BookDAOMemory();

        bookDao.save(new Book(bookDao.nextId(), "Don Quixote", new ISBN("123456"), "978-3-16", 2012, publisherDAO.find(2)));
        bookDao.save(new Book(bookDao.nextId(), "The Odyssey", new ISBN("738912"), "123-4-56", 2000, publisherDAO.find(1)));

        //add a copy per book
        addOneBookCopy(bookDao.find(1));
        addOneBookCopy(bookDao.find(2));

        //publishers are auto informed about the added books

        //now, link authors with books
        authorDAO.find(1).addBook(bookDao.find(1));
        authorDAO.find(2).addBook(bookDao.find(1));
        authorDAO.find(3).addBook(bookDao.find(1));

        authorDAO.find(3).addBook(bookDao.find(2));
        authorDAO.find(4).addBook(bookDao.find(2));

        new ItemDAOMemory().find(1).available();
        LoanService service = new LoanService();
        service.findBorrower(1);
        service.borrow(1);

        //some loans
        LoanDAO loans = new LoanDAOMemory();
        loans.save(makeAvailable(addOneBookCopy(bookDao.find(2))).borrow(borrowerDao.find(8)));
        loans.save(makeAvailable(addOneBookCopy(bookDao.find(1))).borrow(borrowerDao.find(8)));
        loans.save(makeAvailable(addOneBookCopy(bookDao.find(2))).borrow(borrowerDao.find(5)));
        loans.save(makeAvailable(addOneBookCopy(bookDao.find(1))).borrow(borrowerDao.find(3)));
        loans.save(makeAvailable(addOneBookCopy(bookDao.find(1))).borrow(borrowerDao.find(1)));
    }
}
