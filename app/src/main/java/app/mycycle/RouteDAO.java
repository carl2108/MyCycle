package app.mycycle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by carloconnor on 21/02/17.
 */

public class RouteDAO extends DAO {

    private static final String WHERE_ID_EQUALS = DatabaseHelper.ID_COLUMN + " =?";

    public RouteDAO(Context context) {
        super(context);
    }

    public long save(Route route) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ID_COLUMN, route.getID());

        String routeGSON = new Gson().toJson(route.getRouteSections());                             //convert sections arraylist to gson string

        values.put(DatabaseHelper.ROUTE_SECTIONS_COLUMN, routeGSON.toString());
        values.put(DatabaseHelper.TOTAL_TIME_COLUMN, route.getTotalTime());
        values.put(DatabaseHelper.TOTAL_DISTANCE_COLUMN, route.getTotalDistance());
        values.put(DatabaseHelper.AVERAGE_SPEED_COLUMN, route.getAverageSpeed());

        return database.insert(DatabaseHelper.ROUTES_TABLE, null, values);
    }

    public long update(Route route) {                                                               //TODO - not needed?
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ID_COLUMN, route.getID());

        long result = database.update(DatabaseHelper.ROUTES_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(route.getID()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(int id) {
        return database.delete(DatabaseHelper.ROUTES_TABLE, WHERE_ID_EQUALS,
                new String[] { id + "" });
    }

    public Route getRoute(long id) {                                                                //Retrieves a single route record with the given id
        Route route = null;
        String sql = "SELECT * FROM " + DatabaseHelper.ROUTES_TABLE
                + " WHERE " + DatabaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if(cursor.moveToNext()) {
            route = new Route();
            route.setID(cursor.getInt(0));
            route.setRouteSections(cursor.getString(1));
            route.setTotalTime(cursor.getDouble(1));
            route.setTotalDistance(cursor.getDouble(2));
            route.setAverageSpeed(cursor.getDouble(3));
        }
        return route;
    }

    public int getNextRouteID() {
        String sql = "SELECT MAX(" + DatabaseHelper.ID_COLUMN + ") from " + DatabaseHelper.ROUTES_TABLE;
        Cursor results = database.rawQuery(sql, null);
        if(results.moveToFirst()) {
            return results.getInt(0) + 1;
        } else
            return 1;
    }

}
