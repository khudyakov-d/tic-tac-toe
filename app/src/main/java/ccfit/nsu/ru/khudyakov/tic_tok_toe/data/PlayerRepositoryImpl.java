package ccfit.nsu.ru.khudyakov.tic_tok_toe.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static AtomicInteger playersCounter = new AtomicInteger();

    static final List<Player> players = new ArrayList<>();

    @Override
    public void addPlayer(Player player) {
        player.setId(playersCounter.getAndAdd(1));
        players.add(player);
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }


    @Override
    public Player getById(int id) {
        return players.get(players.indexOf(new Player(id)));
    }
}
