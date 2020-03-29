package cmpt276.restaurant_inspector.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmpt276.restaurant_inspector.service.FileDownloadClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 *  Splash screen to welcome user
 */
public class MainActivity extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST = 100;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //grant access to download to disk
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST);
//        }
//        download();
//        Log.d("Start", " yes!");




        //these 3 lines when you hit start download
        FragmentManager manager = getSupportFragmentManager();
        LoadingFragment loadScreen = new LoadingFragment();
        loadScreen.show(manager, "Loading");

        //once loading is cone
        //downloadCsv("api/3/action/package_show?id=restaurants");

        //download on startup, check feature not implemented yet


        Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
            Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
            startActivity(intent);
            finish();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void downloadRestaurants() {
        //create Retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://data.surrey.ca/");

        Retrofit retrofit = builder.build();

        FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);
        Call<ResponseBody> call = fileDownloadClient.downloadRestaurants();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                //boolean writtenToDisk = writeResponseBodyToDisk(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public interface DownloadService {
        @GET
        Call<String> getJsonResponse(@Url String url);
        @GET
        Call<ResponseBody> download(@Url String fileUrl);
    }

    private void download() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://data.surrey.ca/").build();
        DownloadService service = retrofit.create(DownloadService.class);
        service.download("http://data.surrey.ca/dataset/4c8cb648-0e80-4659-9078-ef4917b90ffb/resource/0e5d04a2-be9b-40fe-8de2-e88362ea916b/download/restaurants.csv")
             .enqueue(new Callback<ResponseBody>() {
                 @Override
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                     Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                 }
             });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            //change pathname to proper file directory
            File restaurants = new File(getExternalFilesDir(null) + File.separator + "restaurants.csv");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(restaurants);

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

    private void downloadCsv(String url) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://data.surrey.ca/")
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
                Log.d("downloadUrl", getFileUrl(csvString, "url"));
                LocalDateTime dateTime = LocalDateTime.parse(getFileUrl(csvString, "last_modified"));
                Log.d("downloadDate", dateTime.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
}
