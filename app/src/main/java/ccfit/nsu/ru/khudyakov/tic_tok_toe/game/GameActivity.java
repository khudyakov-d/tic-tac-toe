package ccfit.nsu.ru.khudyakov.tic_tok_toe.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.BaseActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model.Board;

public class GameActivity extends BaseActivity implements GameView {
    private final static String FIRST_PLAYER_ID = "first_player_id";
    private final static String SECOND_PLAYER_ID = "second_player_id";

    private GameViewPresenter gameViewPresenter;

    private Player firstPlayer;
    private Player secondPlayer;

    private int firstPlayerScore = 0;
    private int secondPlayerScore = 0;

    private ArrayAdapter<String> arrayAdapter;

    public static void start(final Context context, int firstPlayerId, int secondPlayerId) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(FIRST_PLAYER_ID, firstPlayerId);
        intent.putExtra(SECOND_PLAYER_ID, secondPlayerId);
        context.startActivity(intent);
    }

    public static void refresh(final Context context) {
        context.startActivity(new Intent(context, GameActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);

        getPlayers();
        initImages();
        initScores();
        initGridBoard();
    }


    @Override
    protected void onStart() {
        super.onStart();
        initModel();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refreshBoard();
    }

    private void getPlayers() {
        firstPlayer = gameViewPresenter.getPlayer(getIntent().getIntExtra(FIRST_PLAYER_ID, -1));
        secondPlayer = gameViewPresenter.getPlayer(getIntent().getIntExtra(SECOND_PLAYER_ID, -1));
    }

    private void initScores() {
        TextView firstPlayerScoreView = findViewById(R.id.first_player_score);
        firstPlayerScoreView.setText(String.valueOf(firstPlayerScore));

        TextView secondPlayerScoreView = findViewById(R.id.second_player_score);
        secondPlayerScoreView.setText(String.valueOf(secondPlayerScore));
    }

    private void initImages() {
        uploadImage(findViewById(R.id.first_player_avatar_image), firstPlayer.getImageUri());
        uploadImage(findViewById(R.id.second_player_avatar_image), secondPlayer.getImageUri());
    }

    private void initGridBoard() {
        List<String> cells = buildEmptyCells();

        final GridView gridView = (GridView) findViewById(R.id.game_board);

        arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.board_item,
                cells
        );

        gridView.setAdapter(arrayAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = view.findViewById(R.id.board_item);

            if (textView.getText().equals("")) {
                int boardSize = Board.getBoardSize();
                gameViewPresenter.nextTurn(textView, position / boardSize, position % boardSize);
            }
        });
    }

    private void refreshBoard() {
        gameViewPresenter.createBoard(firstPlayer, secondPlayer);

        List<String> cells = buildEmptyCells();

        arrayAdapter.clear();
        arrayAdapter.addAll(cells);
        arrayAdapter.notifyDataSetChanged();
    }

    private List<String> buildEmptyCells() {
        return Stream.generate(() -> "")
                .limit(Board.getBoardSize() * Board.getBoardSize())
                .collect(Collectors.toList());
    }

    private void initModel() {
        if (firstPlayer == null || secondPlayer == null) {
            throw new IllegalStateException("Player's are not defined");
        } else {
            gameViewPresenter.createBoard(firstPlayer, secondPlayer);
        }
    }

    private void uploadImage(ImageView imageView, Uri uri) {
        if (uri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setIcon(TextView textView, Player player) {
        if (player.equals(firstPlayer)) {
            textView.setText(Icon.X.name());
        } else if (player.equals(secondPlayer)) {
            textView.setText(Icon.O.name());
        } else {
            throw new IllegalStateException("Unknown user");
        }
    }

    @Override
    public void setCurrentPlayer(Player player) {
        TextView textView = findViewById(R.id.current_player_name);
        textView.setText(String.format("Player's %s turn", player.getNickname()));
    }

    @Override
    public void startNewRound(Player winner) {
        if (winner != null) {
            if (winner.equals(firstPlayer)) {
                firstPlayerScore++;
                ((TextView) findViewById(R.id.first_player_score)).setText(String.valueOf(firstPlayerScore));
            } else if (winner.equals(secondPlayer)) {
                secondPlayerScore++;
                ((TextView) findViewById(R.id.second_player_score)).setText(String.valueOf(secondPlayerScore));
            } else {
                throw new IllegalStateException("Unknown user");
            }
        }
        refresh(this);
    }

    @Override
    protected GamePresenter createPresenter() {
        GamePresenter gamePresenter = GamePresenter.createPresenter();
        gameViewPresenter = gamePresenter;
        return gamePresenter;
    }

    @Override
    protected GameView getMvpView() {
        return this;
    }

}
