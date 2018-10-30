package ca.mcgill.ecse321.driverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DriverProfileActivity extends AppCompatActivity {
    public int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_profile);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);

        // Next here get all driver details and splash them on the view
        // Need first to get all the elements (findViewById())
    }

    public void onSaveClick(View view){
        // Save the change to the driver profil and send them to DB
        // Next once done and successful, go back to main menu
        Intent intent = new Intent(DriverProfileActivity.this, MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void onCancelClick(View view){
        Intent intent = new Intent(DriverProfileActivity.this, MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

}
