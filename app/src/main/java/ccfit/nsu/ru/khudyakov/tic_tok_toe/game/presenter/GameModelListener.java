package ccfit.nsu.ru.khudyakov.tic_tok_toe.game.presenter;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public interface GameModelListener {
     void setSign(int position, Player player);
     void setWinner(Player player);
}
