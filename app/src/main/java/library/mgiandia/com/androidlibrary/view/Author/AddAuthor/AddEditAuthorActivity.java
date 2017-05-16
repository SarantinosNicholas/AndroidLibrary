package library.mgiandia.com.androidlibrary.view.Author.AddAuthor;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.dao.AuthorDAO;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.memorydao.AuthorDAOMemory;

public class AddEditAuthorActivity extends AppCompatActivity
{
    private boolean []is_edit_text_valid;

    private String add_or_edit_author(Author existing_author/* = null if this is a new user add*/)//returns the full name of the saved user
    {
        AuthorDAO author = new AuthorDAOMemory();

        int authorNo = (existing_author == null ? author.next_id() : existing_author.getID());

        String
                firstName = ((EditText)findViewById(R.id.edit_text_first_name)).getText().toString().trim(),
                lastName = ((EditText)findViewById(R.id.edit_text_last_name)).getText().toString().trim();

        if(existing_author == null)//add
        {
            author.save(new Author(authorNo, firstName, lastName));
            return "Επιτυχής Εγγραφή του '"+lastName+" "+firstName+"'!";
        }
        else//update
        {
            existing_author.setFirstName(firstName);
            existing_author.setLastName(lastName);

            return "Επιτυχής Τροποποίηση του '"+lastName+" "+firstName+"'!";//refer to as by new name
        }
    }

    private void initialize_save_button(final Author existing_author/* = null if this is a new user add*/)
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
                        new AlertDialog.Builder(AddEditAuthorActivity.this)
                                .setCancelable(true)
                                .setTitle("Σφάλμα!")
                                .setMessage("Παρακαλώ συμπληρώστε όλα τα πεδία με αποδεκτές τιμές.")
                                .setPositiveButton("ΟΚ", null).create().show();

                        return;
                    }

                Intent retData = new Intent();
                retData.putExtra("message_to_toast", add_or_edit_author(existing_author));
                setResult(RESULT_OK, retData);
                finish();//kill this activity
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

    private String/*empty on valid, else reason is given as string*/ are_edit_text_data_valid(EditText view)
    {
        int view_id = view.getId();

        if(view_id == R.id.edit_text_first_name || view_id == R.id.edit_text_last_name)
        {
            int size = view.getText().toString().trim().length();

            if (size < 2 || size > 15)
                return "Συμπληρώστε 2 έως 15 χαρακτήρες.";
            else
                return "";
        }

        return "";
    }

    private void load_values_from_author(Author author)
    {
        ((EditText)findViewById(R.id.edit_text_first_name)).setText(author.getFirstName());
        ((EditText)findViewById(R.id.edit_text_last_name)).setText(author.getLastName());
    }

    private void initiliaze_edit_mode(Author author)
    {
        getSupportActionBar().setTitle("Συγγραφέας #" + author.getID());//set the new value at top bar
        load_values_from_author(author);//fill the fields
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_author);

        int []edit_text_ids = new int[]{R.id.edit_text_first_name, R.id.edit_text_last_name};

        for(int i = 0; i < edit_text_ids.length; i++) set_view_listener(i, (EditText) findViewById(edit_text_ids[i]));

        is_edit_text_valid = new boolean[edit_text_ids.length];//default false

        if(this.getIntent().hasExtra("author_id"))//if true we are talking about an edit
        {
            for(int i = 0; i < is_edit_text_valid.length; i++) is_edit_text_valid[i] = true;//set booleans to true
            Author author = new AuthorDAOMemory().find(this.getIntent().getExtras().getInt("author_id"));
            initiliaze_edit_mode(author);
            initialize_save_button(author);
        }
        else
            initialize_save_button(null);
    }
}
