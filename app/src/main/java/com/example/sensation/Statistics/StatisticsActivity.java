package com.example.sensation.Statistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.XYPlot;
import com.example.sensation.Home.HomeActivity;
import com.example.sensation.R;

public class StatisticsActivity extends AppCompatActivity {
    private XYPlot graph;
    private StatisticsPresenter statisticsPresenter;
    private Button homeButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        graph = findViewById(R.id.graph_stats);
        statisticsPresenter = new StatisticsPresenter(this);
        statisticsPresenter.InitializeGraph(graph);
        homeButt = findViewById(R.id.home_statistics);
        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
