package com.example.sensation.Statistics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.XYPlot;
import com.example.sensation.R;

public class StatisticsActivity extends AppCompatActivity {
    private XYPlot graph;
    private StatisticsPresenter statisticsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        graph = findViewById(R.id.graph_stats);
        statisticsPresenter = new StatisticsPresenter(this);
        statisticsPresenter.InitializeGraph(graph);
    }
}
