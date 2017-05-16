package library.mgiandia.com.androidlibrary.view.Publisher.AddPublisher;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Publisher;
import library.mgiandia.com.androidlibrary.memorydao.PublisherDAOMemory;

public class AddEditPublisherActivity extends AppCompatActivity {

    private Hashtable<String, Integer> country_to_pos = new Hashtable<String, Integer>();

    private boolean []is_edit_text_valid;

    private String add_or_edit_publisher(Publisher existing_publisher/* = null if this is a new user add*/)//returns the full name of the saved user
    {
        PublisherDAO publisher = new PublisherDAOMemory();

        int publisherNo = (existing_publisher == null ? publisher.next_id() : existing_publisher.getID());

        String Name = ((EditText)findViewById(R.id.edit_text_first_name)).getText().toString().trim();

        Address address = new Address(
                ((EditText)findViewById(R.id.edit_text_street_name)).getText().toString().trim(),
                ((EditText)findViewById(R.id.edit_text_number_name)).getText().toString().trim(),
                ((EditText)findViewById(R.id.edit_text_city_name)).getText().toString().trim(),
                new ZipCode(((EditText)findViewById(R.id.edit_text_zip_name)).getText().toString().trim()),
                ((Spinner)findViewById(R.id.edit_text_country_name)).getSelectedItem().toString().trim());

        EmailAddress email = new EmailAddress(((EditText)findViewById(R.id.edit_text_email_name)).getText().toString().trim());

        TelephoneNumber telephone = new TelephoneNumber(((EditText)findViewById(R.id.edit_text_telephone_name)).getText().toString().trim());

        if(existing_publisher == null)//add
        {
            publisher.save(new Publisher(publisherNo, Name, address, email, telephone));
            return "Επιτυχής Εγγραφή του '"+Name+"'!";
        }
        else//update
        {
            existing_publisher.setName(Name);
            existing_publisher.setAddress(address);
            existing_publisher.setEMail(email);
            existing_publisher.setTelephone(telephone);

            return "Επιτυχής Τροποποίηση του '"+Name+"'!";//refer to as by new name
        }
    }

    private void initialize_save_button(final Publisher existing_publisher/* = null if this is a new user add*/)
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
                        new AlertDialog.Builder(AddEditPublisherActivity.this)
                                .setCancelable(true)
                                .setTitle("Σφάλμα!")
                                .setMessage("Παρακαλώ συμπληρώστε όλα τα πεδία με αποδεκτές τιμές.")
                                .setPositiveButton("ΟΚ", null).create().show();

                        return;
                    }

                Intent retData = new Intent();
                retData.putExtra("message_to_toast", add_or_edit_publisher(existing_publisher));
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

        if(view_id == R.id.edit_text_first_name ||  view_id == R.id.edit_text_city_name || view_id == R.id.edit_text_street_name)
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(view)).setAdapter(adapter);
    }

    private void initialize_spinners()
    {
        List<String> country_names = Arrays.asList(getResources().getStringArray(R.array.country_names));
        for(int i = 0; i < country_names.size(); i++) country_to_pos.put(country_names.get(i), i);
        init_spinner(R.id.edit_text_country_name, country_names);
    }

    private void load_values_from_publisher(Publisher publisher)
    {
        ((EditText)findViewById(R.id.edit_text_first_name)).setText(publisher.getName());

        ((EditText)findViewById(R.id.edit_text_street_name)).setText(publisher.getAddress().getStreet());
        ((EditText)findViewById(R.id.edit_text_number_name)).setText(publisher.getAddress().getNumber());
        ((EditText)findViewById(R.id.edit_text_city_name)).setText(publisher.getAddress().getCity());
        ((EditText)findViewById(R.id.edit_text_number_name)).setText(publisher.getAddress().getNumber());
        ((EditText)findViewById(R.id.edit_text_zip_name)).setText(publisher.getAddress().getZipCode().getCode());

        Integer val = country_to_pos.get(publisher.getAddress().getCountry());
        ((Spinner)findViewById(R.id.edit_text_country_name)).setSelection(val == null ? 0 : val);//if value not found, use greece.

        ((EditText)findViewById(R.id.edit_text_email_name)).setText(publisher.getEMail().getAddress());
        ((EditText)findViewById(R.id.edit_text_telephone_name)).setText(publisher.getTelephone().getTelephoneNumber());
    }

    private void initiliaze_edit_mode(Publisher publisher)
    {
        getSupportActionBar().setTitle("Εκδοτικός Οίκος #" + publisher.getID());//set the new value at top bar
        load_values_from_publisher(publisher);//fill the fields
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_publisher);

        int []edit_text_ids = new int[]{R.id.edit_text_first_name, R.id.edit_text_telephone_name,
                R.id.edit_text_email_name, R.id.edit_text_city_name, R.id.edit_text_street_name, R.id.edit_text_number_name, R.id.edit_text_zip_name};

        for(int i = 0; i < edit_text_ids.length; i++) set_view_listener(i, (EditText) findViewById(edit_text_ids[i]));

        initialize_spinners();

        is_edit_text_valid = new boolean[edit_text_ids.length];//default false

        if(this.getIntent().hasExtra("publisher_id"))//if true we are talking about an edit
        {
            for(int i = 0; i < is_edit_text_valid.length; i++) is_edit_text_valid[i] = true;//set booleans to true
            Publisher publisher = new PublisherDAOMemory().find(this.getIntent().getExtras().getInt("publisher_id"));
            initiliaze_edit_mode(publisher);
            initialize_save_button(publisher);
        }
        else
            initialize_save_button(null);
    }
}
