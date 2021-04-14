package com.ozgurs.yazlabii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        try {
            bigquery = BigQueryOptions.newBuilder().setProjectId("crack-glider-304919")
                    .setCredentials(
                            ServiceAccountCredentials.fromStream(this.getAssets().open("crack-glider-304919-ce7e2081cc2a.json"))
                    ).build().getService();


            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(
                            "SELECT * FROM `crack-glider-304919.taxi_zones.Trips` LIMIT 1000")
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
                // String type
                BigDecimal id = row.get("VendorID").getNumericValue();
                // Record type
                long pickup_time = row.get("tpep_pickup_datetime").getTimestampValue();
                long dropoff_time = row.get("tpep_dropoff_datetime").getTimestampValue();
                BigDecimal passenger_count = row.get("passenger_count").getNumericValue();
                BigDecimal trip_distance = row.get("trip_distance").getNumericValue();
                BigDecimal PULocationID = row.get("PULocationID").getNumericValue();
                BigDecimal DOLocationID = row.get("DOLocationID").getNumericValue();
                BigDecimal total_amount = row.get("total_amount").getNumericValue();
                // String Repeated type

                //int date1 = (int) Float.parseFloat(pickup_time);
                //int date2 = (int) Float.parseFloat(dropoff_time);

                Date date3 = new Date(date1*1000);
                Date date4 = new Date(date2*1000);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  |  hh:mm a");
                String time1 = sdf.format(date3);
                String time2 = sdf.format(date4);

                System.out.println("id: " + id + " pickup_time: " + pickup_time + " dropoff_time " + dropoff_time+ " passenger_count: " + passenger_count + " trip_distance: " + trip_distance
                        + " PULocationID: " + PULocationID + " DOLocationID: " + DOLocationID + " total_amount: " + total_amount);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        */
    }

    public void tip1_function(View view){
        Intent intent = new Intent(this,Tip1Activity.class);
        startActivity(intent);
    }

    public void tip2_function(View view){
        Intent intent = new Intent(this,Tip2Activity.class);
        startActivity(intent);
    }

    public void tip3_function(View view){
        Intent intent = new Intent(this,Tip3Activity.class);
        startActivity(intent);
    }



}