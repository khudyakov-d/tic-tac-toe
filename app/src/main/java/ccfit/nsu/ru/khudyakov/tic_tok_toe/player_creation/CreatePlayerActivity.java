package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_creation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.BaseActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game_menu.GameMenuActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.utils.Utils;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CreatePlayerActivity extends BaseActivity implements CreatePlayerView {
    private static final int RESULT_LOAD_IMAGE = 1;

    private Uri imageUri = null;
    private CreatePlayerPresenter createPlayerPresenter;

    public static void start(final Context context) {
        Intent intent = new Intent(context, CreatePlayerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.player_creation_form);

        final EditText nicknameText = findViewById(R.id.player_nickname_edit_text);
        final ImageButton imageButton = findViewById(R.id.upload_image);

        imageButton.setOnClickListener(arg0 -> {

            Intent loadIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(loadIntent, RESULT_LOAD_IMAGE);
        });

        Button confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(arg0 -> {
            if (nicknameText.getText().toString().equals("")) {
                showError(getString(R.string.player_creation_error));
            } else {
                createPlayerPresenter.addPlayer(new Player(nicknameText.getText().toString(), imageUri));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            imageUri = data.getData();
            Utils.uploadImage(this, (ImageView) findViewById(R.id.player_avatar_image), imageUri);
        } else {
            showError(getString(R.string.image_selection_error));
        }
    }

    @Override
    public void createNewPlayer() {
        Intent intent = new Intent(this, GameMenuActivity.class);

        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected MvpPresenter<CreatePlayerView> createPresenter() {
        createPlayerPresenter = CreatePlayerPresenterFactory.createPresenter(this);
        return createPlayerPresenter;
    }

    @Override
    protected CreatePlayerView getMvpView() {
        return this;
    }
}
