package ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model;

import java.util.Objects;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class Cell {
    private final Player player;

    public Cell(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(player, cell.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }
}
