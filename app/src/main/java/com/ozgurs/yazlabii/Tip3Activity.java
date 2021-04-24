package com.ozgurs.yazlabii;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Tip3Activity extends AppCompatActivity {
    private ArrayList<Tip3DataClass> data;
    private Lat_Lng latLng_pickup;
    private Lat_Lng latLng_drop;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip3);
        button = findViewById(R.id.button3);
        button.setVisibility(View.INVISIBLE);
    }

    public void tip3Query(View view){
        EditText text = findViewById(R.id.editTextTextPersonName3);
        String tarih = text.getText().toString();
        if(tarih.matches("")){
            Toast.makeText(this,"Lütfen tarih giriniz.",Toast.LENGTH_SHORT).show();
            return;
        }else{
            button.setVisibility(View.VISIBLE);
        }
        String que = "SELECT date(tpep_pickup_datetime) as pickup_date ,PULocationID,DOLocationID,trip_distance  " +
                "FROM `crack-glider-304919.taxi_zones.Trips` " +
                "WHERE date(tpep_pickup_datetime)=" + '"'+ tarih +'"' + " AND PULocationID!=DOLocationID " +
                "ORDER BY trip_distance DESC LIMIT 1";
        data = new ArrayList<>();
        try{
            BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("crack-glider-304919")
                    .setCredentials(
                            ServiceAccountCredentials.fromStream(this.getAssets().open("crack-glider-304919-ce7e2081cc2a.json"))
                    ).build().getService();

            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(
                            que)
                            .setUseLegacySql(false)
                            .build();

            JobId jobId = JobId.of(UUID.randomUUID().toString());

            Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
            queryJob = queryJob.waitFor();

            if (queryJob == null) {
                throw new RuntimeException("Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                // You can also look at queryJob.getStatus().getExecutionErrors() for all
                // errors, not just the latest one.
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }

            // Get the results.
            TableResult result = queryJob.getQueryResults();

            // Print all pages of the results.
            for (FieldValueList row : result.iterateAll()) {
                double trip_distance = row.get("trip_distance").getDoubleValue();
                String date = row.get("pickup_date").getStringValue();
                BigDecimal pickup_location = row.get("PULocationID").getNumericValue();
                BigDecimal drop_location = row.get("DOLocationID").getNumericValue();
                data.add(new Tip3DataClass(date,pickup_location.intValue(),drop_location.intValue(),trip_distance));
            }
            if(data.get(0) != null){

                latLng_pickup = getLatLng(data.get(0).getPickup_location(),bigquery);
                latLng_drop = getLatLng(data.get(0).getDrop_location(),bigquery);
            }
        }catch(Exception e){

        }
        CustomAdapter2 customAdapter = new CustomAdapter2(data,Tip3Activity.this);
        ListView lw = findViewById(R.id.listView2);
        lw.setAdapter(customAdapter);



    }
    public Lat_Lng getLatLng (int locationID, BigQuery bigQuery) throws Exception{
        String que = "SELECT latitude as lng, longitude as lat  FROM `crack-glider-304919.taxi_zones.zones` WHERE LocationID = "+locationID+ " LIMIT 1";
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(
                        que)
                        .setUseLegacySql(false)
                        .build();

        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the results.
        TableResult result = queryJob.getQueryResults();

        // Print all pages of the results.
        for (FieldValueList row : result.iterateAll()) {
            double lng = row.get("lng").getDoubleValue();
            double lat = row.get("lat").getDoubleValue();
            return new Lat_Lng(lat,lng);
        }
        return null;
    }

    public void openOnMap(View view){
        if(latLng_pickup != null && latLng_drop != null) {
            Intent intent = new Intent(this, Tip4Activity.class);
            intent.putExtra("latLng_pickup", latLng_pickup);
            intent.putExtra("latLng_drop", latLng_drop);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Herhangi bir sorgulama yapılmamış.",Toast.LENGTH_SHORT).show();
            return;
        }

    }



}