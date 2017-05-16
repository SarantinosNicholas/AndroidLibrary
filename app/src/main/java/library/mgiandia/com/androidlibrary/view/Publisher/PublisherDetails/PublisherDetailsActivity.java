package library.mgiandia.com.androidlibrary.view.Publisher.PublisherDetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.domain.Publisher;
import library.mgiandia.com.androidlibrary.memorydao.PublisherDAOMemory;
import library.mgiandia.com.androidlibrary.view.Book.ManageBooks.ManageBooksActivity;
import library.mgiandia.com.androidlibrary.view.Publisher.AddPublisher.AddEditPublisherActivity;

public class PublisherDetailsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_details);

        int publisher_id = this.getIntent().getExtras().getInt("publisher_id");
        getSupportActionBar().setTitle("Εκδοτικός Οίκος #"+publisher_id);

        final Publisher cur = new PublisherDAOMemory().find(publisher_id);
        //nothing will be null, since we have absolute control over data entry

        ((TextView)findViewById(R.id.text_first_name)).setText(cur.getName());
        ((TextView)findViewById(R.id.text_user_id)).setText("#"+cur.getID());
        ((TextView)findViewById(R.id.text_telephone)).setText(cur.getTelephone().getTelephoneNumber());
        ((TextView)findViewById(R.id.text_email)).setText(cur.getEMail().getAddress());

        ((TextView)findViewById(R.id.text_country)).setText(cur.getAddress().getCountry());
        ((TextView)findViewById(R.id.text_city)).setText(cur.getAddress().getCity());
        ((TextView)findViewById(R.id.text_street)).setText(cur.getAddress().getStreet());
        ((TextView)findViewById(R.id.text_number)).setText(cur.getAddress().getNumber());
        ((TextView)findViewById(R.id.text_zip)).setText(cur.getAddress().getZipCode().getCode());

        int books_written = cur.getBooks().size();
        String one_book = " Βιβλίο";
        String many_books = " Βιβλία";

        ((TextView)findViewById(R.id.books_published_text)).setText(books_written+""+(books_written == 1 ? one_book : many_books));

        findViewById(R.id.edit_user_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), AddEditPublisherActivity.class);
                intent.putExtra("publisher_id", cur.getID());
                startActivityForResult(intent, 2/*request code 0, can be any integer*/);
            }
        });

        findViewById(R.id.display_books_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), ManageBooksActivity.class);
                intent.putExtra("publisher_id", cur.getID());
                startActivityForResult(intent, 100/*request code 100, can be any integer*/);//we don't care about the result here
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {//we intentionally don't make this code more compact, so we get what's going on
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2)//if the edit user activity returned
        {
            if(resultCode == Activity.RESULT_OK)//and an actual edit happened
            {
                recreate();//recreate this activity so the changes are going to be visible, because the user was edited
                Toast.makeText(PublisherDetailsActivity.this, data.getStringExtra("message_to_toast"), Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 100)
            recreate();
    }
}
