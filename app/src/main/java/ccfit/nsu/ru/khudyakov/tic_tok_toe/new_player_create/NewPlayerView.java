package ccfit.nsu.ru.khudyakov.tic_tok_toe.new_player_create;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpView;

public interface NewPlayerView extends MvpView {
    void createNewPlayer();

    void showError(String message);
}
