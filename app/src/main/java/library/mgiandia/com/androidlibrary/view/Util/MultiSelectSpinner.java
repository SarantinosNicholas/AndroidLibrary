package library.mgiandia.com.androidlibrary.view.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.List;

public class MultiSelectSpinner extends android.support.v7.widget.AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener
{
    private List<String> items;
    private boolean[] selected_indexes;
    int selected_no;

    public MultiSelectSpinner(Context arg0)
    {
        super(arg0);
    }

    public MultiSelectSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSelectSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int index, boolean checked)
    {
        if(checked) selected_no++;
        else selected_no--;

        selected_indexes[index] = checked;
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new String[]{
                selected_no == 0 ? "Επιλέξτε" : selected_no+" Επιλογές"
        }));
        this.onItemsSelected(selected_indexes);
    }

    @Override
    public boolean performClick()
    {
        new AlertDialog.Builder(getContext())
            .setMultiChoiceItems(items.toArray(new CharSequence[items.size()]), selected_indexes, this)
            .setPositiveButton(android.R.string.ok,
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            })
            .setOnCancelListener(this)
            .show();

        return true;
    }

    public void setSelectedItems(List<Integer> indexes)
    {

        for(int index : indexes)
            onClick(null, index, true);

        onCancel(null);
    }

    public void setItems(List<String> items)
    {
        this.items = items;
        selected_indexes = new boolean[items.size()];
        this.selected_no = 0;
    }

    public boolean[] getItemsIndexes()
    {
        return selected_indexes;
    }

    public int getItemsIndexesNo()
    {
        return selected_no;
    }

    public void onItemsSelected(boolean[] selected)
    {

    }
}
