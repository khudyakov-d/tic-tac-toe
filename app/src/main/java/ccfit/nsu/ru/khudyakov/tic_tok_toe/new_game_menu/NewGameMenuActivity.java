package ccfit.nsu.ru.khudyakov.tic_tok_toe.new_game_menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.BaseActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.GameActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.new_player_create.NewPlayerActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selector.PlayerSelectorFragment;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selector.SelectedPlayerFragment;

public class NewGameMenuActivity extends BaseActivity implements NewGameMenuView, PlayerSelectorFragment.SelectPlayerListener {

    private NewGamePresenter newGamePresenter;

    private Player firstPlayer;
    private Player secondPlayer;

    public static void start(final Context context) {
        Intent intent = new Intent(context, NewGameMenuActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startPlayerSelection(R.id.first_fragment_container);
        startPlayerSelection(R.id.second_fragment_container);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game_menu);
        initView();
    }


    private void initView() {
        Button createPlayerButton = findViewById(R.id.create_player_button);
        createPlayerButton.setOnClickListener(v -> createNewPlayer());

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(v -> confirm());

        startPlayerSelection(R.id.first_fragment_container);
        startPlayerSelection(R.id.second_fragment_container);
    }


    private void startPlayerSelection(int fragmentId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(fragmentId, PlayerSelectorFragment.newInstance())
                .commit();
    }

    @Override
    public void createNewPlayer() {
        NewPlayerActivity.start(this);
    }

    @Override
    public void confirm() {
        if (firstPlayer == null || secondPlayer == null) {
            showError("Please select both players");
        } else if (firstPlayer == secondPlayer) {
            showError("Please select different players");
            start(this);
        } else {
            GameActivity.start(this, firstPlayer.getId(), secondPlayer.getId());
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlayerSelect(Player player, int fragmentId) {
        switch (fragmentId) {
            case R.id.first_fragment_container:
                firstPlayer = player;
                handlePlayerSelection(player, fragmentId);
                break;
            case R.id.second_fragment_container:
                secondPlayer = player;
                handlePlayerSelection(player, fragmentId);
                break;
            default:
                throw new IllegalStateException("Not correct id of fragment");
        }
    }

    private void handlePlayerSelection(Player player, int fragmentId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(fragmentId, SelectedPlayerFragment.newInstance(player))
                .commit();
    }

    @Override
    protected MvpPresenter<NewGameMenuView> createPresenter() {
        newGamePresenter = NewGamePresenter.createPresenter();
        return newGamePresenter;
    }

    @Override
    protected NewGameMenuView getMvpView() {
        return this;
    }

    public NewGamePresenter getNewGamePresenter() {
        return newGamePresenter;
    }
}
