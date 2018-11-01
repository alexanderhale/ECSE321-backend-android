package ca.mcgill.ecse321.passengerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class PassengerProfileActivity extends AppCompatActivity {
    int rating = 0;
    String token;
    EditText username, fullname, password;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        username = findViewById(R.id.username_textview);
        fullname = findViewById(R.id.fullname_textview);
        password = findViewById(R.id.password_textview);
        ratingBar = findViewById(R.id.ratingBar);


        // Next here get all driver details and splash them on the view
        // Need first to get all the elements (findViewById())
        HttpUtils.get("rider/secure/me", null, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String name_str = response.get("name").toString();
                    String username_str = response.get("username").toString();
                    String password_str = response.get("password").toString();
                    //int rating = Integer.parseInt(response.get("rating").toString());
                    //int age = Integer.parseInt(response.get("age").toString());

                    fullname.setText(name_str);
                    username.setText(username_str);
                    password.setText(password_str);

                    if (response.get("rating") != null) {
                        rating = (int) response.get("rating");
                    }
                    ratingBar.setRating((float) rating);

                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PassengerProfileActivity.this);
                    builder.setTitle("Fetching profile failed");
                    builder.setMessage("Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(PassengerProfileActivity.this, MainActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                            finish();
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

    public void saveProfile(){
        String username_str = username.getText().toString();
        String password_str = password.getText().toString();
        String fullName_str = fullname.getText().toString();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username", username_str);
            jsonParams.put("password", password_str);
            jsonParams.put("name", fullName_str);
        } catch (JSONException e) {
            Log.e("Error", "unexpected exception", e);
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("Error", "unexpected exception", e);
        }
        HttpUtils.put(this, "driver/secure/modify", entity, "application/json", token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(PassengerProfileActivity.this, MainActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PassengerProfileActivity.this);
                    builder.setTitle("Saving changes failed");
                    builder.setMessage("Please try again.");
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


    public void onSaveClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(PassengerProfileActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to save changes ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveProfile();
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

    public void onCancelClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(PassengerProfileActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to cancel ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PassengerProfileActivity.this, MainActivity.class);
                intent.putExtra("token", token);
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

}
