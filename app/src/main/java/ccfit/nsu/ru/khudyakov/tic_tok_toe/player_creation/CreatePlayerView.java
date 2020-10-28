package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_creation;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpView;

public interface CreatePlayerView extends MvpView {
    void createNewPlayer();

    void showError(String message);
}
