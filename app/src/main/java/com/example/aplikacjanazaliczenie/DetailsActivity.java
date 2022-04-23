package com.example.aplikacjanazaliczenie;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DetailsActivity extends AppCompatActivity {

    private void addElement(String text){
        LinearLayout detailsLayout = findViewById(R.id.details_layout);

        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(8, 16, 0, 0);

        textView.setLayoutParams(params);
        detailsLayout.addView(textView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("json");

            LinearLayout detailsLayout = findViewById(R.id.details_layout);

            try {
                JSONObject jsonObject = new JSONObject(value);
                JSONObject responseObject = jsonObject.getJSONObject("response");
                JSONArray holidaysArray = responseObject.getJSONArray("holidays");

                for(int i = 0; i < holidaysArray.length(); i++){
                    JSONObject holidaysObject = holidaysArray.getJSONObject(i);
                    JSONObject dateObject = holidaysObject.getJSONObject("date");
                    JSONObject datetimeObject = dateObject.getJSONObject("datetime");

                    String date = datetimeObject.getString("year") + "-" +
                            datetimeObject.getString("month") + "-" +
                            datetimeObject.getString("day");
                    String name = holidaysObject.getString("name");

                    this.addElement("Nazwa: " + name + "\nData: " + date);
                }

            } catch (JSONException e) {
                this.addElement("Wystąpił błąd w pobraniu danych.");
            }
        }


    }
}