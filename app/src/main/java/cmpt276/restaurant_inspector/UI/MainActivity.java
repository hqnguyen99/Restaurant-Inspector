package cmpt276.restaurant_inspector.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 *  Splash screen to welcome user
 */
public class MainActivity extends AppCompatActivity
{
    Runnable runnable;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public boolean isServiceOK(){
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
