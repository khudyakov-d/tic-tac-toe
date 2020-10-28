package ccfit.nsu.ru.khudyakov.tic_tok_toe.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.BaseActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.model.Board;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.presenter.GamePresenter;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game.presenter.GamePresenterFactory;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game_menu.GameMenuActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.utils.Utils;

public class GameActivity extends BaseActivity implements GameView {
    private final static String BOARD_CURRENT_STATE = "board_current_state";

    private final static String FIRST_PLAYER_ID = "first_player_id";
    private final static String SECOND_PLAYER_ID = "second_player_id";
    private final static String CURRENT_PLAYER_ID = "current_player_id";

    private final static String FIRST_PLAYER_SCORE = "first_player_score";
    private final static String SECOND_PLAYER_SCORE = "second_player_score";

    private GamePresenter gamePresenter;

    private Player firstPlayer;
    private Player secondPlayer;

    private Player currentPlayer;

    private int firstPlayerScore = 0;
    private int secondPlayerScore = 0;

    private ArrayAdapter<String> arrayAdapter;
    private List<String> savedCells = null;

    boolean activityRecreated = false;

    public static void start(final Context context, int firstPlayerId, int secondPlayerId) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(FIRST_PLAYER_ID, firstPlayerId);
        intent.putExtra(SECOND_PLAYER_ID, secondPlayerId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);

        if (savedInstanceState == null) {
            initPlayers();
            initNewGridBoard();
        } else {
            initSavedPlayers(savedInstanceState);
            restoreGridBoard(savedInstanceState);
        }

        initImages();
        initScores();
        initNewGameButton();

        activityRecreated = savedInstanceState != null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initModel();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<String> state = new ArrayList<>();
        for (int i = 0, count = arrayAdapter.getCount(); i < count; i++) {
            state.add(i, arrayAdapter.getItem(i));
        }

        outState.putStringArrayList(BOARD_CURRENT_STATE, state);

        outState.putInt(FIRST_PLAYER_ID, firstPlayer.getId());
        outState.putInt(SECOND_PLAYER_ID, secondPlayer.getId());
        outState.putInt(CURRENT_PLAYER_ID, currentPlayer.getId());

        outState.putInt(FIRST_PLAYER_SCORE, firstPlayerScore);
        outState.putInt(SECOND_PLAYER_SCORE, secondPlayerScore);
    }

    private void initPlayers() {
        firstPlayer = gamePresenter.getPlayer(getIntent().getIntExtra(FIRST_PLAYER_ID, -1));
        secondPlayer = gamePresenter.getPlayer(getIntent().getIntExtra(SECOND_PLAYER_ID, -1));
    }

    private void initSavedPlayers(Bundle savedInstanceState) {
        firstPlayer = gamePresenter.getPlayer(savedInstanceState.getInt(FIRST_PLAYER_ID, -1));
        secondPlayer = gamePresenter.getPlayer(savedInstanceState.getInt(SECOND_PLAYER_ID, -1));
        currentPlayer = gamePresenter.getPlayer(savedInstanceState.getInt(CURRENT_PLAYER_ID, -1));
    }

    private void initImages() {
        Utils.uploadImage(this, findViewById(R.id.first_player_avatar_image), firstPlayer.getImageUri());
        Utils.uploadImage(this, findViewById(R.id.second_player_avatar_image), secondPlayer.getImageUri());
    }

    private void initScores() {
        TextView firstPlayerScoreView = findViewById(R.id.first_player_score);
        firstPlayerScoreView.setText(String.valueOf(firstPlayerScore));

        TextView secondPlayerScoreView = findViewById(R.id.second_player_score);
        secondPlayerScoreView.setText(String.valueOf(secondPlayerScore));
    }

    private void initNewGameButton() {
        Button button = findViewById(R.id.new_game);
        button.setOnClickListener(v -> startNewGame());
    }

    private void initNewGridBoard() {
        savedCells = buildEmptyCells();
        initGridBoard(savedCells);
    }

    private void restoreGridBoard(Bundle savedInstanceState) {
        savedCells = savedInstanceState.getStringArrayList(BOARD_CURRENT_STATE);
        initGridBoard(savedCells);
    }

    private void initGridBoard(List<String> cells) {
        final GridView gridView = findViewById(R.id.game_board);

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
                gamePresenter.nextTurn(position, position / boardSize, position % boardSize);
            }
        });
    }

    private void refreshBoard() {
        gamePresenter.createBoard(firstPlayer, secondPlayer);

        arrayAdapter.clear();
        arrayAdapter.addAll(buildEmptyCells());
        updateAdapter();
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
            if (activityRecreated) {
                gamePresenter.restoreBoard(firstPlayer, secondPlayer, currentPlayer, savedCells);
            } else {
                gamePresenter.createBoard(firstPlayer, secondPlayer);
            }
        }
    }

    @Override
    public void setSign(int position, Player player) {
        if (player.equals(firstPlayer)) {
            savedCells.set(position, Sign.X.name());
            updateAdapter();
        } else if (player.equals(secondPlayer)) {
            savedCells.set(position, Sign.O.name());
            updateAdapter();
        } else {
            throw new IllegalStateException("Unknown user");
        }
    }

    private void updateAdapter() {
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
        TextView textView = findViewById(R.id.current_player_name);
        textView.setText(String.format(getString(R.string.player_turn), currentPlayer.getNickname()));
    }

    @Override
    public void startNewRound(Player winner) {
        if (winner != null) {
            if (winner.equals(firstPlayer)) {
                ((TextView) findViewById(R.id.first_player_score)).setText(String.valueOf(++firstPlayerScore));
            } else if (winner.equals(secondPlayer)) {
                ((TextView) findViewById(R.id.second_player_score)).setText(String.valueOf(++secondPlayerScore));
            } else {
                throw new IllegalStateException("Unknown user");
            }
        }

        refreshBoard();
    }

    @Override
    public void startNewGame() {
        GameMenuActivity.start(this);
    }

    @Override
    protected GamePresenter createPresenter() {
        gamePresenter = GamePresenterFactory.createPresenter(this);
        return gamePresenter;
    }

    @Override
    protected GameView getMvpView() {
        return this;
    }

    @Override
    public void onBackPressed() {
        GameMenuActivity.start(this);
    }
}
