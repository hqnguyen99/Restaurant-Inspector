package cmpt276.restaurant_inspector.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM favourites")
    List<Favourite> getAll();

    @Query("SELECT * FROM favourites WHERE id=:id")
    public int getNewestInspectionDate(String id);

    @Insert
    void insert(Favourite favourite);

    @Delete
    void delete(Favourite favourite);
}
