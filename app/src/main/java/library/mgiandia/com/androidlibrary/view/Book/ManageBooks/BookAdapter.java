package library.mgiandia.com.androidlibrary.view.Book.ManageBooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.mgiandia.com.androidlibrary.R;
import library.mgiandia.com.androidlibrary.domain.Book;
import library.mgiandia.com.androidlibrary.memorydao.AuthorDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.BookDAOMemory;
import library.mgiandia.com.androidlibrary.memorydao.PublisherDAOMemory;
import library.mgiandia.com.androidlibrary.util.PixelUtils;

public class BookAdapter extends BaseAdapter implements Filterable
{
    private Context context;
    private LayoutInflater inflater;
    private List<Book> data_source;
    private ItemFilter mFilter = new ItemFilter();

    String var_name;
    int var_value;

    private List<Book> get_all_books_in_reverse_order()
    {
        //reverse the list using a shallow copy. we do this since we want the newest items to appear at the top
        //another solution is to keep the data reversed on memory but this will mess our data structure so we better not do it
        //just for a bit extra performance

        List<Book> tmp;

        if(var_name.equals("author_id"))
            tmp = new ArrayList<>(new AuthorDAOMemory().find(var_value).getBooks());
        else if(var_name.equals("publisher_id"))
            tmp = new ArrayList<>(new PublisherDAOMemory().find(var_value).getBooks());
        else
            tmp = new BookDAOMemory().findAll();

        List<?> shallowCopy = tmp.subList(0, tmp.size());
        Collections.reverse(shallowCopy);

        return tmp;
    }

    public BookAdapter(Context context, String var_name, int var_value)
    {
        this.var_name = var_name;
        this.var_value = var_value;

        this.context = context;
        data_source = get_all_books_in_reverse_order();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return data_source.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data_source.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = inflater.inflate(R.layout.adapter_item, parent, false);

        Book book = (Book) getItem(position);

        ((TextView) rowView.findViewById(R.id.item_lastname)).setText(book.getTitle());
        ((TextView) rowView.findViewById(R.id.item_name)).setText("Από "+book.getPublisher().getName()+", "+book.getPublicationYear());
        ((TextView) rowView.findViewById(R.id.item_details)).setText("Κωδικός: "+book.getID()+".  Συγγραφείς: "+book.getAuthors().size());
        ((TextView) rowView.findViewById(R.id.item_view_details)).setText(">");

        PixelUtils.draw_initials_image(context, (ImageView) rowView.findViewById(R.id.item_image), book.getTitle());//generated the color based on the full name
        ((TextView) rowView.findViewById(R.id.image_text)).setText((book.getTitle().charAt(0)+""+book.getTitle().charAt(1)).toUpperCase());

        return rowView;
    }

    public void reload()
    {
        data_source.clear();
        data_source.addAll(get_all_books_in_reverse_order());
        notifyDataSetChanged();
    }

    public Filter getFilter()
    {
        return mFilter;
    }

    private class ItemFilter extends Filter
    {
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            data_source.clear();
            data_source.addAll((List<Book>) results.values);

            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            List<Book> tmp = get_all_books_in_reverse_order();//load everything here

            int count = tmp.size();
            final List<Book> ret_list = new ArrayList<Book>(count);

            for (int i = 0; i < count; i++)
            {
                Book book = tmp.get(i);

                String
                        title = book.getTitle().toLowerCase(),
                        publisher = book.getPublisher().getName();

                if (title.contains(filterString) || publisher.contains(filterString))
                    ret_list.add(book);
            }

            results.values = ret_list;
            results.count = ret_list.size();

            return results;
        }
    }
}
