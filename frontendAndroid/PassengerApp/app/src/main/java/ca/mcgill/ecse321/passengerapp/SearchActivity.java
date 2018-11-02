package ca.mcgill.ecse321.passengerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

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
        final Context that = this;

        final LinearLayout linLayout = findViewById(R.id.lin_layout);
        linLayout.setGravity(Gravity.CENTER);


        // Next here get all driver details and splash them on the view
        // Need first to get all the elements (findViewById())
        HttpUtils.get("journey/all", null, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    TextView driver = new TextView(that);
                    TextView journey = new TextView(that);
                    TextView start = new TextView(that);
                    TextView end = new TextView(that);
                    TextView noPass = new TextView(that);

                   // closeJourney.setOnClickListener(onCloseJourney);

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String driverId = obj.getString("driver");
                        String journeyId = obj.getString("journeyid");
                        String startAddress = obj.getString("startAddress");
                        int numberOfPassengers = obj.getInt("numberOfPassengers");
                        int capacity = obj.getInt("capacity");


                        CardView cv = new CardView(that);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 20, 0, 20);
                        cv.setLayoutParams(params);
                        cv.setContentPadding(50, 50, 50, 50);
                        cv.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        cv.setMinimumHeight(400);
                        cv.setMinimumWidth(1000);

                        LinearLayout textLayout = new LinearLayout(that);
                        textLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        textLayout.setLayoutParams(textLayoutParams);

                        driver.setText("Driver "+ driverId);
                        journey.setText("Journey "+ journeyId);
                        start.setText("Start: " + startAddress);
                        noPass.setText("Passengers : " + Integer.toString(numberOfPassengers) + "/" + Integer.toString(capacity));

                        start.setTextColor(Color.parseColor("#000000"));
                        end.setTextColor(Color.parseColor("#000000"));
                        noPass.setTextColor(Color.parseColor("#000000"));

                        textLayout.addView(journey);
                        textLayout.addView(driver);
                        textLayout.addView(start);
                        textLayout.addView(end);
                        textLayout.addView(noPass);

                        cv.addView(textLayout);

                        linLayout.addView(cv);

                    } catch (JSONException e) {
                        Log.e("Error", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
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
