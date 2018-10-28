package ca.mcgill.ecse321.driverapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import org.json.JSONException;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.io.IOException;
import android.text.format.DateFormat;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AddJourneyActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private String error = null;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    Spinner numPassengers;
    EditText pricePassenger, startLocation, endLocation;
    Button addJourney;
    Button selectTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_journey);

        numPassengers = (Spinner) findViewById(R.id.num_passenger);
        pricePassenger = (EditText) findViewById(R.id.price_passenger);
        startLocation = (EditText) findViewById(R.id.start_loc);
        endLocation = (EditText) findViewById(R.id.end_loc);
        addJourney = (Button) findViewById(R.id.add_journey_button);
        selectTime = (Button) findViewById(R.id.pickup_time_button);

        selectTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddJourneyActivity.this,
                        AddJourneyActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddJourneyActivity.this,
                AddJourneyActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        hourFinal = i;
        minuteFinal = i1;

        String pickuptime = yearFinal + "/" + monthFinal + "/" + dayFinal + " - "
                + hourFinal + ":" + minuteFinal;
        selectTime.setText(pickuptime);

    }
    public void addJourney(View v) {
        error = "";
//
//
//
//        HttpUtils.post("journey/create/" + tv.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
//            @Override
//            public void onFinish() {
//                tv.setText("");
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                try {
//                    error += errorResponse.get("message").toString();
//                } catch (JSONException e) {
//                    error += e.getMessage();
//                }
//            }
//        });
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
