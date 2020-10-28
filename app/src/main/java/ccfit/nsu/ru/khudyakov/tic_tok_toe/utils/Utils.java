package ccfit.nsu.ru.khudyakov.tic_tok_toe.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;

public class Utils {

    public static void uploadImage(Context context, ImageView imageView, Uri uri) {
        if (context != null) {
            Picasso.get().load(uri).placeholder(R.drawable.empty_image).fit().into(imageView);
        }
    }
}
