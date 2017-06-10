package library.mgiandia.com.androidlibrary.memorydao;

import library.mgiandia.com.androidlibrary.dao.AuthorDAO;
import library.mgiandia.com.androidlibrary.dao.BookDAO;
import library.mgiandia.com.androidlibrary.dao.BorrowerCategoryDAO;
import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.dao.CountryDAO;
import library.mgiandia.com.androidlibrary.dao.Initializer;
import library.mgiandia.com.androidlibrary.dao.ItemDAO;
import library.mgiandia.com.androidlibrary.dao.LoanDAO;
import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;
import library.mgiandia.com.androidlibrary.domain.Item;
import library.mgiandia.com.androidlibrary.domain.Loan;
import library.mgiandia.com.androidlibrary.domain.Publisher;

public class MemoryInitializer extends Initializer {

    @Override
    protected void eraseData() {

        for(Author author : getAuthorDAO().findAll()) {
            getAuthorDAO().delete(author);
        }

        for(Book book : getBookDAO().findAll()) {
            getBookDAO().delete(book);
        }

        for(BorrowerCategory borrowerCategory : getBorrowerCategoryDAO().findAll()) {
            getBorrowerCategoryDAO().delete(borrowerCategory);
        }

        for(Borrower borrower : getBorrowerDAO().findAll()) {
            getBorrowerDAO().delete(borrower);
        }

        for(Item item : getItemDAO().findAll()) {
            getItemDAO().delete(item);
        }

        for(Loan loan : getLoanDAO().findAll()) {
            getLoanDAO().delete(loan);
        }

        for(Publisher publisher : getPublisherDAO().findAll()) {
            getPublisherDAO().delete(publisher);
        }
    }

    @Override
    public AuthorDAO getAuthorDAO()
    {
        return new AuthorDAOMemory();
    }

    @Override
    public BookDAO getBookDAO()
    {
        return new BookDAOMemory();
    }

    @Override
    public BorrowerCategoryDAO getBorrowerCategoryDAO()
    {
        return new BorrowerCategoryDAOMemory();
    }

    @Override
    public BorrowerDAO getBorrowerDAO() {
        return new BorrowerDAOMemory();
    }

    @Override
    public ItemDAO getItemDAO() {
        return new ItemDAOMemory();
    }

    @Override
    public LoanDAO getLoanDAO() {
        return new LoanDAOMemory();
    }

    @Override
    public PublisherDAO getPublisherDAO()
    {
        return new PublisherDAOMemory();
    }

    @Override
    public CountryDAO getCountryDAO()
    {
        return new CountryDAOMemory();
    }
}