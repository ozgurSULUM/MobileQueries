package com.ozgurs.yazlabii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip3);
    }

    public void tip3Query(View view){
        EditText text = findViewById(R.id.editTextTextPersonName3);
        String tarih = text.getText().toString();
        String que = "SELECT date(tpep_pickup_datetime) as pickup_date ,PULocationID,DOLocationID,trip_distance  " +
                "FROM `crack-glider-304919.taxi_zones.Trips` " +
                "WHERE date(tpep_pickup_datetime)=" + '"'+ tarih +'"' + " AND PULocationID!=DOLocationID " +
                "ORDER BY trip_distance DESC LIMIT 5";
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
        }catch(Exception e){

        }
        CustomAdapter2 customAdapter = new CustomAdapter2(data,Tip3Activity.this);
        ListView lw = findViewById(R.id.listView2);
        lw.setAdapter(customAdapter);



    }
    public void openOnMap(View view){
        Intent intent = new Intent(this, MapsActivity.class );
        startActivity(intent);
    }


}