package com.lco.wheatherapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText location;
    Button search;
    TextView result,pressure,humidity;
    RequestQueue mQueue;
    String locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location=findViewById(R.id.location);
        search=findViewById(R.id.button);
        result=findViewById(R.id.result);
        pressure=findViewById(R.id.pressure);
        humidity=findViewById(R.id.humidity);
        mQueue= Volley.newRequestQueue(this);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locate=location.getText().toString();
                //location.setText("");
                result.setText("");
                pressure.setText("");
                humidity.setText("");

                loadurl(locate);

            }
        });

    }
    public void loadurl(String locate){
        String URL="http://api.openweathermap.org/data/2.5/weather?q="+locate+"&appid=6e77d7e6e65dbfc9a3768d61b203e12c";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=response.getJSONObject("main");
                            Double temp=jsonObject.getDouble("temp");
                            temp=temp-273.15;
                            int pressureapi=jsonObject.getInt("pressure");
                            int humidityapi=jsonObject.getInt("humidity");
                            result.setText(String.valueOf(temp));
                            pressure.setText("pressure :"+pressureapi+"");
                            humidity.append("Humidity :"+humidityapi+"%");




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

}