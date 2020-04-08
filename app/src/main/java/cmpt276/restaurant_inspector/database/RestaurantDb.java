package cmpt276.restaurant_inspector.database;

import androidx.room.Database;

@Database(entities = {Favourite.class}, version = 1)
public abstract class RestaurantDb {
    public FavouriteDao favouriteDao;
}
