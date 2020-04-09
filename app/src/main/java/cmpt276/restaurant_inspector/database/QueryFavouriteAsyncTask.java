package cmpt276.restaurant_inspector.database;

import android.os.AsyncTask;

import java.util.List;

public class QueryFavouriteAsyncTask extends AsyncTask<Void, Void, List<Favourite>> {
    private FavouriteDao favouriteDao;

    public QueryFavouriteAsyncTask(FavouriteDao favouriteDao) {
        this.favouriteDao = favouriteDao;
    }

    @Override
    protected List<Favourite> doInBackground(Void... voids) {
        return favouriteDao.getFavourites();
    }
}
