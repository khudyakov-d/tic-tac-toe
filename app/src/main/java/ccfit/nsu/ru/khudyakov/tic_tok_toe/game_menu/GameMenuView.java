package ccfit.nsu.ru.khudyakov.tic_tok_toe.game_menu;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpView;

public interface GameMenuView extends MvpView {
    void createNewPlayer();

    void confirm();

    void showMsg(String message);
}
