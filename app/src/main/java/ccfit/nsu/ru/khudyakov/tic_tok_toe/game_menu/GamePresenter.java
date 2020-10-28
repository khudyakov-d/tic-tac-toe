package ccfit.nsu.ru.khudyakov.tic_tok_toe.game_menu;

import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepository;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class GamePresenter extends MvpPresenter<GameMenuView> {
    private final PlayerRepository playerRepository;

    public GamePresenter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.getPlayers();
    }
}
