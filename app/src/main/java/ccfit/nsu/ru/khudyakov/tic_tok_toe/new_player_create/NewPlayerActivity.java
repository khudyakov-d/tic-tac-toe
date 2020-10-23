package ccfit.nsu.ru.khudyakov.tic_tok_toe.new_player_create;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.BaseActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.new_game_menu.NewGameMenuActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class NewPlayerActivity extends BaseActivity implements NewPlayerView {
    private static int RESULT_LOAD_IMAGE = 1;
    private Uri imageUri;
    private NewPlayerPresenter newPlayerPresenter;

    public static void start(final Context context) {
        Intent intent = new Intent(context, NewPlayerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_player_form);

        final EditText nicknameText = findViewById(R.id.player_nickname_edit_text);
        ImageButton imageButton = findViewById(R.id.upload_image);

        imageButton.setOnClickListener(arg0 -> {

            Intent loadIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(loadIntent, RESULT_LOAD_IMAGE);
        });

        Button confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(arg0 -> {
            if (nicknameText.getText().toString().equals("") || imageUri == null) {
                showError("Fields not correct");
            } else {
                newPlayerPresenter.addPlayer(new Player(nicknameText.getText().toString(), imageUri));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            imageUri = data.getData();
        } else {
            showError("Can't upload picture form gallery");
        }
    }

    @Override
    public void createNewPlayer() {
        Intent intent = new Intent(this, NewGameMenuActivity.class);

        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected MvpPresenter<NewPlayerView> createPresenter() {
        newPlayerPresenter = NewPlayerPresenter.createPresenter();
        return newPlayerPresenter;
    }

    @Override
    protected NewPlayerView getMvpView() {
        return this;
    }
}
