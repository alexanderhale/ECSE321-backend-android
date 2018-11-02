package ca.mcgill.ecse321.driverapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ViewJourneysActivity extends AppCompatActivity {
    public int userId;
    Button backButton;

    public void closeJourneyAction() {

    }

    View.OnClickListener onCloseJourney = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewJourneysActivity.this);
            builder.setTitle("Close Journey");
            builder.setMessage("Confirm closing journey?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    closeJourneyAction();
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_journeys);
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        final Context that = this;

        final LinearLayout linLayout = findViewById(R.id.lin_layout);
        linLayout.setGravity(Gravity.CENTER);

        HttpUtils.get("journey/secure/all", null, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    TextView start = new TextView(that);
                    TextView end = new TextView(that);
                    TextView noPass = new TextView(that);

                    Button closeJourney = new Button(that);
                    Button modifyJourney = new Button(that);
                    closeJourney.setText("Close Journey");
                    modifyJourney.setText("Modify Journey");

                    closeJourney.setOnClickListener(onCloseJourney);

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String startAddress = obj.getString("startAddress");
                        String startCity = obj.getString("startCity");
                        String startCountry = obj.getString("startCountry");

                        String endAddress = obj.getString("endAddress");
                        String endCity = obj.getString("endCity");
                        String endCountry = obj.getString("endCountry");

                        int numberOfPassengers = obj.getInt("numberOfPassengers");
                        int capacity = obj.getInt("capacity");

                        boolean isClosed = obj.getBoolean("closed");
                        if (isClosed) closeJourney.setEnabled(false);

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

                        start.setText("Start: " + startAddress);
                        end.setText("End: " + endAddress);
                        noPass.setText("Passengers : " + Integer.toString(numberOfPassengers) + "/" + Integer.toString(capacity));

                        start.setTextColor(Color.parseColor("#000000"));
                        end.setTextColor(Color.parseColor("#000000"));
                        noPass.setTextColor(Color.parseColor("#000000"));

                        textLayout.addView(start);
                        textLayout.addView(end);
                        textLayout.addView(noPass);
                        textLayout.addView(closeJourney);
                        textLayout.addView(modifyJourney);

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


}
