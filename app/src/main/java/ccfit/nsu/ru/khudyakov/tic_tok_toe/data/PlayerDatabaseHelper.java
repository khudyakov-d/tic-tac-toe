package ccfit.nsu.ru.khudyakov.tic_tok_toe.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerDatabaseHelper extends SQLiteOpenHelper {

    public PlayerDatabaseHelper(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table player ("
                + "id integer primary key autoincrement,"
                + "nickname varchar(16) not null,"
                + "imageUri text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}


