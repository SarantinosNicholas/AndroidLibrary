package library.mgiandia.com.androidlibrary.view.Borrower.AddEditBorrower;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerCategoryDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerDAOMemory;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AddEditBorrowerActivity extends AppCompatActivity implements AddEditBorrowerView
{
    public void showErrorMessage(String title, String message)
    {
        new AlertDialog.Builder(AddEditBorrowerActivity.this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null).create().show();
    }

    public void successfullyFinishActivity(String message)
    {
        Intent retData = new Intent();
        retData.putExtra("message_to_toast", message);
        setResult(RESULT_OK, retData);
        finish();
    }

    public String getFirstName()
    {
        return ((EditText)findViewById(R.id.edit_text_first_name)).getText().toString().trim();
    }

    public String getLastName()
    {
        return ((EditText)findViewById(R.id.edit_text_last_name)).getText().toString().trim();
    }

    public Integer getCategoryPosition()
    {
        return ((Spinner)findViewById(R.id.edit_text_category_name)).getSelectedItemPosition()+1;
    }

    public String getPhone()
    {
        return ((EditText)findViewById(R.id.edit_text_telephone_name)).getText().toString().trim();
    }

    public String getEmail()
    {
        return ((EditText)findViewById(R.id.edit_text_email_name)).getText().toString().trim();
    }

    public Integer getCountryPosition()
    {
        return ((Spinner)findViewById(R.id.edit_text_country_name)).getSelectedItemPosition();
    }

    public String getAddressCity()
    {
        return ((EditText)findViewById(R.id.edit_text_city_name)).getText().toString().trim();
    }

    public String getAddressStreet()
    {
        return ((EditText)findViewById(R.id.edit_text_street_name)).getText().toString().trim();
    }

    public String getAddressNumber()
    {
        return ((EditText)findViewById(R.id.edit_text_number_name)).getText().toString().trim();
    }

    public String getAddressPostalCode()
    {
        return ((EditText)findViewById(R.id.edit_text_zip_name)).getText().toString().trim();
    }

    public Integer getAttachedBorrowerID()
    {
        return this.getIntent().hasExtra("borrower_id") ? this.getIntent().getExtras().getInt("borrower_id") : null;
    }

    public void setFirstName(String value)
    {
        ((EditText)findViewById(R.id.edit_text_first_name)).setText(value);
    }

    public void setLastName(String value)
    {
        ((EditText)findViewById(R.id.edit_text_last_name)).setText(value);
    }

    public void setCategoryPosition(Integer value)
    {
        ((Spinner)findViewById(R.id.edit_text_category_name)).setSelection(value-1);
    }

    public void setPhone(String value)
    {
        ((EditText)findViewById(R.id.edit_text_telephone_name)).setText(value);
    }

    public void setEmail(String value)
    {
        ((EditText)findViewById(R.id.edit_text_email_name)).setText(value);
    }

    public void setCountryPosition(Integer value)
    {
        ((Spinner)findViewById(R.id.edit_text_country_name)).setSelection(value);
    }

    public void setAddressCity(String value)
    {
        ((EditText)findViewById(R.id.edit_text_city_name)).setText(value);
    }

    public void setAddressStreet(String value)
    {
        ((EditText)findViewById(R.id.edit_text_street_name)).setText(value);
    }

    public void setAddressNumber(String value)
    {
        ((EditText)findViewById(R.id.edit_text_number_name)).setText(value);
    }

    public void setAddressPostalCode(String value)
    {
        ((EditText)findViewById(R.id.edit_text_zip_name)).setText(value);
    }

    public void setPageName(String value)
    {
        getSupportActionBar().setTitle(value);
    }

    public void setCategoryList(List<String> names)
    {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.edit_text_category_name)).setAdapter(adapter);
    }

    public void setCountryList(List<String> names)
    {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.edit_text_country_name)).setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_borrower);
        final AddEditBorrowerPresenter presenter = new AddEditBorrowerPresenter(this, new BorrowerDAOMemory(), new BorrowerCategoryDAOMemory(), Arrays.asList(getResources().getStringArray(R.array.country_names)));

        findViewById(R.id.complete_registration_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presenter.onSaveBorrower();
            }
        });
    }
}
