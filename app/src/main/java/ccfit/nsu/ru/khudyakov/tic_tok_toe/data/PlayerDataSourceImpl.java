package ccfit.nsu.ru.khudyakov.tic_tok_toe.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class PlayerDataSourceImpl implements PlayerDataSource {

    private final SQLiteDatabase database;

    public PlayerDataSourceImpl(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void addPlayer(Player player) {
        ContentValues cv = new ContentValues();
        cv.put("nickname", player.getNickname());

        if (player.getImageUri() != null) {
            cv.put("imageUri", player.getImageUri().toString());
        } else {
            cv.putNull("imageUri");
        }

        long result = database.insert("player", null, cv);

        if (result == -1) {
            throw new IllegalStateException("Player insert error");
        }
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        Cursor c = database.query("player", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            int id = c.getColumnIndex("id");
            int nickname = c.getColumnIndex("nickname");
            int imageUri = c.getColumnIndex("imageUri");

            do {
                players.add(new Player(c.getInt(id),
                        c.getString(nickname),
                        c.getString(imageUri) == null ? null : Uri.parse(c.getString(imageUri)))
                );
            } while (c.moveToNext());
        } else {
            players = new ArrayList<>();
        }
        c.close();
        return players;
    }

    @Override
    public Player getById(int id) {
        Cursor c = database.query("player",
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        Player player = null;

        if (c.moveToFirst()) {

            int playerId = c.getColumnIndex("id");
            int nickname = c.getColumnIndex("nickname");
            int imageUri = c.getColumnIndex("imageUri");

            player = new Player(
                    c.getInt(playerId),
                    c.getString(nickname),
                    c.getString(imageUri) == null ? null : Uri.parse(c.getString(imageUri))
            );
        }

        c.close();

        return player;
    }
}
