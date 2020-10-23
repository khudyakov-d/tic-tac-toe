package ccfit.nsu.ru.khudyakov.tic_tok_toe.game;

import android.widget.TextView;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public interface GameViewPresenter {

    Player getPlayer(int playerId);

    void nextTurn(TextView view, int i, int j);

    void createBoard(Player firstPlayer, Player secondPlayer);
}
