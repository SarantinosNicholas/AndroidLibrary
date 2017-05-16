package library.mgiandia.com.androidlibrary.view.Author.ManageAuthors;

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
import library.mgiandia.com.androidlibrary.domain.Author;
import library.mgiandia.com.androidlibrary.memorydao.AuthorDAOMemory;
import library.mgiandia.com.androidlibrary.util.PixelUtils;

public class AuthorAdapter extends BaseAdapter implements Filterable
{
    private Context context;
    private LayoutInflater inflater;
    private List<Author> data_source;
    private ItemFilter mFilter = new ItemFilter();

    private List<Author> get_all_authors_in_reverse_order()
    {
        //reverse the list using a shallow copy. we do this since we want the newest items to appear at the top
        //another solution is to keep the data reversed on memory but this will mess our data structure so we better not do it
        //just for a bit extra performance

        List<Author> tmp = new AuthorDAOMemory().findAll();
        List<?> shallowCopy = tmp.subList(0, tmp.size());
        Collections.reverse(shallowCopy);

        return tmp;
    }

    public AuthorAdapter(Context context)
    {
        this.context = context;
        data_source = get_all_authors_in_reverse_order();
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

        Author author = (Author) getItem(position);

        ((TextView) rowView.findViewById(R.id.item_name)).setText(author.getFirstName());
        ((TextView) rowView.findViewById(R.id.item_lastname)).setText(author.getLastName());
        ((TextView) rowView.findViewById(R.id.item_details)).setText("Σύνολο βιβλίων "+author.getBooks().size());
        ((TextView) rowView.findViewById(R.id.item_view_details)).setText(">");

        PixelUtils.draw_initials_image(context, (ImageView) rowView.findViewById(R.id.item_image), author.getFirstName()+" "+author.getLastName());//generated the color based on the full name
        ((TextView) rowView.findViewById(R.id.image_text)).setText((author.getLastName().charAt(0)+""+author.getFirstName().charAt(0)).toUpperCase());

        return rowView;
    }

    public void reload()
    {
        data_source.clear();
        data_source.addAll(get_all_authors_in_reverse_order());
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
            data_source.addAll((List<Author>) results.values);

            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            List<Author> tmp = get_all_authors_in_reverse_order();//load everything here

            int count = tmp.size();
            final List<Author> ret_list = new ArrayList<Author>(count);

            for (int i = 0; i < count; i++)
            {
                Author author = tmp.get(i);

                String
                        first_name = author.getFirstName().toLowerCase(),
                        last_name = author.getLastName().toLowerCase();

                if (first_name.contains(filterString) || last_name.contains(filterString) || (first_name+" "+last_name).contains(filterString) || (last_name+" "+first_name).contains(filterString))
                    ret_list.add(author);
            }

            results.values = ret_list;
            results.count = ret_list.size();

            return results;
        }
    }
}
