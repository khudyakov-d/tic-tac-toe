package ccfit.nsu.ru.khudyakov.tic_tok_toe.new_game_menu;

import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepository;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepositoryImpl;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class NewGamePresenter extends MvpPresenter<NewGameMenuView> {
    private PlayerRepository playerRepository = new PlayerRepositoryImpl();

    public static NewGamePresenter createPresenter() {
        return new NewGamePresenter();
    }

    public List<Player> getPlayers() {
        return playerRepository.getPlayers();
    }
}
