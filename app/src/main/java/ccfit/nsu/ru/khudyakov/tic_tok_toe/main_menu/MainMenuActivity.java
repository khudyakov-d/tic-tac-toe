package ccfit.nsu.ru.khudyakov.tic_tok_toe.main_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.new_game_menu.NewGameMenuActivity;

public class MainMenuActivity extends AppCompatActivity implements MainMenuView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        initView();
    }

    private void initView() {
        Button newGameButton = findViewById(R.id.new_game_button);

        newGameButton.setOnClickListener(v -> createNewGame());

        Button scoreButton = findViewById(R.id.score_button);

        scoreButton.setOnClickListener(v -> showScore());
    }

    @Override
    public void createNewGame() {
        Intent intent = new Intent(this, NewGameMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void showScore() {

    }
}
