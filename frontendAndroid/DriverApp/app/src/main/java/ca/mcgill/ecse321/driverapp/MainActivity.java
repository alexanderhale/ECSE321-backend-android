package ca.mcgill.ecse321.driverapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    int rating = 0;
    public int userId;
    Button addJourney, viewJourneys, myProfile, logout;
    TextView driverName;
    RatingBar ratingbar;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        addJourney = findViewById(R.id.add_journey_button);
        viewJourneys = findViewById(R.id.view_journeys_button);
        myProfile = findViewById(R.id.my_profile_button);
        logout = findViewById(R.id.logout_button);
        driverName = findViewById(R.id.driver_name);
        ratingbar = findViewById(R.id.ratingBar);

        HttpUtils.get("driver/secure/me", null, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String name = response.get("name").toString();
                    driverName.setText(name);
                    if(response.get("rating") != null){
                        rating = (int) response.get("rating");
                    }
                    ratingbar.setRating((float) rating);
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    System.out.println(errorResponse.get("path").toString());
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }
        });
    }

    public void onLogoutClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });
        builder.show();
    }

    public void onAddJourneyClick(View view){
        Intent intent = new Intent(MainActivity.this, AddJourneyActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        //to be check
        finish(); //not sure if we need to use finish() for other activies as well or not ...
    }

    public void onViewJourneysClick(View view){
        Intent intent = new Intent(MainActivity.this, ViewJourneysActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }

    public void onMyProfileClick(View view){
        Intent intent = new Intent(MainActivity.this, DriverProfileActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }
}
