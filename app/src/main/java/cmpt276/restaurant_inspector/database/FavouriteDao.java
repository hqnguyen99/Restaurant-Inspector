package cmpt276.restaurant_inspector.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM favourites")
    List<Favourite> getFavourites();

    @Query("SELECT newestInspectionDate FROM favourites WHERE id=:id")
    public int getNewestInspectionDate(String id);

    @Insert
    void insert(Favourite favourite);

    @Query("DELETE FROM favourites WHERE id=:id")
    void delete(String id);
}
