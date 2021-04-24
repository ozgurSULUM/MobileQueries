package com.ozgurs.yazlabii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class Tip2Activity extends AppCompatActivity {
    private BigQuery bigquery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip2);
        try {
            bigquery = BigQueryOptions.newBuilder().setProjectId("crack-glider-304919")
                    .setCredentials(
                            ServiceAccountCredentials.fromStream(this.getAssets().open("crack-glider-304919-ce7e2081cc2a.json"))
                    ).build().getService();
        }catch (IOException e){

        }
    }

    public void tip2Query(View view){
        EditText txt1 = findViewById(R.id.editTextTextPersonName2);
        EditText txt2 = findViewById(R.id.editTextTextPersonName);
        String tarih1 = txt1.getText().toString();
        String tarih2 = txt2.getText().toString();
        if(tarih1.matches("") || tarih2.matches("")){
            Toast.makeText(this,"Lütfen tarihleri giriniz.",Toast.LENGTH_SHORT).show();
            return;
        }
        String que = "SELECT date(tpep_pickup_datetime) as pickup_date, trip_distance  " +
                "FROM `crack-glider-304919.taxi_zones.Trips` " +
                "WHERE trip_distance!= 0 AND date(tpep_pickup_datetime) BETWEEN " +'"'+tarih1+ '"'+ " AND " +'"'+tarih2+ '"'+
                " ORDER BY trip_distance ASC LIMIT 5";
        ArrayList<Tip2DataClass> data = new ArrayList<>();

        try{
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
                // String type
                double trip_distance = row.get("trip_distance").getDoubleValue();
                // Record type
                String date = row.get("pickup_date").getStringValue();
                data.add(new Tip2DataClass(date,trip_distance));
            }
        }catch(Exception e){

        }
        CustomAdapter1 customAdapter = new CustomAdapter1(data,Tip2Activity.this);
        ListView lw = findViewById(R.id.listView1);
        lw.setAdapter(customAdapter);
    }
}