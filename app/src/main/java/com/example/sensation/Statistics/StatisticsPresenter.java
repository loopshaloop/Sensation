package com.example.sensation.Statistics;

import android.graphics.Color;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import com.example.sensation.Statistics.StatisticsActivity;

import java.util.Arrays;

public class StatisticsPresenter {
    private StatisticsActivity statisticsActivity;
    public StatisticsPresenter(StatisticsActivity statisticsActivity){
        this.statisticsActivity = statisticsActivity;
    }

    public void InitializeGraph(XYPlot graph){

        Number[] series1Numbers = {2, 3, 4, 5, 6, 7, 8, 9};
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series 1");
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.RED, Color.TRANSPARENT, null);
        graph.addSeries(series1, series1Format);
    }
}
