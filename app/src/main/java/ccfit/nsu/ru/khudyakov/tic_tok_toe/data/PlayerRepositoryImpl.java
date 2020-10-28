package ccfit.nsu.ru.khudyakov.tic_tok_toe.data;

import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class PlayerRepositoryImpl implements PlayerRepository {
    private final PlayerDataSource playerDataSource;

    public PlayerRepositoryImpl(PlayerDataSource playerDataSource) {
        this.playerDataSource = playerDataSource;
    }

    @Override
    public void addPlayer(Player player) {
        playerDataSource.addPlayer(player);
    }

    @Override
    public List<Player> getPlayers() {
        return playerDataSource.getPlayers();
    }


    @Override
    public Player getById(int id) {
        return playerDataSource.getById(id);
    }
}
