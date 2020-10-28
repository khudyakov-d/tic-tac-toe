package ccfit.nsu.ru.khudyakov.tic_tok_toe.game_menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.BaseActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.GameActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.player_creation.CreatePlayerActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selection.PlayerSelectorFragment;

public class GameMenuActivity extends BaseActivity implements GameMenuView {

    private GamePresenter gamePresenter;

    private Player firstPlayer;
    private Player secondPlayer;

    public static void start(final Context context) {
        Intent intent = new Intent(context, GameMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        selectFirstPlayer();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu);
        initView();
    }

    private void initView() {
        Button createPlayerButton = findViewById(R.id.create_player_button);
        createPlayerButton.setOnClickListener(v -> createNewPlayer());
        selectFirstPlayer();
    }


    private void selectFirstPlayer() {
        PlayerSelectorFragment playerSelectorFragment = (PlayerSelectorFragment) PlayerSelectorFragment.newInstance(
                getString(R.string.select_first_player)
        );

        playerSelectorFragment.setListener(player -> {
            firstPlayer = player;
            selectSecondPlayer();
        });

        startFragment(playerSelectorFragment);
    }

    private void selectSecondPlayer() {
        PlayerSelectorFragment playerSelectorFragment = (PlayerSelectorFragment) PlayerSelectorFragment.newInstance(
                getString(R.string.select_second_player)
        );

        playerSelectorFragment.setListener(player -> {
            secondPlayer = player;
            confirm();
        });

        startFragment(playerSelectorFragment);
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.select_player_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void createNewPlayer() {
        CreatePlayerActivity.start(this);
    }

    @Override
    public void confirm() {
        if (firstPlayer.equals(secondPlayer)) {
            showMsg(getString(R.string.players_selection_error));
            start(this);
        } else {
            GameActivity.start(this, firstPlayer.getId(), secondPlayer.getId());
        }
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected MvpPresenter<GameMenuView> createPresenter() {
        gamePresenter = GamePresenterFactory.createPresenter(this);
        return gamePresenter;
    }

    @Override
    protected GameMenuView getMvpView() {
        return this;
    }

    public GamePresenter getGamePresenter() {
        return gamePresenter;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }
}
