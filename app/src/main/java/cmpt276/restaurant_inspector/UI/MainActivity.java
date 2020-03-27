package cmpt276.restaurant_inspector.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import cmpt276.restaurant_inspector.service.FileDownloadClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        //grant access to download to disk
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
        //download on startup, check feature not implemented yet
        //downloadRestaurants();
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
                .baseUrl("https://futurestud.io/");

        Retrofit retrofit = builder.build();

        //todo get client & call object for the request
        FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);

        Call<ResponseBody> call = fileDownloadClient.downloadRestaurants();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
