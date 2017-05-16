package library.mgiandia.com.androidlibrary.view.Book.BookDetails;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.memorydao.BookDAOMemory;
import library.mgiandia.com.androidlibrary.view.Book.AddEditBook.AddEditBookActivity;
import library.mgiandia.com.androidlibrary.view.Book.ManageItems.ManageItemsActivity;

public class BookDetailsActivity extends AppCompatActivity
{
    private TextView create_plain_text_view(String text, double weight, int color_resource)
    {
        TextView tx = new TextView(this);
        tx.setText(text);
        tx.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT, (float)weight));
        tx.setGravity(Gravity.LEFT);
        tx.setMaxLines(1);
        tx.setTextColor(getResources().getColor(color_resource));
        tx.setTextSize(16);
        tx.setPadding(12, 12, 12, 12);

        return tx;
    }

    private TableRow add_author_to_table(String title, String details) {
        //we can possibly improve the padding (but make sure items fit)
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.setWeightSum(7);

        tr.addView(create_plain_text_view(title, 3, R.color.colorWhite));
        tr.addView(create_plain_text_view(details, 4, R.color.colorSemiWhite));

        return tr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        int book_id = this.getIntent().getExtras().getInt("book_id");
        getSupportActionBar().setTitle("Βιβλίο #"+book_id);

        final Book cur = new BookDAOMemory().find(book_id);
        //nothing will be null, since we have absolute control over data entry

        ((TextView)findViewById(R.id.text_book_id)).setText("#"+cur.getID());
        ((TextView)findViewById(R.id.text_book_title)).setText(cur.getTitle());
        ((TextView)findViewById(R.id.text_book_publisher)).setText(cur.getPublisher().getName());
        ((TextView)findViewById(R.id.text_book_isbn)).setText(cur.getIsbn().getValue());
        ((TextView)findViewById(R.id.text_book_publication)).setText(cur.getPublication());
        ((TextView)findViewById(R.id.text_book_publicationyear)).setText(cur.getPublicationYear()+"");

        int copies_we_have = cur.getItems().size();
        ((TextView)findViewById(R.id.text_book_copies)).setText(copies_we_have+""+(copies_we_have == 1 ? " Αντίτυπο" : " Αντίτυπα"));

        findViewById(R.id.edit_book_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), AddEditBookActivity.class);
                intent.putExtra("book_id", cur.getID());
                startActivityForResult(intent, 2/*request code 0, can be any integer*/);
            }
        });

        findViewById(R.id.display_copies_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), ManageItemsActivity.class);
                intent.putExtra("book_id", cur.getID());
                startActivityForResult(intent, 100/*request code 100, can be any integer*/);//we don't care about the result here
            }
        });

        TableLayout table = (TableLayout)findViewById(R.id.author_parent_layout);

        ArrayList<Author> authors = new ArrayList<Author>(cur.getAuthors());

        for(int i = 0; i < authors.size(); i++)
            table.addView(add_author_to_table("#"+(i+1), authors.get(i).getLastName()+" "+authors.get(i).getFirstName()));
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
                Toast.makeText(BookDetailsActivity.this, data.getStringExtra("message_to_toast"), Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 100)
            recreate();
    }
}
