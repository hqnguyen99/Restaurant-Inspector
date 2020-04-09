package cmpt276.restaurant_inspector.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites")
public class Favourite {
    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "newestInspectionDate")
    private int newestInspectionDate;

    public Favourite(@NonNull String id, int newestInspectionDate) {
        this.id = id;
        this.newestInspectionDate = newestInspectionDate;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public int getNewestInspectionDate() {
        return newestInspectionDate;
    }
}
