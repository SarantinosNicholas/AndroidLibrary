package library.mgiandia.com.androidlibrary.view.Book.ManageItems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.memorydao.BookDAOMemory;

public class ManageItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);
        Book book = new BookDAOMemory().find(this.getIntent().getExtras().getInt("book_id"));
        getSupportActionBar().setTitle("Αντίτυπα Βιβλίου #"+book.getID());
    }
}
