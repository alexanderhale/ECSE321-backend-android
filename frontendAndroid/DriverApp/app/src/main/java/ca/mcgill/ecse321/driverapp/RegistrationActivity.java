package ca.mcgill.ecse321.driverapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RegistrationActivity extends AppCompatActivity {
    EditText usernameInput, passwordInput, carModelInput, fullNameInput, ageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        carModelInput = findViewById(R.id.carModel);
        fullNameInput = findViewById(R.id.fullname);
        ageInput = findViewById(R.id.age);
    }

    public void onBackPress(View view){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void onRegisterPress(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String carModel = carModelInput.getText().toString();
        String fullName = fullNameInput.getText().toString();
        String age = ageInput.getText().toString();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username", username);
            jsonParams.put("password", password);
            jsonParams.put("name", fullName);
            jsonParams.put("carModel", carModel);
            jsonParams.put("age", age);
        } catch (JSONException e) {
            Log.e("Error", "unexpected exception", e);
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("Error", "unexpected exception", e);
        }

        HttpUtils.post(this, "driver/register", entity, "application/json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String message = (String) response.get("message");
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    intent.putExtra("message", message);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setTitle("Registration failed");
                    builder.setMessage("Username already taken.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // do nothing
                        }
                    });
                    builder.show();
                    String error = (String) errorResponse.get("message");
                    System.out.println(errorResponse.get("path").toString());
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }
        });
    }
}
