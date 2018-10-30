package ca.mcgill.ecse321.driverapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//
//import com.google.android.gms.awareness.Awareness;
//import com.google.android.gms.awareness.snapshot.WeatherResult;
//import com.google.android.gms.awareness.state.Weather;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.tasks.Task;

public class FrontPageActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        username = (EditText) findViewById(R.id.username_field);
        password = (EditText) findViewById(R.id.password_field);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
    }

    public void onLoginClick(View view){
        String username_value = username.getText().toString();
        String password_value = password.getText().toString();

        //FIRST PERFORM LOGIN REQUEST HERE
        //Replace 42 (for testing purposes) by actual user id
        int userId = 42;

        //if(login successful){      GO TO NEXT ACTIVITY with Intent
        Intent intent = new Intent(FrontPageActivity.this, MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void onRegisterClick(View view){
        //FIRST PERFORM REGISTER REQUEST HERE
        //Replace 42 (for testing purposes) by actual user id
        int userId = 42;
        //if(register successful){ GO TO NEXT ACTIVITY with Intent
        Intent intent = new Intent(FrontPageActivity.this, MainActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

}
