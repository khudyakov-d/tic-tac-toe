package ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model;

import java.util.ArrayList;
import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.Sign;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.presenter.GameModelListener;

public class Board {
    private final List<GameModelListener> gameModelListeners = new ArrayList<>();

    private static final int BOARD_SIZE = 3;

    final private Player firstPlayer;
    final private Player secondPlayer;

    private Player currentPlayer;

    private final Cell[][] cells;


    public Board(Player firstPlayer, Player secondPlayer) {

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = firstPlayer;
    }

    public Board(Player firstPlayer, Player secondPlayer, Player currentPlayer, List<String> savedCells) {

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.cells = new Cell[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0, savedCellsSize = savedCells.size(); i < savedCellsSize; i++) {
            if (!savedCells.get(i).equals("")) {
                switch (Sign.valueOf(savedCells.get(i))) {
                    case X:
                        cells[i / BOARD_SIZE][i % BOARD_SIZE] = new Cell(firstPlayer);
                        break;
                    case O:
                        cells[i / BOARD_SIZE][i % BOARD_SIZE] = new Cell(secondPlayer);
                        break;
                }
            }
        }

        this.currentPlayer = currentPlayer;
    }

    public void addListener(GameModelListener gameModelListener) {
        gameModelListeners.add(gameModelListener);
    }

    private void winnerNotify(Player player) {
        for (GameModelListener listener : gameModelListeners) {
            listener.setWinner(player);
        }
    }

    private void signNotify(int position, Player player) {
        for (GameModelListener listener : gameModelListeners) {
            listener.setSign(position, player);
        }
    }

    public void nextTurn(int i, int j) {
        cells[i][j] = new Cell(currentPlayer);
        signNotify(i * BOARD_SIZE + j, currentPlayer);
        checkGameEnd();
    }

    private void checkGameEnd() {
        if (checkCellsColumns() || checkCellsRows() || checkCellsDiagonals()) {
            winnerNotify(currentPlayer);
        } else if (isBoardFull()) {
            winnerNotify(null);
        } else {
            switchPlayer();
        }
    }

    private boolean checkCellsRows() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkCellsEqual(cells[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCellsColumns() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkCellsEqual(getColumn(i))) {
                return true;
            }
        }
        return false;
    }

    private Cell[] getColumn(int index) {
        Cell[] column = new Cell[BOARD_SIZE];
        for (int i = 0; i < column.length; i++) {
            column[i] = cells[i][index];
        }
        return column;
    }

    private boolean checkCellsDiagonals() {
        return checkCellsEqual(getMainDiagonal()) || checkCellsEqual(getAdditionalDiagonal());
    }

    private Cell[] getMainDiagonal() {
        Cell[] column = new Cell[BOARD_SIZE];
        for (int i = 0; i < column.length; i++) {
            column[i] = cells[i][i];
        }
        return column;
    }

    private Cell[] getAdditionalDiagonal() {
        Cell[] column = new Cell[BOARD_SIZE];
        for (int i = 0; i < column.length; i++) {
            column[i] = cells[i][BOARD_SIZE - i - 1];
        }
        return column;
    }

    private boolean isBoardFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell == null) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkCellsEqual(Cell... cells) {
        if (cells == null || cells.length == 0) {
            return false;
        }

        for (Cell cell : cells) {
            if (cell == null) {
                return false;
            }
        }

        Cell comparisonBase = cells[0];
        for (Cell cell : cells) {
            if (!cell.equals(comparisonBase)) {
                return false;
            }
        }

        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == firstPlayer) ? secondPlayer : firstPlayer;
    }

    public static int getBoardSize() {
        return BOARD_SIZE;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

}