package ccfit.nsu.ru.khudyakov.tic_tok_toe.new_game_menu;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpView;

public interface NewGameMenuView extends MvpView {
    void createNewPlayer();

    void confirm();

    void showError(String message);
}
