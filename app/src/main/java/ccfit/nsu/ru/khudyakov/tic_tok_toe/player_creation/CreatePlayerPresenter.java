package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_creation;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepository;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class CreatePlayerPresenter extends MvpPresenter<CreatePlayerView> {

    private final PlayerRepository playerRepository;

    public CreatePlayerPresenter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void addPlayer(Player player) {
        playerRepository.addPlayer(player);
        view.createNewPlayer();
    }
}
