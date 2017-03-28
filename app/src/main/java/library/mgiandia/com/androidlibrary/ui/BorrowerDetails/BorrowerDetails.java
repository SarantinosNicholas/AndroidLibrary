package library.mgiandia.com.androidlibrary.ui.BorrowerDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerDAOMemory;
import library.mgiandia.com.androidlibrary.ui.AddBorrower.AddEditBorrower;
import library.mgiandia.com.androidlibrary.ui.DisplayLoans.DisplayLoans;

public class BorrowerDetails extends AppCompatActivity
{
    private void delete_user_button_init(final Borrower cur)
    {
        final Button button = (Button) findViewById(R.id.delete_user_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new AlertDialog.Builder(BorrowerDetails.this)
                        .setCancelable(true)
                        .setTitle("Διαγραφή Δανειζομένου;")
                        .setMessage("Όλα τα βιβλία που δεν έχουν επιστραφεί θα σημειωθούν ως χαμένα.")
                        .setPositiveButton("ΟΚ, ΔΙΑΓΡΑΦΗ", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                cur.mark_all_open_loans_as_lost();
                                new BorrowerDAOMemory().delete(cur);

                                Intent retData = new Intent();
                                retData.putExtra("message_to_toast", "Επιτυχής Διαγραφή του "+cur.getLastName()+" "+ cur.getFirstName()+"!");
                                setResult(RESULT_OK, retData);
                                finish();//kill this activity
                            }
                        })
                        .setNegativeButton("ΑΚΥΡΩΣΗ", null).create().show();
            }
        });
    }

    private void edit_user_button_init(final Borrower cur)
    {
        ((Button) findViewById(R.id.edit_user_button)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), AddEditBorrower.class);
                intent.putExtra("borrower_id", cur.getBorrowerNo());
                startActivityForResult(intent, 2/*request code 0, can be any integer*/);
            }
        });
    }

    private void display_loans_button(final Borrower cur)
    {
        ((Button) findViewById(R.id.display_loans_button)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), DisplayLoans.class);
                intent.putExtra("borrower_id", cur.getBorrowerNo());
                startActivity(intent);//we don't care about the result here
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_details);

        int borrower_id = this.getIntent().getExtras().getInt("borrower_id");
        getSupportActionBar().setTitle("Δανειζόμενος #"+borrower_id);

        Borrower cur = new BorrowerDAOMemory().find(borrower_id);
        //nothing will be null, since we have absolute control over data entry

        ((TextView)findViewById(R.id.text_first_name)).setText(cur.getFirstName());
        ((TextView)findViewById(R.id.text_last_name)).setText(cur.getLastName());
        ((TextView)findViewById(R.id.text_user_id)).setText("#"+cur.getBorrowerNo());
        ((TextView)findViewById(R.id.text_category)).setText(cur.getCategory().getDescription());
        ((TextView)findViewById(R.id.text_telephone)).setText(cur.getTelephone().getTelephoneNumber());
        ((TextView)findViewById(R.id.text_email)).setText(cur.getEmail().getAddress());

        ((TextView)findViewById(R.id.text_country)).setText(cur.getAddress().getCountry());
        ((TextView)findViewById(R.id.text_city)).setText(cur.getAddress().getCity());
        ((TextView)findViewById(R.id.text_street)).setText(cur.getAddress().getStreet());
        ((TextView)findViewById(R.id.text_number)).setText(cur.getAddress().getNumber());
        ((TextView)findViewById(R.id.text_zip)).setText(cur.getAddress().getZipCode().getCode());

        int loaned_loans = cur.get_ongoing_loans().size();
        int returned_loans = cur.get_returned_loans().size();
        int lost_loans = cur.get_lost_loans().size();
        String one_book = " Βιβλίο";
        String many_books = " Βιβλία";

        ((TextView)findViewById(R.id.books_loaned_text)).setText(loaned_loans+""+(loaned_loans == 1 ? one_book : many_books));
        ((TextView)findViewById(R.id.books_returned_text)).setText(returned_loans+""+(returned_loans == 1 ? one_book : many_books));
        ((TextView)findViewById(R.id.books_lost_text)).setText(lost_loans+""+(lost_loans == 1 ? one_book : many_books));

        delete_user_button_init(cur);
        edit_user_button_init(cur);
        display_loans_button(cur);
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
                Toast.makeText(BorrowerDetails.this, data.getStringExtra("message_to_toast"), Toast.LENGTH_LONG).show();
            }
        }
    }
}
