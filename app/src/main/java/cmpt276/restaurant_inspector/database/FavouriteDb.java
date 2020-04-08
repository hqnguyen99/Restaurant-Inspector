package cmpt276.restaurant_inspector.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favourite.class}, version = 1, exportSchema = false)
public abstract class FavouriteDb extends RoomDatabase {
    private static FavouriteDb INSTANCE;
    public FavouriteDao favouriteDao;

    public static FavouriteDb getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                Room.databaseBuilder(context.getApplicationContext(),
                    FavouriteDb.class, "favouriteDb")
                    .allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
}
