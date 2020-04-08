package cmpt276.restaurant_inspector.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites")
public class Favourite {
    @NonNull
    @PrimaryKey
    public String id;

    public int newestInspectionDate;
}
