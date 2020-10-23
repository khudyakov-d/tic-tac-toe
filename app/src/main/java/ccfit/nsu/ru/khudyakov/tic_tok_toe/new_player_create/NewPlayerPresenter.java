package ccfit.nsu.ru.khudyakov.tic_tok_toe.new_player_create;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepository;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepositoryImpl;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class NewPlayerPresenter extends MvpPresenter<NewPlayerView> {
    private PlayerRepository playerRepository = new PlayerRepositoryImpl();

    public static NewPlayerPresenter createPresenter() {
        return new NewPlayerPresenter();
    }

    public void addPlayer(Player player) {
        playerRepository.addPlayer(player);
        view.createNewPlayer();
    }
}
