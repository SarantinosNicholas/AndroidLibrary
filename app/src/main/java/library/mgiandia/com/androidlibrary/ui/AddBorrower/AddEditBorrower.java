package library.mgiandia.com.androidlibrary.ui.AddBorrower;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.contacts.Address;
import library.mgiandia.com.androidlibrary.contacts.EmailAddress;
import library.mgiandia.com.androidlibrary.contacts.TelephoneNumber;
import library.mgiandia.com.androidlibrary.contacts.ZipCode;
import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerCategoryDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerDAOMemory;

public class AddEditBorrower extends AppCompatActivity
{
    private Hashtable<String, Integer> country_to_pos = new Hashtable<String, Integer>();
    private Hashtable<String, Integer> category_to_pos = new Hashtable<String, Integer>();

    private boolean []is_edit_text_valid;

    private String add_or_edit_borrower(Borrower existing_borrower/* = null if this is a new user add*/)//returns the full name of the saved user
    {
        BorrowerDAO borrower = new BorrowerDAOMemory();

        int borrowerNo = (existing_borrower == null ? borrower.next_id() : existing_borrower.getBorrowerNo());

        String firstName = ((EditText)findViewById(R.id.edit_text_first_name)).getText().toString().trim();
        String lastName = ((EditText)findViewById(R.id.edit_text_last_name)).getText().toString().trim();

        Address address = new Address(
                ((EditText)findViewById(R.id.edit_text_street_name)).getText().toString().trim(),
                ((EditText)findViewById(R.id.edit_text_number_name)).getText().toString().trim(),
                ((EditText)findViewById(R.id.edit_text_city_name)).getText().toString().trim(),
                new ZipCode(((EditText)findViewById(R.id.edit_text_zip_name)).getText().toString().trim()),
                ((Spinner)findViewById(R.id.edit_text_country_name)).getSelectedItem().toString().trim());

        EmailAddress email = new EmailAddress(((EditText)findViewById(R.id.edit_text_email_name)).getText().toString().trim());

        TelephoneNumber telephone = new TelephoneNumber(((EditText)findViewById(R.id.edit_text_telephone_name)).getText().toString().trim());

        BorrowerCategory category = new BorrowerCategoryDAOMemory().find(((Spinner)findViewById(R.id.edit_text_category_name)).getSelectedItem().toString().trim());

        if(existing_borrower == null)//add
        {
            borrower.save(new Borrower(borrowerNo, firstName, lastName, address, email, telephone, category));
            return "Επιτυχής Εγγραφή του '"+lastName+" "+firstName+"'!";
        }
        else//update
        {
            existing_borrower.setFirstName(firstName);
            existing_borrower.setLastName(lastName);
            existing_borrower.setAddress(address);
            existing_borrower.setEmail(email);
            existing_borrower.setTelephone(telephone);
            existing_borrower.setCategory(category);

            return "Επιτυχής Τροποποίηση του '"+lastName+" "+firstName+"'!";//refer to as by new name
        }
    }

    private void initialize_save_button(final Borrower existing_borrower/* = null if this is a new user add*/)
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
                        new AlertDialog.Builder(AddEditBorrower.this)
                                .setCancelable(true)
                                .setTitle("Σφάλμα!")
                                .setMessage("Παρακαλώ συμπληρώστε όλα τα πεδία με αποδεκτές τιμές.")
                                .setPositiveButton("ΟΚ", null).create().show();

                        return;
                    }

                Intent retData = new Intent();
                retData.putExtra("message_to_toast", add_or_edit_borrower(existing_borrower));
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

        if(view_id == R.id.edit_text_first_name || view_id == R.id.edit_text_last_name ||  view_id == R.id.edit_text_city_name || view_id == R.id.edit_text_street_name)
        {
            int size = view.getText().toString().trim().length();

            if (size < 2 || size > 15)
                return "Συμπληρώστε 2 έως 15 χαρακτήρες.";
            else
                return "";
        }
        else if(view_id == R.id.edit_text_number_name || view_id == R.id.edit_text_zip_name)
        {
            String str = view.getText().toString().trim();
            int size = str.length();

            if (size < 1 || size > 10)
                return "Συμπληρώστε 1 έως 10 χαρακτήρες.";
            else //check if all digits
            {
                for(int i = 0; i < size; i++)
                    if(!Character.isDigit(str.charAt(i)))
                        return "Επιτρέπονται μόνο αριθμοί.";

                return "";
            }
        }
        else if(view_id == R.id.edit_text_telephone_name)
        {
            String str = view.getText().toString().trim();
            if(str.length() < 2 || str.length() > 15 || !Patterns.PHONE.matcher(str).matches())
                return "Ο αριθμός δεν είναι ορθός.";
        }
        else if(view_id == R.id.edit_text_email_name)
        {
            String str = view.getText().toString().trim();
            if(str.length() < 2 || str.length() > 100 || !Patterns.EMAIL_ADDRESS.matcher(str).matches())
                return "Το e-mail δεν είναι ορθό.";
        }

        return "";
    }

    private void init_spinner(int view, List<String> values)
    {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((Spinner) findViewById(view)).setAdapter(adapter);
    }

    private void initialize_spinners()
    {
        List<String> category_names = new BorrowerCategoryDAOMemory().findAllNames();
        for(int i = 0; i < category_names.size(); i++) category_to_pos.put(category_names.get(i), i);
        init_spinner(R.id.edit_text_category_name, category_names);

        List<String> country_names = Arrays.asList(getResources().getStringArray(R.array.country_names));
        for(int i = 0; i < country_names.size(); i++) country_to_pos.put(country_names.get(i), i);
        init_spinner(R.id.edit_text_country_name, country_names);
    }

    private void load_values_from_borrower(Borrower borrower)
    {
        ((EditText)findViewById(R.id.edit_text_first_name)).setText(borrower.getFirstName());
        ((EditText)findViewById(R.id.edit_text_last_name)).setText(borrower.getLastName());

        ((EditText)findViewById(R.id.edit_text_street_name)).setText(borrower.getAddress().getStreet());
        ((EditText)findViewById(R.id.edit_text_number_name)).setText(borrower.getAddress().getNumber());
        ((EditText)findViewById(R.id.edit_text_city_name)).setText(borrower.getAddress().getCity());
        ((EditText)findViewById(R.id.edit_text_number_name)).setText(borrower.getAddress().getNumber());
        ((EditText)findViewById(R.id.edit_text_zip_name)).setText(borrower.getAddress().getZipCode().getCode());

        ((Spinner)findViewById(R.id.edit_text_country_name)).setSelection(country_to_pos.get(borrower.getAddress().getCountry()));

        ((EditText)findViewById(R.id.edit_text_email_name)).setText(borrower.getEmail().getAddress());
        ((EditText)findViewById(R.id.edit_text_telephone_name)).setText(borrower.getTelephone().getTelephoneNumber());
        ((Spinner)findViewById(R.id.edit_text_category_name)).setSelection(category_to_pos.get(borrower.getCategory().getDescription()));
    }

    private void initiliaze_edit_mode(Borrower borrower)
    {
        getSupportActionBar().setTitle("Δανειζόμενος #" + borrower.getBorrowerNo());//set the new value at top bar
        load_values_from_borrower(borrower);//fill the fields
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_borrower);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        int []edit_text_ids = new int[]{R.id.edit_text_first_name, R.id.edit_text_last_name, R.id.edit_text_telephone_name,
                R.id.edit_text_email_name, R.id.edit_text_city_name, R.id.edit_text_street_name, R.id.edit_text_number_name, R.id.edit_text_zip_name};

        for(int i = 0; i < edit_text_ids.length; i++) set_view_listener(i, (EditText) findViewById(edit_text_ids[i]));

        initialize_spinners();

        is_edit_text_valid = new boolean[edit_text_ids.length];//default false

        if(this.getIntent().hasExtra("borrower_id"))//if true we are talking about an edit
        {
            for(int i = 0; i < is_edit_text_valid.length; i++) is_edit_text_valid[i] = true;//set booleans to true
            Borrower borrower = new BorrowerDAOMemory().find(this.getIntent().getExtras().getInt("borrower_id"));
            initiliaze_edit_mode(borrower);
            initialize_save_button(borrower);
        }
        else
            initialize_save_button(null);
    }
}
