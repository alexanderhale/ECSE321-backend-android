package ca.mcgill.ecse321.driverapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
    }

    public void onLoginPress(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username", username);
            jsonParams.put("password", password);
        } catch (JSONException e) {
            Log.e("Error", "unexpected exception", e);
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("Error", "unexpected exception", e);
        }

        HttpUtils.post(this, "driver/login", entity, "application/json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    token = (String) response.get("message");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Login failed");
                    builder.setMessage("Incorrect username/password combination or user not found. Please try again");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // do nothing
                        }
                    });
                    builder.show();
                    System.out.println(errorResponse.get("path").toString());
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }
        });
    }

    public void onRegisterPress(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}
