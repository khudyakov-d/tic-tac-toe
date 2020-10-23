package ccfit.nsu.ru.khudyakov.tic_tok_toe.data;

import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public interface PlayerRepository {
    void addPlayer(Player player);

    List<Player> getPlayers();

    Player getById(int id);
}
