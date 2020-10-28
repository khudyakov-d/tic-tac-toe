package ccfit.nsu.ru.khudyakov.tic_tok_toe.game.presenter;

import android.widget.TextView;

import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.MvpPresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepository;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.GameView;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model.Board;

public class GamePresenter extends MvpPresenter<GameView> implements GameModelListener {
    private final PlayerRepository playerRepository;
    private Board board;

    public GamePresenter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayer(int playerId) {
        return playerRepository.getById(playerId);
    }

    public void nextTurn(int position, int i, int j) {
        board.nextTurn(i, j);
        view.setCurrentPlayer(board.getCurrentPlayer());
    }

    public void createBoard(Player firstPlayer, Player secondPlayer) {
        board = new Board(firstPlayer, secondPlayer);
        board.addListener(this);
        view.setCurrentPlayer(board.getCurrentPlayer());
    }

    public void restoreBoard(Player firstPlayer, Player secondPlayer, Player currentPlayer, List<String> savedCells) {
        board = new Board(firstPlayer, secondPlayer, currentPlayer, savedCells);
        board.addListener(this);
        view.setCurrentPlayer(board.getCurrentPlayer());
    }


    @Override
    public void setSign(int position, Player player) {
        view.setSign(position, player);
    }

    @Override
    public void setWinner(Player player) {
        view.startNewRound(player);
    }

}
