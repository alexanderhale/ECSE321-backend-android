package ca.mcgill.ecse321.driverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ViewJourneysActivity extends AppCompatActivity {
    public int userId;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_journeys);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        backButton = (Button) findViewById(R.id.back_button);
    }

    public void onBackClick(View view){
        Intent intent = new Intent(ViewJourneysActivity.this, MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}
