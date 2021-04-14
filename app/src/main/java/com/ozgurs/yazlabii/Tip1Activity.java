package com.ozgurs.yazlabii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import java.io.IOException;
import java.util.ArrayList;

public class Tip1Activity extends AppCompatActivity {

    private ArrayList<Tip1DataClass> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip1);

        data = new ArrayList<>();
        try {
            BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("crack-glider-304919")
                    .setCredentials(
                            ServiceAccountCredentials.fromStream(this.getAssets().open("crack-glider-304919-ce7e2081cc2a.json"))
                    ).build().getService();

            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(
                            "SELECT SUM(passenger_count) AS sum_passenger, date(tpep_pickup_datetime) AS pick_date FROM `crack-glider-304919.taxi_zones.Trips` GROUP BY pick_date ORDER BY sum_passenger DESC LIMIT 5")
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
                BigDecimal sum_passenger = row.get("sum_passenger").getNumericValue();
                // Record type
                String date = row.get("pick_date").getStringValue();
                System.out.println("sayi: "+sum_passenger+" tarih: "+date);
                data.add(new Tip1DataClass(sum_passenger.intValue(),date));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //data.add(new Tip1DataClass(20,"2020-12-07"));
        //data.add(new Tip1DataClass(25,"2020-12-06"));
        CustomAdapter customAdapter = new CustomAdapter(data,Tip1Activity.this);
        ListView lw = findViewById(R.id.listView);
        lw.setAdapter(customAdapter);

    }
}