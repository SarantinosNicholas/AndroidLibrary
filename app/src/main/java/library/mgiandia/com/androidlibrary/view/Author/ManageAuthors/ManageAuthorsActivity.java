package library.mgiandia.com.androidlibrary.view.Author.ManageAuthors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.view.Author.AddAuthor.AddEditAuthorActivity;
import library.mgiandia.com.androidlibrary.view.Author.AuthorDetails.AuthorDetailsActivity;

public class ManageAuthorsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    private ListView item_list_view;
    private SearchView search_list_view;
    private library.mgiandia.com.androidlibrary.view.Author.ManageAuthors.AuthorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_items);

        item_list_view = (ListView) findViewById(R.id.item_list_view);

        adapter = new library.mgiandia.com.androidlibrary.view.Author.ManageAuthors.AuthorAdapter(this);
        item_list_view.setAdapter(adapter);
        item_list_view.setTextFilterEnabled(true);

        final Context new_context = this;
        item_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(new_context, AuthorDetailsActivity.class);
                intent.putExtra("author_id", ((Author)parent.getItemAtPosition(position)).getID());
                startActivityForResult(intent, 1/*request code 1, can be any integer*/);
            }
        });

        //search
        search_list_view = (SearchView) findViewById(R.id.items_list_search_view);

        search_list_view.setIconifiedByDefault(false);
        search_list_view.setOnQueryTextListener(this);
        //done

        final Button add_new_button = (Button) findViewById(R.id.item_add_new);
        add_new_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), AddEditAuthorActivity.class);
                startActivityForResult(intent, 0/*request code 0, can be any integer*/);
            }
        });
    }

    public boolean onQueryTextChange(String text)
    {
        if (TextUtils.isEmpty(text))
            item_list_view.clearTextFilter();
        else
            item_list_view.setFilterText(text);

        return true;
    }

    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    private void clear_search_bar()
    {
        search_list_view.setQuery("", false);//clear the search view if there was text there
        search_list_view.clearFocus();

        adapter.reload();//reload the list
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {//we intentionally don't make this code more compact, so we get what's going on
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0)//if "add new user activity finished"
        {
            if(resultCode == Activity.RESULT_OK)//and a new user was actually added. this won't fire if the back button was just pressed
            {
                clear_search_bar();
                Toast.makeText(ManageAuthorsActivity.this, data.getStringExtra("message_to_toast"), Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 1)//if "delete user (found on user details) activity finished"
        {
            clear_search_bar();//when this activity exits always refresh the list because it's possible a user was editted or deleted

            if(resultCode == Activity.RESULT_OK)//and the user was actually deleted
                Toast.makeText(ManageAuthorsActivity.this, data.getStringExtra("message_to_toast"), Toast.LENGTH_LONG).show();
        }
    }
}


