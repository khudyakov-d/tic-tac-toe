package ccfit.nsu.ru.khudyakov.tic_tok_toe.game;

import android.widget.TextView;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpView;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public interface GameView extends MvpView {
    void setSign(int position, Player player);

    void setCurrentPlayer(Player player);

    void startNewRound(Player player);

    void startNewGame();
}
