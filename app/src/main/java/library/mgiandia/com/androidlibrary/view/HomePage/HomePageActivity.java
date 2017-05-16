package library.mgiandia.com.androidlibrary.view.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.memorydao.InitializeData;
import library.mgiandia.com.androidlibrary.view.Author.ManageAuthors.ManageAuthorsActivity;
import library.mgiandia.com.androidlibrary.view.Book.ManageBooks.ManageBooksActivity;
import library.mgiandia.com.androidlibrary.view.Borrower.ManageBorrowers.ManageBorrowersActivity;
import library.mgiandia.com.androidlibrary.view.Publisher.ManagePublishers.ManagePublishersActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViewById(R.id.manage_borrowers_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ManageBorrowersActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.manage_books_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ManageBooksActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.manage_authors_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ManageAuthorsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.manage_publishers_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ManagePublishersActivity.class);
                startActivity(intent);
            }
        });

        InitializeData.init();
    }
}