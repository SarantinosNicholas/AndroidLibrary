package library.mgiandia.com.androidlibrary.view.Items.ManageItems;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.memorydao.BookDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.ItemDAOMemory;
import library.mgiandia.com.androidlibrary.util.Quadruple;
import library.mgiandia.com.androidlibrary.view.Util.AdvancedListAdapter;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class ManageItemsActivity extends AppCompatActivity implements ManageItemsView, SearchView.OnQueryTextListener
{
    ManageItemsPresenter presenter;

    private ListView itemListView;
    private SearchView searchListView;
    private AdvancedListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_items);

        adapter = new AdvancedListAdapter(this);

        itemListView = (ListView) findViewById(R.id.item_list_view);
        itemListView.setAdapter(adapter);
        itemListView.setTextFilterEnabled(true);

        searchListView = (SearchView) findViewById(R.id.items_list_search_view);
        searchListView.setIconifiedByDefault(false);
        searchListView.setOnQueryTextListener(this);

        presenter = new ManageItemsPresenter(this, new BookDAOMemory(), new ItemDAOMemory());

        findViewById(R.id.item_add_new).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                presenter.onAddNewItem();
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                presenter.onClickItem(((Quadruple)parent.getItemAtPosition(position)).getUID());
            }
        });
    }

    public boolean onQueryTextChange(String text)
    {
        if (TextUtils.isEmpty(text))
            itemListView.clearTextFilter();
        else
            itemListView.setFilterText(text);

        return true;
    }

    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    private void clear_search_bar()
    {
        searchListView.setQuery("", false);
        searchListView.clearFocus();
        presenter.onLoadSource();
    }

    public void loadSource(List<Quadruple> input)
    {
        adapter.loadSource(input);
    }

    private void makeAlert(String title, String message, boolean hasCancel, DialogInterface.OnClickListener okListener)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setCancelable(true).setTitle(title).setMessage(message).setPositiveButton(R.string.ok, okListener);
        if(hasCancel) dialog.setNegativeButton(R.string.cancel, null);
        dialog.create().show();
    }

    public void newItemAddAlert(String title, String message)
    {
        makeAlert(title, message, true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.doAddNewItem();
            }
        });
    }

    public void newItemStateSelectAlert(int uid, String title, String message)
    {
        final int tmp = uid;

        makeAlert(title, message, true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onChangeItemState(tmp);
            }
        });
    }

    public void refresh()
    {
        clear_search_bar();
    }

    public void showToast(String value)
    {
        Toast.makeText(this, value, Toast.LENGTH_LONG).show();
    }

    public void showAlert(String title, String message)
    {
        makeAlert(title, message, false, null);
    }

    public int getAttachedBookID()
    {
        return this.getIntent().getExtras().getInt("book_id");
    }

    public void setPageName(String value)
    {
        getSupportActionBar().setTitle(value);
    }
}
