package ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.GameModelPresenter;

public class Board {
    private final GameModelPresenter gameModelPresenter;

    private static final int BOARD_SIZE = 3;

    private Player firstPlayer;
    private Player secondPlayer;

    private Player currentPlayer;

    public Cell[][] cells;


    public Board(GameModelPresenter gameModelPresenter, Player firstPlayer, Player secondPlayer) {
        this.gameModelPresenter = gameModelPresenter;

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = firstPlayer;
    }

    public Player nextTurn(int i, int j) {
        cells[i][j] = new Cell(currentPlayer);

        return currentPlayer;
    }

    public void checkGameEnd() {
        if (checkCellsColumns() || checkCellsRows() || checkCellsDiagonals()) {
            gameModelPresenter.setWinner(currentPlayer);
        } else if (isBoardFull()) {
            gameModelPresenter.setWinner(null);
        } else {
            switchPlayer();
        }
    }

    public boolean checkCellsRows() {
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