package cmpt276.restaurant_inspector.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites")
public class Favourite {
    @PrimaryKey
    public String id;

    @ColumnInfo
    public int newestInspectionDate;
}
