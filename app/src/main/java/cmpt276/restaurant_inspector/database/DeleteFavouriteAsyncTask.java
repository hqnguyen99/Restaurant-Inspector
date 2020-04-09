package cmpt276.restaurant_inspector.database;

import android.os.AsyncTask;

public final class DeleteFavouriteAsyncTask extends AsyncTask<String, Void, Void> {
    private FavouriteDao favouriteDao;

    public DeleteFavouriteAsyncTask(FavouriteDao favouriteDao) {
        this.favouriteDao = favouriteDao;
    }

    @Override
    protected Void doInBackground(String... strings) {
        favouriteDao.delete(strings[0]);
        return null;
    }
}
