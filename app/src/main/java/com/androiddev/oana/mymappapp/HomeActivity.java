package com.androiddev.oana.mymappapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private String input;
    //start implementation of Place Picker
    int PLACE_PICKER_REQUEST = 1;
    private Button pickAPlaceButton;
    private TextView placeAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //implementing Place Picker
        placeAddress = (TextView)findViewById(R.id.search_input);


        //Button and ClickListener
        pickAPlaceButton = (Button)findViewById(R.id.search_button);
        pickAPlaceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                //building the intent
                Intent intent = null;
                try
                {
                    try {
                        intent = builder.build((Activity)getApplicationContext());
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                }
                catch(GooglePlayServicesRepairableException e)
                {
                    e.printStackTrace();
                }

            }
        });

    }

    //create method to receive selected place and set it to the get place text view
    //onActivity Result has 3 parameters:requestCode to check what we send from get_place,
    //resultCode and data that will come from the Place Picker user Interface Widget
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==PLACE_PICKER_REQUEST && resultCode==RESULT_OK)
        {

                getPickerResult(data);
        }
    }

    private void getPickerResult(Intent data){
       Place placePicked = PlacePicker.getPlace(data, this);

        String address = String.format("Places:%s",placePicked.getAddress());
        placeAddress.setText(address);
        // call google maps?
        startMapsActivity(address);
    }
    public void startMapsActivity(String s){
        //TextView textView = (TextView) findViewById(R.id.search_input);
        //input = textView.getText().toString();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("searchInput", s);
        startActivity(intent);
    }
    /*
    public void startMapsActivity(View v){
        TextView textView = (TextView) findViewById(R.id.search_input);
        input = textView.getText().toString();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("searchInput", input);
        startActivity(intent);
    }*/
}