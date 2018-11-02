package ca.mcgill.ecse321.passengerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    String token;
    SearchView viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        viewById = findViewById(R.id.search_bar);


        // Next here get all driver details and splash them on the view
        // Need first to get all the elements (findViewById())
        HttpUtils.get("journey/all", null, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    JSONArray s = response.getJSONArray("s");


                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    builder.setTitle("Fetching journeys failed");
                    builder.setMessage("Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
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


    public void sortByPrice(View view) {
    }

    public void sortByCarModel(View view) {
    }

    public void sortByDriver(View view) {
    }

    public void searchPressed(View view) {
        System.out.println(viewById.getQuery().toString());
    }
}
