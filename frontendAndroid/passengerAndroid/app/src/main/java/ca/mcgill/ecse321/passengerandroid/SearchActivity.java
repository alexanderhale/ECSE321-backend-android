package ca.mcgill.ecse321.passengerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private String error = null;

    List<Journey> journeys;             // all the journeys in the database
    List<Journey> matchingJourneys;     // the journeys in the database that match the inputted destination
    Intent intent;
    String token = intent.getStringExtra("token");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        intent = getIntent();

        // TODO finish this: get the full list of journeys in the system
        HttpUtils.get("journey/all", null, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TODO replace with proper error check
                try {
                    /*String username = (String) response.get("username");*/
                    driverName.setText(username);
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // TODO replace with proper error check
                /*try {
                    String error = (String) errorResponse.get("message");
                    System.out.println(errorResponse.get("path").toString());
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }*/
            }
        });
    }

    protected void searchJourneysByDestination(View view) {
        // get the text from the SearchView
        final SearchView sv = findViewById(R.id.search_bar);
        String destination = (String) sv.getQuery();

        // make a list with the journeys that match the destination
        List<Journey> matchingJourney;

        for (Journey j : journeys) {
            if (j.getEndCity().equals(destination)) {
                // when one is found, add it to the matchingJourneys list
                matchingJourneys.add(j);
            }
        }

        // add all matchingJourneys to the display list
        final RecyclerView drivers = findViewById(R.id.journey_search_driver);
        final RecyclerView carModels = findViewById(R.id.journey_search_car_model);
        final RecyclerView prices = findViewById(R.id.journey_search_price);

        for (Journey j : matchingJourneys) {
            // TODO probably need an adapter to make this easier
            // add j.getDriver() to drivers
                // this will return the driver ID, need another step to get the name
            // add j.getDriver().getCarModel() to carModels
            // add j.getPrice() to prices
        }

    }

    protected void sortByDriver(View view) {
        SortedList sortedByDriver = new SortedList<Journey>();
        // TODO sort matchingJourneys by the driver and output to RecyclerViews

    }

    protected void sortByCarModel(View view) {
        SortedList sortedByCarModel = new SortedList<Journey>();
        // TODO sort matchingJourneys by the carModel and output to RecyclerViews
    }

    protected void sortByPrice(View view) {
        SortedList sortedByPrice = new SortedList<Journey>();
        // TODO sort matchingJourneys by the price and output to RecyclerViews
    }

}
