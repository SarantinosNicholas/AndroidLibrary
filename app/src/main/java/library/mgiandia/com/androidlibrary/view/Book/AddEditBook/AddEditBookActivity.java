package library.mgiandia.com.androidlibrary.view.Book.AddEditBook;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.dao.BookDAO;
import library.mgiandia.com.androidlibrary.dao.ItemDAO;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.domain.ISBN;
import library.mgiandia.com.androidlibrary.domain.Item;
import library.mgiandia.com.androidlibrary.domain.Publisher;
import library.mgiandia.com.androidlibrary.memorydao.AuthorDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.BookDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.ItemDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.PublisherDAOMemory;
import library.mgiandia.com.androidlibrary.view.Util.MultiSelectSpinner;

public class AddEditBookActivity extends AppCompatActivity
{
    private boolean []is_edit_text_valid;

    private String add_or_edit_book(Book existing_book)
    {
        BookDAO book = new BookDAOMemory();

        int bookNo = (existing_book == null ? book.next_id() : existing_book.getID());

        String
                book_title = ((EditText)findViewById(R.id.edit_text_book_title)).getText().toString().trim(),
                isbn = ((EditText)findViewById(R.id.edit_text_isbn)).getText().toString().trim(),
                publication = ((EditText)findViewById(R.id.edit_text_publication)).getText().toString().trim(),
                publicationyear = ((EditText)findViewById(R.id.edit_text_publicationyear)).getText().toString().trim();

        int publisher = ((Spinner)findViewById(R.id.edit_text_publisher)).getSelectedItemPosition();

        boolean[] author_indexes = ((MultiSelectSpinner)findViewById(R.id.edit_text_authors)).getItemsIndexes();

        if(existing_book == null)//add
        {
            //create a new item and add it
            Book book_tmp = new Book(bookNo, book_title, new ISBN(isbn), publication, Integer.parseInt(publicationyear), new PublisherDAOMemory().find(publisher));
            book.save(book_tmp);

            ItemDAO item = new ItemDAOMemory();
            Item item_tmp = new Item(1);
            item_tmp.setBook(book_tmp);

            item.save(item_tmp);

            for(int i = 0; i < author_indexes.length; i++)
                if(author_indexes[i]) new AuthorDAOMemory().find(i+1/*because item ids start from 1*/).addBook(book_tmp);

            return "Επιτυχής Προσθήκη του Βιβλίου '"+book_title+"'!";
        }
        else//update
        {
            existing_book.setTitle(book_title);
            existing_book.setIsbn(new ISBN(isbn));
            existing_book.setPublication(publication);
            existing_book.setPublicationYear(Integer.parseInt(publicationyear));
            existing_book.setPublisher(new PublisherDAOMemory().find(publisher));

            for(Author old_author : existing_book.getAuthors())
                old_author.removeBook(existing_book);

            for(int i = 0; i < author_indexes.length; i++)
                if(author_indexes[i]) new AuthorDAOMemory().find(i+1/*because item ids start from 1*/).addBook(existing_book);

            return "Επιτυχής Τροποποίηση του Βιβλίου '"+book_title+"'!";//refer to as by new name
        }
    }

    private void simple_alert(String msg)
    {
        new AlertDialog.Builder(AddEditBookActivity.this)
                .setCancelable(true)
                .setTitle("Σφάλμα!")
                .setMessage(msg)
                .setPositiveButton("ΟΚ", null).create().show();
    }

    private void initialize_save_button(final Book existing_book)
    {
        final Button button = (Button) findViewById(R.id.complete_registration_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                View vv = getCurrentFocus();
                if(vv != null)
                    vv.clearFocus();//so validation code will fire

                for(int i = 0; i < is_edit_text_valid.length; i++)
                    if(!is_edit_text_valid[i])
                    {
                        simple_alert("Παρακαλώ συμπληρώστε όλα τα πεδία με αποδεκτές τιμές.");
                        return;
                    }

                String tmp = are_spinners_valid();

                if(tmp.equals(""))
                {
                    Intent retData = new Intent();
                    retData.putExtra("message_to_toast", add_or_edit_book(existing_book));
                    setResult(RESULT_OK, retData);
                    finish();//kill this activity
                }
                else
                    simple_alert(tmp);
            }
        });
    }

    private void set_view_listener(final int i, EditText view)
    {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus && v instanceof EditText)
                {
                    EditText edit_v = (EditText)v;

                    String check_res = are_edit_text_data_valid(edit_v);

                    is_edit_text_valid[i] = check_res.equals("");

                    if(check_res.equals(""))
                        edit_v.setError(null);
                    else
                        edit_v.setError(check_res);
                }
            }
        });
    }

    private String/*empty on valid, else reason is given as string*/ are_spinners_valid()
    {
        if(((Spinner)findViewById(R.id.edit_text_publisher)).getSelectedItemPosition() == 0)
            return "Επιλέξτε Εκδοτικό Οίκο.";
        else if(((MultiSelectSpinner)findViewById(R.id.edit_text_authors)).getItemsIndexesNo() == 0)
            return "Επιλέξτε τουλάχιστον ένα Συγγραφέα.";
        else
            return "";
    }

    private String/*empty on valid, else reason is given as string*/ are_edit_text_data_valid(EditText view)
    {
        int view_id = view.getId();

        if(view_id == R.id.edit_text_book_title || view_id == R.id.edit_text_isbn ||  view_id == R.id.edit_text_publication)
        {
            int size = view.getText().toString().trim().length();

            if (size < 2 || size > 15)
                return "Συμπληρώστε 2 έως 15 χαρακτήρες.";
            else
                return "";
        }
        else if(view_id == R.id.edit_text_publicationyear)
        {
            String str = view.getText().toString().trim();
            int size = str.length();

            if (size != 4)
                return "Συμπληρώστε 4 χαρακτήρες.";
            else //check if all digits
            {
                for(int i = 0; i < size; i++)
                    if(!Character.isDigit(str.charAt(i)))
                        return "Επιτρέπονται μόνο αριθμοί.";

                return "";
            }
        }

        return "";
    }

    private void init_spinner(int view, List<String> values)
    {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(view)).setAdapter(adapter);
    }

    private void initialize_spinners()
    {
        List<String> publisher_names = new ArrayList<String>();
        publisher_names.add("Επιλέξτε");

        for(Publisher p : new PublisherDAOMemory().findAll())
            publisher_names.add(p.getName());

        init_spinner(R.id.edit_text_publisher, publisher_names);

        List<String> author_names = new ArrayList<String>();

        for(Author p : new AuthorDAOMemory().findAll())
            author_names.add(p.getLastName()+" "+p.getFirstName());

        MultiSelectSpinner multiSpinner = (MultiSelectSpinner) findViewById(R.id.edit_text_authors);
        multiSpinner.setItems(author_names);
    }

    private void load_values_from_book(Book book)
    {
        ((EditText)findViewById(R.id.edit_text_book_title)).setText(book.getTitle());
        ((EditText)findViewById(R.id.edit_text_isbn)).setText(book.getIsbn().toString());
        ((EditText)findViewById(R.id.edit_text_publication)).setText(book.getPublication());
        ((EditText)findViewById(R.id.edit_text_publicationyear)).setText(book.getPublicationYear()+"");

        ((Spinner)findViewById(R.id.edit_text_publisher)).setSelection(book.getPublisher().getID());

        List<Integer> itemIndexes = new ArrayList<Integer>();
        for(Author author : book.getAuthors())
            itemIndexes.add(author.getID()-1);

        ((MultiSelectSpinner)findViewById(R.id.edit_text_authors)).setSelectedItems(itemIndexes);
    }

    private void initiliaze_edit_mode(Book book)
    {
        getSupportActionBar().setTitle("Βιβλίο #" + book.getID());//set the new value at top bar
        load_values_from_book(book);//fill the fields
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        int []edit_text_ids = new int[]{R.id.edit_text_book_title, R.id.edit_text_isbn, R.id.edit_text_publication, R.id.edit_text_publicationyear};

        for(int i = 0; i < edit_text_ids.length; i++) set_view_listener(i, (EditText) findViewById(edit_text_ids[i]));

        initialize_spinners();

        is_edit_text_valid = new boolean[edit_text_ids.length];//default false

        if(this.getIntent().hasExtra("book_id"))//if true we are talking about an edit
        {
            for(int i = 0; i < is_edit_text_valid.length; i++) is_edit_text_valid[i] = true;//set booleans to true
            Book book = new BookDAOMemory().find(this.getIntent().getExtras().getInt("book_id"));
            initiliaze_edit_mode(book);
            initialize_save_button(book);
        }
        else
        {
            ((MultiSelectSpinner)findViewById(R.id.edit_text_authors)).setSelectedItems(new ArrayList<Integer>());
            initialize_save_button(null);
        }
    }
}
