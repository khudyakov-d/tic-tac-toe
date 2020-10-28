package ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.presenter.GamePresenter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BoardTest {

    @Mock
    private GamePresenter gamePresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private final Player firstPlayer = new Player(1, "x", null);
    private final Player secondPlayer = new Player(2, "o", null);
    private Board board;

    @Before
    public void setUp() {
        board = new Board(firstPlayer, secondPlayer);
        board.addListener(gamePresenter);
    }

    @Test
    public void firstPlayerWinWithRow() {
        board.nextTurn(0, 0);
        board.nextTurn(1, 0);
        board.nextTurn(0, 1);
        board.nextTurn(1, 1);
        board.nextTurn(0, 2);

        verify(gamePresenter, times(1)).setWinner(firstPlayer);
    }

    @Test
    public void secondPlayerWinWithColumn() {
        board.nextTurn(0, 2);
        board.nextTurn(0, 0);
        board.nextTurn(1, 1);
        board.nextTurn(0, 2);
        board.nextTurn(1, 2);
        board.nextTurn(0, 1);

        verify(gamePresenter, times(1)).setWinner(secondPlayer);
    }

    @Test
    public void firstPlayerWinWithMainDiagonal() {
        board.nextTurn(0, 0);
        board.nextTurn(0, 2);
        board.nextTurn(1, 1);
        board.nextTurn(1, 2);
        board.nextTurn(2, 2);

        verify(gamePresenter, times(1)).setWinner(firstPlayer);
    }

    @Test
    public void noWinnersGame() {
        board.nextTurn(0, 0);
        board.nextTurn(1, 1);
        board.nextTurn(2, 2);
        board.nextTurn(1, 0);
        board.nextTurn(2, 0);
        board.nextTurn(0, 2);
        board.nextTurn(0, 1);
        board.nextTurn(2, 1);
        board.nextTurn(1, 2);

        verify(gamePresenter, times(1)).setWinner(null);
    }


}