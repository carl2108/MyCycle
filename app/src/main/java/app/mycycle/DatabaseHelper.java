package app.mycycle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carloconnor on 20/02/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mycycle.db";
    private static final int DATABASE_VERSION = 1;

    public static final String ROUTES_TABLE = "ROUTES";
    public static final String ID_COLUMN = "ID";
    public static final String ROUTE_SECTIONS_COLUMN = "ROUTE_SECTIONS";
    public static final String TOTAL_TIME_COLUMN = "TOTAL_TIME";
    public static final String TOTAL_DISTANCE_COLUMN = "TOTAL_DISTANCE";
    public static final String AVERAGE_SPEED_COLUMN = "AVERAGE_SPEED";

    private static final String CREATE_ROUTES_TABLE = "CREATE TABLE " + ROUTES_TABLE +
            "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + ROUTE_SECTIONS_COLUMN + " TEXT, "
            + TOTAL_TIME_COLUMN + " DOUBLE, "
            + TOTAL_DISTANCE_COLUMN + " DOUBLE, "
            + AVERAGE_SPEED_COLUMN + " DOUBLE" + ")";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROUTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
