package ccfit.nsu.ru.khudyakov.tic_tok_toe.entities;

import android.net.Uri;

import java.util.Objects;

public class Player {

    private Integer id;

    private String nickname;

    private Uri imageUri;

    public Player(String nickname, Uri imageUri) {
        this.nickname = nickname;
        this.imageUri = imageUri;
    }

    public Player(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
