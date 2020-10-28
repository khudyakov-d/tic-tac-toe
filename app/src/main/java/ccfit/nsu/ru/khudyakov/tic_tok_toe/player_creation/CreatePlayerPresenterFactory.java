package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_creation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerDataSource;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerDataSourceImpl;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerDatabaseHelper;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepository;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.data.PlayerRepositoryImpl;

public class CreatePlayerPresenterFactory {
    public static CreatePlayerPresenter createPresenter(Context context) {
        PlayerDatabaseHelper playerDatabaseHelper = new PlayerDatabaseHelper(context);
        SQLiteDatabase database = playerDatabaseHelper.getWritableDatabase();

        final PlayerDataSource dataSource = new PlayerDataSourceImpl(database);
        final PlayerRepository repository = new PlayerRepositoryImpl(dataSource);

        return new CreatePlayerPresenter(repository);
    }
}
