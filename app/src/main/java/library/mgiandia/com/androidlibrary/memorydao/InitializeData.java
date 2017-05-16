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
import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;
import library.mgiandia.com.androidlibrary.domain.ISBN;
import library.mgiandia.com.androidlibrary.domain.Item;
import library.mgiandia.com.androidlibrary.domain.Publisher;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerCategoryDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerDAOMemory;
import library.mgiandia.com.androidlibrary.util.Money;

public class InitializeData
{
    private static void add_one_book_copy(Book book)
    {
        ItemDAO item = new ItemDAOMemory();
        Item item_tmp = new Item(1);
        item_tmp.setBook(book);
        item.save(item_tmp);
    }

    public static void init()
    {
        PublisherDAO publisherDAO = new PublisherDAOMemory();

        publisherDAO.save(new Publisher(publisherDAO.next_id(), "McGraw-Hill Education", new Address("Αιόλου", "10", "Αθήνα",
                        new ZipCode("100"), "Ελλάδα"), new EmailAddress("akr.agam@gmail.com"), new TelephoneNumber("2101070741")));

        publisherDAO.save(new Publisher(publisherDAO.next_id(), "Wiley", new Address("Ακαδημίας", "22", "Αθήνα",
                        new ZipCode("129"), "Ελλάδα"), new EmailAddress("gain.agg@gmail.com"), new TelephoneNumber("2102070991")));


        AuthorDAO authorDAO = new AuthorDAOMemory();

        authorDAO.save(new Author(authorDAO.next_id(), "Ευάγγελος", "Αβέρωφ"));
        authorDAO.save(new Author(authorDAO.next_id(), "Χρήστος", "Βυζάντιος"));
        authorDAO.save(new Author(authorDAO.next_id(), "Απόστολος", "Γεωργιάδης"));
        authorDAO.save(new Author(authorDAO.next_id(), "Άλκης", "Θρύλος"));
        authorDAO.save(new Author(authorDAO.next_id(), "Βαγγέλης", "Ηλιόπουλος"));


        BorrowerCategoryDAO borrowerCategoryDao = new BorrowerCategoryDAOMemory();

        borrowerCategoryDao.save(new BorrowerCategory("Προπτυχιακός Φοιτητής", 7 , 2, Money.euros(10)));
        borrowerCategoryDao.save(new BorrowerCategory("Μεταπτυχιακός Φοιτητής", 14 , 3, Money.euros(7)));
        borrowerCategoryDao.save(new BorrowerCategory("Διδακτορικός Φοιτητής", 21 , 4, Money.euros(5)));
        borrowerCategoryDao.save(new BorrowerCategory("Μέλος Δ.Ε.Π.", 21 , 4, Money.euros(5)));
        borrowerCategoryDao.save(new BorrowerCategory("Καθηγητής", 180 , 6, Money.euros(0)));


        BorrowerDAO borrowerDao = new BorrowerDAOMemory();
        //all country names should be from our list, see string.xml

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Αγαμέμνων", "Ακρίδας",
            new Address("Αιόλου", "10", "Αθήνα", new ZipCode("100"), "Ελλάδα"),
            new EmailAddress("akr.agam@gmail.com"), new TelephoneNumber("2101070741"), borrowerCategoryDao.find("Προπτυχιακός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Μανόλης", "Γιακουμάκης",
            new Address("Αιόλου", "11", "Αθήνα", new ZipCode("119"), "Ελλάδα"),
            new EmailAddress("mgia@aueb.gr"), new TelephoneNumber("2108203292"), borrowerCategoryDao.find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Άγγελος", "Γιαννόπουλος",
            new Address("Ακαδημίας", "22", "Αθήνα", new ZipCode("129"), "Ελλάδα"),
            new EmailAddress("gain.agg@gmail.com"), new TelephoneNumber("2102070991"), borrowerCategoryDao.find("Μεταπτυχιακός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Βασίλης", "Δραγούμης",
            new Address("Ερμού", "100", "Αθήνα", new ZipCode("150"), "Ελλάδα"),
            new EmailAddress("bal.drag@gmail.com"), new TelephoneNumber("2101070911"), borrowerCategoryDao.find("Διδακτορικός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Άρτεμις", "Λύτρου",
            new Address("Ερμού", "101", "Αθήνα", new ZipCode("150"), "Ελλάδα"),
            new EmailAddress("art.lutou@gmail.com"), new TelephoneNumber("2102071912"), borrowerCategoryDao.find("Διδακτορικός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Γερακίνα", "Λούπη",
            new Address("Πανεπιστημίου", "122", "Αθήνα", new ZipCode("170"), "Ελλάδα"),
            new EmailAddress("ger.louph@gmail.com"), new TelephoneNumber("2101271123"), borrowerCategoryDao.find("Διδακτορικός Φοιτητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Mario", "Cox",
            new Address("Glenwood St. NE", "1807", "Palm Bay FL", new ZipCode("32907"), "Ηνωμένο Βασίλειο"),
            new EmailAddress("mario.lee@gmail.com"), new TelephoneNumber("5417543010"), borrowerCategoryDao.find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Αντρέας", "Βυζάντιος",
            new Address("Πανεπιστημίου", "123", "Αθήνα", new ZipCode("170"), "Ελλάδα"),
            new EmailAddress("adre.bizant@gmail.com"), new TelephoneNumber("2101271131"), borrowerCategoryDao.find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Νίκος", "Διαμαντίδης",
            new Address("Πανεπιστημίου", "27", "Αθήνα", new ZipCode("1229"), "Ελλάδα"),
            new EmailAddress("nad@aueb.gr"), new TelephoneNumber("2108203292"), borrowerCategoryDao.find("Καθηγητής")));

        borrowerDao.save(new Borrower(borrowerDao.next_id(), "Ασήμω", "Παπαδοπούλου",
            new Address("Ακαδημίας", "27", "Αθήνα", new ZipCode("129"), "Ελλάδα"),
            new EmailAddress("simo.papadop@gmail.com"), new TelephoneNumber("2102392110"), borrowerCategoryDao.find("Καθηγητής")));

        BookDAO bookDao = new BookDAOMemory();

        bookDao.save(new Book(bookDao.next_id(), "Don Quixote", new ISBN("123456"), "978-3-16", 2012, publisherDAO.find(2)));
        bookDao.save(new Book(bookDao.next_id(), "The Odyssey", new ISBN("738912"), "123-4-56", 2000, publisherDAO.find(1)));

        //add a copy per book
        add_one_book_copy(bookDao.find(1));
        add_one_book_copy(bookDao.find(2));

        //publishers are auto informed about the added books

        //now, link authors with books
        authorDAO.find(1).addBook(bookDao.find(1));
        authorDAO.find(2).addBook(bookDao.find(1));
        authorDAO.find(3).addBook(bookDao.find(1));

        authorDAO.find(3).addBook(bookDao.find(2));
        authorDAO.find(4).addBook(bookDao.find(2));

    }
}
