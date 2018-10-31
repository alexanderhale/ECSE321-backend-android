package ca.mcgill.ecse321.driverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private String error = null;
    public int userId;
    Button addJourney, viewJourneys, myProfile, logout;
    TextView driverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        userId = intent.getIntExtra("userId", 0);

        addJourney = findViewById(R.id.add_journey_button);
        viewJourneys = findViewById(R.id.view_journeys_button);
        myProfile = findViewById(R.id.my_profile_button);
        logout = findViewById(R.id.logout_button);
        driverName = findViewById(R.id.driver_name);

        HttpUtils.get("driver/secure/me", null, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String username = (String) response.get("username");
                    driverName.setText(username);
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    String error = (String) errorResponse.get("message");
                    System.out.println(errorResponse.get("path").toString());
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }
        });
    }

    public void onLogoutClick(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onAddJourneyClick(View view){
        Intent intent = new Intent(MainActivity.this, AddJourneyActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        //to be check
        finish(); //not sure if we need to use finish() for other activies as well or not ...
    }

    public void onViewJourneysClick(View view){
        Intent intent = new Intent(MainActivity.this, ViewJourneysActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void onMyProfileClick(View view){
        Intent intent = new Intent(MainActivity.this, DriverProfileActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}
