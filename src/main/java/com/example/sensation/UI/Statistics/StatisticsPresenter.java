package com.example.sensation.UI.Statistics;

import android.graphics.Color;
import com.example.sensation.Model.Game;
import com.example.sensation.Persistence.Repository;
import com.example.sensation.Model.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StatisticsPresenter implements Repository.LoadUserListener{
    private StatisticsActivity statisticsActivity;
    private Repository repository;
    private User user;
    private LineChart mChart;

    public StatisticsPresenter(StatisticsActivity statisticsActivity){
        this.statisticsActivity = statisticsActivity;
        this.repository = Repository.getInstance();
        this.mChart = statisticsActivity.mChart;
        repository.setLoadUserListener(this);
        repository.readUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
    public void initializeGraph(){
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();
        games = user.sortGames();
        for(int i = 0; i < games.size(); i++){
            Game game = games.get(i);
            yValues.add(new Entry(game.getDate().getTime(), game.getScore()));
        }
        LineDataSet set1 = new LineDataSet(yValues, "Scores");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)value));
            }
        });
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        set1.setFillAlpha(0);
        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextColor(Color.WHITE);
        mChart.getXAxis().setLabelRotationAngle(-45);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setData(data);
    }
    @Override
    public void updateUser(User user)
    {
        this.user = user;
        statisticsActivity.setBest(user);
    }
}
