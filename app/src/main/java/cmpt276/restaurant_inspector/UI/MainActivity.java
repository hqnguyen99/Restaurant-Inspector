package cmpt276.restaurant_inspector.UI;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmpt276.restaurant_inspector.database.Favourite;
import cmpt276.restaurant_inspector.database.FavouriteDao;
import cmpt276.restaurant_inspector.database.FavouriteDb;
import cmpt276.restaurant_inspector.database.QueryFavouriteAsyncTask;
import cmpt276.restaurant_inspector.model.AppData;
import cmpt276.restaurant_inspector.model.DataSingleton;
import cmpt276.restaurant_inspector.model.DateTimeUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 *  Splash screen to welcome user,
 *  also downloads new data from the Surrey data service if found.
 */
public class MainActivity extends AppCompatActivity implements AskForDownloadFragment.OnSelectionListener
{
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final String BASE_URL = "http://data.surrey.ca/";
    private final String RESTAURANT_FILENAME = "restaurants";
    private final String INSPECTION_FILENAME = "inspection";
    private final String CSV = ".csv";
    private boolean updateRequested = false;
    private boolean updateConfirmed = false;
    Runnable runnable;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    public void sendStatus(boolean status) {
        updateConfirmed = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fullRestaurantFilepath = getFilesDir() + File.separator + RESTAURANT_FILENAME + CSV;
        String fullInspectionFilepath = getFilesDir() + File.separator + INSPECTION_FILENAME + CSV;

        String RESTAURANT_METADATA_URL = "api/3/action/package_show?id=restaurants";
        checkForUpdateAndDownload(RESTAURANT_METADATA_URL, fullRestaurantFilepath);

        String INSPECTION_METADATA_URL = "api/3/action/package_show?id=fraser-health-restaurant-inspection-reports";
        checkForUpdateAndDownload(INSPECTION_METADATA_URL, fullInspectionFilepath);

        try {
            createRestaurantList(fullRestaurantFilepath, fullInspectionFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            setFavourites(FavouriteDb.getInstance(getApplicationContext()).favouriteDao());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();

        if(isServiceOK()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                }
            };

            handler.postDelayed(runnable, 3000);
        }
    }

    private void createRestaurantList
        (String restaurantFilepath, String inspectionFilepath) throws IOException {
        DataSingleton data = AppData.INSTANCE;

        if (!(new File(restaurantFilepath)).exists() ||
            !(new File(inspectionFilepath)).exists()) {
            InputStream restaurantIs =
                getResources().openRawResource(R.raw.restaurants_itr1);
            InputStream inspectionIs =
                getResources().openRawResource(R.raw.inspectionreports_itr1);

            data.init(
                new BufferedReader(new InputStreamReader(
                    restaurantIs, StandardCharsets.UTF_8)),
                new BufferedReader(new InputStreamReader(
                    inspectionIs, StandardCharsets.ISO_8859_1))
            );

            restaurantIs.close();
            inspectionIs.close();
        } else {

            FileInputStream restaurantFis = openFileInput(RESTAURANT_FILENAME + CSV);
            FileInputStream inspectionFis = openFileInput(INSPECTION_FILENAME + CSV);
            data.init(
                new BufferedReader(new InputStreamReader(restaurantFis)),
                new BufferedReader(new InputStreamReader(inspectionFis))
            );

            restaurantFis.close();
            inspectionFis.close();
        }
    }

    private void setFavourites(FavouriteDao favouriteDao)
        throws ExecutionException, InterruptedException {
        List<Favourite> favouriteList =
            new QueryFavouriteAsyncTask(favouriteDao).execute().get();

        DataSingleton data = AppData.INSTANCE;

        for (Favourite favourite : favouriteList) {
            data.entries().get(favourite.getId()).changeFavourite(true);
        }

    }

    public interface DownloadService {
        @GET
        Call<String> getJsonResponse(@Url String url);
        @GET
        Call<ResponseBody> download(@Url String url);
    }

    private void checkForUpdateAndDownload(String url, String filename) {
        LocalDateTime lastModifiedTime = null;
        File file = new File(filename);

        if (file.exists()) {
            lastModifiedTime = Instant.ofEpochMilli(file.lastModified())
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

            if (DateTimeUtil.hoursFromNow(lastModifiedTime) < 20) {
                return;
            }
        }

        downloadCSV(url, lastModifiedTime, filename);
    }

    private void downloadCSV(String url, LocalDateTime timeOfLastUpdate, String filename) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
        DownloadService service = retrofit.create(DownloadService.class);

        service.getJsonResponse(url).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT);
                String responseString = response.body();
                List<String> resourceStrings = getResourceStrings(responseString);
                int i;

                for (i = 0; !resourceStrings.get(i).contains("csv"); i++) {
                    Log.d("ResourcesString ", resourceStrings.get(i));
                }

                String csvString = resourceStrings.get(i);

                LocalDateTime timeOfUpload =
                    LocalDateTime.parse(getFileUrl(csvString, "last_modified"));

                if (timeOfLastUpdate != null) {
                    if (timeOfLastUpdate.isAfter(timeOfUpload)) {
                        return;
                    }
                }

                String csvUrl = getFileUrl(csvString, "url");
                download(csvUrl, filename);

//                if (!updateRequested) {
//                    AskForDownloadFragment downloadPrompt = new AskForDownloadFragment();
//                    downloadPrompt.show(getSupportFragmentManager(),
//                        "New data is available, would you like to download?");
//                }
//                if (updateConfirmed) {
//                    download(csvUrl, filename);
//                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private static void download(String url, String filename) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        DownloadService service = retrofit.create(MainActivity.DownloadService.class);

        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                service.download(url).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        boolean written = writeResponseBodyToDisk(response.body(), filename);
                        Log.d("File Download status", String.valueOf(written));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("download", t.getMessage());
                    }
                });

                return null;
            }
        }.execute();
    }

    private static boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {
            //change pathname to proper file directory
            File csvFile = new File(fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(csvFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("Restaurant file", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private String getFileUrl(String jsonInput, String attribute) {
        final String REGEX = '"' + attribute + "\": " + "\"(.+?)\"";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(jsonInput);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    private List<String> getResourceStrings(String jsonInput) {
        final String RESOURCES = "\"resources\": [";
        int start = jsonInput.indexOf(RESOURCES) + RESOURCES.length();
        int end = jsonInput.indexOf(']', start);
        String entireResourceString = jsonInput.substring(start, end);

        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{(.+?)\\}");
        Matcher matcher =  pattern.matcher(entireResourceString);

        while(matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public boolean isServiceOK() {
        Log.d(TAG,"isServiceOK: checking google service version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS){
            //everything is fine and user can make map requests
            Log.d(TAG, "isServiceOK: Google Map working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG,"isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this,"You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
