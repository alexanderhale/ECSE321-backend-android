package ca.mcgill.ecse321.driverapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import org.json.JSONException;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.IOException;
import android.text.format.DateFormat;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class EditJourneyActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    Spinner numPassengers;
    EditText pricePassenger, startLocation, endLocation;
    Button editJourney;
    Button selectTime;
    Button cancelButton;
    String token, journeyId, driverId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_journey);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        journeyId = intent.getStringExtra("id");

        numPassengers = (Spinner) findViewById(R.id.num_passenger);
        pricePassenger = (EditText) findViewById(R.id.price_passenger);
        startLocation = (EditText) findViewById(R.id.start_loc);
        endLocation = (EditText) findViewById(R.id.end_loc);
        editJourney = (Button) findViewById(R.id.edit_journey_button);
        selectTime = (Button) findViewById(R.id.pickup_time_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        String journeyUrl = "journey/" + journeyId + "/me";
        HttpUtils.get(journeyUrl, null, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String numPassengers_str = response.get("numberOfPassengers").toString();
                    String pricePassenger_str = response.get("price").toString();
                    String startAddr_str = response.get("startAddress").toString();
                    String startCity_str = response.get("startCity").toString();
                    String startCountry_str = response.get("startCountry").toString();
                    String endAddr_str = response.get("endAddress").toString();
                    String endCity_str = response.get("endCity").toString();
                    String endCountry_str = response.get("endCountry").toString();
                    String time_str = response.get("timePickup").toString();
                    driverId = response.get("driver").toString();

                    //setSelection() works with position 0-indexed which is why we use -1
                    numPassengers.setSelection(Integer.getInteger(numPassengers_str)-1);
                    pricePassenger.setText(pricePassenger_str);
                    startLocation.setText(startAddr_str + ", " + startCity_str + ", " + startCountry_str);
                    endLocation.setText(endAddr_str + ", " + endCity_str + ", " + endCountry_str);
                    selectTime.setText(time_str);

                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println(responseString);
            }

//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                try {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(EditJourneyActivity.this);
//                    builder.setTitle("Fetching journey failed");
//                    builder.setMessage("Please try again.");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Intent intent = new Intent(EditJourneyActivity.this, ViewJourneysActivity.class);
//                            intent.putExtra("token", token);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                    builder.show();
//                    System.out.println(errorResponse.get("path").toString());
//                } catch (JSONException e) {
//                    Log.e("Error", "unexpected exception", e);
//                }
//            }
        });

        selectTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditJourneyActivity.this,
                        EditJourneyActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    public void editJourneyAction(){

        String startLoc_str = startLocation.getText().toString();
        String endLoc_str = endLocation.getText().toString();
        String[] detailed_startLoc = startLoc_str.split(",");
        String[] detailed_endLoc = endLoc_str.split(",");
        String pricePass_str = pricePassenger.getText().toString();
        String numberPass_str = numPassengers.getSelectedItem().toString();
        LatLng startLoc = getLocationFromAddress(startLoc_str);
        LatLng endLoc = getLocationFromAddress(endLoc_str);
        String timePickup = selectTime.getText().toString();


        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("driver", driverId);
            jsonParams.put("numberOfPassengers", 0);
            jsonParams.put("capacity", numberPass_str);
            jsonParams.put("price", pricePass_str);
            jsonParams.put("startLat", String.valueOf(startLoc.latitude));
            jsonParams.put("startLong", String.valueOf(startLoc.longitude));
            jsonParams.put("startAddress", detailed_startLoc[0]);
            jsonParams.put("startCity", detailed_startLoc[1]);
            jsonParams.put("startCountry", detailed_startLoc[2]);
            jsonParams.put("endLat", String.valueOf(endLoc.latitude));
            jsonParams.put("endLong", String.valueOf(endLoc.longitude));
            jsonParams.put("endAddress", detailed_endLoc[0]);
            jsonParams.put("endCity", detailed_endLoc[1]);
            jsonParams.put("endCountry", detailed_endLoc[2]);
            jsonParams.put("timePickup", timePickup);

        } catch (JSONException e) {
            Log.e("Error", "unexpected exception", e);
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("Error", "unexpected exception", e);
        }
        String journeyUrl = "journey/"+journeyId+"/modify";
        HttpUtils.put(this, journeyUrl, entity, "application/json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(EditJourneyActivity.this, ViewJourneysActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditJourneyActivity.this);
                    builder.setTitle("Adding journey failed");
                    builder.setMessage("Please try again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // do nothing
                        }
                    });
                    builder.show();
                    System.out.println(errorResponse.toString());
                    System.out.println("ERROR STATUS CODE IS :::: " + statusCode);
                    System.out.println(errorResponse.get("path").toString());
                } catch (JSONException e) {
                    Log.e("Error", "unexpected exception", e);
                }
            }
        });
    }

    public void editJourneyErrorCheck(){
        try{
            String startLoc_str = startLocation.getText().toString();
            String endLoc_str = endLocation.getText().toString();
            String[] detailed_startLoc = startLoc_str.split(",");
            String[] detailed_endLoc = endLoc_str.split(",");
            editJourneyAction();
        }
        catch (IndexOutOfBoundsException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(EditJourneyActivity.this);
            builder.setTitle("Wrong address format");
            builder.setMessage("Proper format is : Street Address, City, Country. Please try again");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
        }
    }

    public void onEditClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditJourneyActivity.this);
        builder.setTitle("Edit journey");
        builder.setMessage("Confirm editing journey?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editJourneyErrorCheck();
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

    public void cancelOnClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditJourneyActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to cancel ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(EditJourneyActivity.this, ViewJourneysActivity.class);
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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditJourneyActivity.this,
                EditJourneyActivity.this, hour, minute, DateFormat.is24HourFormat(this));
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

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(EditJourneyActivity.this);
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
