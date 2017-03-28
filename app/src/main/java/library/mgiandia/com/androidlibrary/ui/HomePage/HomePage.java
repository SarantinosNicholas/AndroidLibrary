package library.mgiandia.com.androidlibrary.ui.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.ui.ManageBooks.ManageBooks;
import library.mgiandia.com.androidlibrary.ui.ManageBorrowers.ManageBorrowers;

import static library.mgiandia.com.androidlibrary.ui.HomePage.InitializeData.initialize_borrower_categories;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ((Button) findViewById(R.id.manage_borrowers_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ManageBorrowers.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.manage_books_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ManageBooks.class);
                startActivity(intent);
            }
        });

        initialize_borrower_categories();
        InitializeData.initialize_borrowers();
    }
}