package ca.mcgill.ecse321.passengerapp;

import android.content.Context;
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
    CardView cv;


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

        final View.OnClickListener onJoinJourney = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                joinPress(v.getId());

            }
        };
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
                    TextView pricePerPass = new TextView(that);
                    TextView pickupTime = new TextView(that);
                    Button joinJourney = new Button(that);

                    joinJourney.setOnClickListener(onJoinJourney);
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String driverId = obj.getString("driver");
                        String journeyId = obj.getString("journeyid");
                        int numberOfPassengers = obj.getInt("numberOfPassengers");
                        int capacity = obj.getInt("capacity");

                        String startAddress = obj.getString("startAddress");
                        String startCity = obj.getString("startCity");
                        String startCountry = obj.getString("startCountry");

                        String endAddress = obj.getString("endAddress");
                        String endCity = obj.getString("endCity");
                        String endCountry = obj.getString("endCountry");
                        String pickupTime_str = obj.getString("timePickup");

                        int price = obj.getInt("price");

                        boolean isClosed = obj.getBoolean("closed");
                        if (isClosed){
                            joinJourney.setEnabled(false);
                        }
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
                        noPass.setText("Passengers : " + Integer.toString(numberOfPassengers) + "/" + Integer.toString(capacity));
                        end.setText("End: " + endAddress + ", " + endCity);
                        noPass.setText("Passengers : " + Integer.toString(numberOfPassengers) + "/" + Integer.toString(capacity));
                        pricePerPass.setText("Price : " + price + " $");
                        pickupTime.setText("Time : " + pickupTime_str);
                        start.setTextColor(Color.parseColor("#000000"));
                        end.setTextColor(Color.parseColor("#000000"));
                        noPass.setTextColor(Color.parseColor("#000000"));
                        pricePerPass.setTextColor(Color.parseColor("#000000"));
                        pickupTime.setTextColor(Color.parseColor("#000000"));

                        driver.setTextColor(Color.parseColor("#000000"));
                        journey.setTextColor(Color.parseColor("#000000"));
                        start.setTextColor(Color.parseColor("#000000"));
                        end.setTextColor(Color.parseColor("#000000"));
                        noPass.setTextColor(Color.parseColor("#000000"));

                        joinJourney.setText("Join journey");

                        textLayout.addView(journey);
                        textLayout.addView(driver);
                        textLayout.addView(start);
                        textLayout.addView(end);
                        textLayout.addView(pricePerPass);
                        textLayout.addView(pickupTime);
                        textLayout.addView(joinJourney);
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

    public void joinPress(int id){
        String urlJoin = "journey/"+id+"/addRider";
//        HttpUtils.put(urlJoin, null, token, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//
//            }
//        }
    }


    public void searchPressed(View view) {

        String adressSearched = viewById.getQuery().toString();
       // cv.removeAllViews();

    }

    public void sortByDriver(View view) {
    }

    public void sortByCarModel(View view) {
    }

    public void sortByPrice(View view) {
    }
}
