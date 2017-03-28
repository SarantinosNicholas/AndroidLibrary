package library.mgiandia.com.androidlibrary.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import library.mgiandia.com.androidlibrary.R;

/**
 * Created by Shadow on 22/3/2017.
 */

public class PixelUtils
{
    public static int dp_to_px(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int px_to_dp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void draw_initials_image(Context context, ImageView iv, String name)
    {
        int view_width = iv.getLayoutParams().width;
        int view_heigth = iv.getLayoutParams().height;

        int[] color_list = new int[]{R.color.colorSmallBlue, R.color.colorSmallGreen, R.color.colorSmallOrange};
        int color_resource_id = color_list[Math.abs(name.hashCode()) % color_list.length];

        Bitmap bitmap = Bitmap.createBitmap(view_width, view_heigth, Bitmap.Config.ARGB_8888);

        Paint color_paint = new Paint();
        color_paint.setColor(context.getResources().getColor(color_resource_id));

        Canvas canvas = new Canvas(bitmap);
        canvas.translate(view_width/2f, view_heigth/2f);
        canvas.drawCircle(0, 0, 90, color_paint);

        iv.setImageBitmap(bitmap);
    }
}
