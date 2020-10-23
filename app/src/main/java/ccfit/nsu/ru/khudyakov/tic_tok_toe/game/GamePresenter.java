package ccfit.nsu.ru.khudyakov.tic_tok_toe.game;

import android.widget.TextView;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepository;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepositoryImpl;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model.Board;

public class GamePresenter extends MvpPresenter<GameView> implements GameModelPresenter, GameViewPresenter {
    private PlayerRepository playerRepository = new PlayerRepositoryImpl();
    private Board board;

    public static GamePresenter createPresenter() {
        return new GamePresenter();
    }

    @Override
    public Player getPlayer(int playerId) {
        return playerRepository.getById(playerId);
    }

    @Override
    public void nextTurn(TextView boardItem, int i, int j) {
        Player player = board.nextTurn(i, j);
        view.setIcon(boardItem, player);
        board.checkGameEnd();
        view.setCurrentPlayer(board.getCurrentPlayer());
    }

    @Override
    public void createBoard(Player firstPlayer, Player secondPlayer) {
        board = new Board(this, firstPlayer, secondPlayer);
        view.setCurrentPlayer(board.getCurrentPlayer());
    }

    @Override
    public void setWinner(Player player) {
        view.startNewRound(player);
    }
}
