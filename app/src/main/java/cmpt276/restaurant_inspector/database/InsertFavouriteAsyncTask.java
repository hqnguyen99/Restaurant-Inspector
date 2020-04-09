package cmpt276.restaurant_inspector.database;

import android.os.AsyncTask;

public class InsertFavouriteAsyncTask extends AsyncTask<Favourite, Void, Void> {
    private FavouriteDao favouriteDao;

    public InsertFavouriteAsyncTask(FavouriteDao favouriteDao) {
        this.favouriteDao = favouriteDao;
    }

    @Override
    protected Void doInBackground(Favourite... favourites) {
        favouriteDao.insert(favourites[0]);
        return null;
    }
}
