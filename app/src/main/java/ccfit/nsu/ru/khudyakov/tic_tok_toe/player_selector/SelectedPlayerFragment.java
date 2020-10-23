package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selector;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class SelectedPlayerFragment extends Fragment {
    private static final String NICKNAME_PARAM = "nickname";
    private static final String IMAGE_URI_PARAM = "uri";

    public SelectedPlayerFragment() {
    }

    public static SelectedPlayerFragment newInstance(Player player) {
        SelectedPlayerFragment selectedPlayerFragment = new SelectedPlayerFragment();
        Bundle args = new Bundle();
        args.putString(NICKNAME_PARAM, player.getNickname());
        args.putString(IMAGE_URI_PARAM, player.getImageUri().toString());
        selectedPlayerFragment.setArguments(args);
        return selectedPlayerFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.selected_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ImageView imageView = view.findViewById(R.id.player_avatar_image);
        TextView textView = view.findViewById(R.id.player_nickname_text);

        if (getArguments() != null) {
            Uri uri = Uri.parse((String) getArguments().get(IMAGE_URI_PARAM));

            uploadImage(imageView, uri);

            textView.setText((String) getArguments().get(NICKNAME_PARAM));
        }
    }

    private void uploadImage(ImageView imageView, Uri uri) {
        if (uri != null && getContext() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
