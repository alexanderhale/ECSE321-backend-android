package ca.mcgill.ecse321.driverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String error = null;
    public int userId;
    Button addJourney, viewJourneys, myProfile, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        addJourney = (Button) findViewById(R.id.add_journey_button);
        viewJourneys = (Button) findViewById(R.id.view_journeys_button);
        myProfile = (Button) findViewById(R.id.my_profile_button);
        logout = (Button) findViewById(R.id.logout_button);
    }

    public void onLogoutClick(View view){
        Intent intent = new Intent(MainActivity.this, FrontPageActivity.class);
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
