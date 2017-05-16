package library.mgiandia.com.androidlibrary.view.Borrower.DisplayLoans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.memorydao.BorrowerDAOMemory;

public class DisplayLoansActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_loans);
        Borrower borrower = new BorrowerDAOMemory().find(this.getIntent().getExtras().getInt("borrower_id"));
        getSupportActionBar().setTitle("Δάνεια Δανειζομένου #"+borrower.getBorrowerNo());

        //
    }
}
