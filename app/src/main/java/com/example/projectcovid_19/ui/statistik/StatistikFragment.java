package com.example.projectcovid_19.ui.statistik;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectcovid_19.R;
import com.example.projectcovid_19.model.kabupaten.Response;
import com.example.projectcovid_19.model.kabupaten.ResultsItem;
import com.example.projectcovid_19.network.Client;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class StatistikFragment extends Fragment {

    private StatistikViewModel dashboardViewModel;
    TextView sembuh,positif,meninggal,times;
    Spinner spinner;
    private List<Entry> yValues = new ArrayList<>();
    private LineChart lineChart;
    private List<Entry> deathValues = new ArrayList<>();
    private List<Entry> recoveryValues = new ArrayList<>();
    private ArrayList<String> xValues = new ArrayList<>();
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(StatistikViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistik, container, false);
        sembuh = root.findViewById(R.id.tv_sembuh);
        positif = root.findViewById(R.id.tv_positif);
        meninggal = root.findViewById(R.id.tv_meninggal);
        spinner = root.findViewById(R.id.spinner);
        times = root.findViewById(R.id.time);
        lineChart = root.findViewById(R.id.lineChart);
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();
        lineChart.getAxisRight().setEnabled(false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showLoading(true);
                clearData();
                String kodeKab = getResources().getStringArray(R.array.kabupaten_sumbar)[spinner.getSelectedItemPosition()];
                getdata(kodeKab);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
    }

    private void clearData() {
        yValues.clear();
        deathValues.clear();
        recoveryValues.clear();
        xValues.clear();
    }

    private void getdata(String kodeKab) {
        Client.getInstance().getKab(kodeKab).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()){
                    showLoading(false);
                    setChart(response.body().getResults());
                }else{
                    showLoading(false);
                    Toast.makeText(getActivity(), "Eror response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                showLoading(false);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setChart(List<ResultsItem> results) {
        if(results != null){
            int time =0;
            for(int i= results.size()-7 ; i<results.size(); i++){
                Log.i("TAG",results.get(i).getPositif());
                String[] parts = results.get(i).getTglUpdate().split(" ");
                String[] parts1 = parts[0].split("-");
                yValues.add(new Entry(time,Float.parseFloat(results.get(i).getPositif())));
                deathValues.add(new Entry(time,Float.parseFloat(results.get(i).getCovidMeninggal())));
                recoveryValues.add(new Entry(time,Float.parseFloat(results.get(i).getCovidSembuh())));
                xValues.add(parts1[2]);
                time++;
            }
            sembuh.setText(results.get(results.size()-1).getCovidSembuh());
            positif.setText(results.get(results.size()-1).getPositif());
            meninggal.setText(results.get(results.size()-1).getCovidMeninggal());
            times.setText("Terakhir di update "+results.get(results.size()-1).getTglUpdate());
            lineChart.setPinchZoom(false);
            lineChart.setBackgroundColor(Color.WHITE);
            lineChart.getDescription().setEnabled(false);
            lineChart.animateX(1500);

            MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custome_marker_view);

            // Set the marker to the chart
            mv.setChartView(lineChart);
            lineChart.setMarker(mv);

            LineDataSet d1 = new LineDataSet(yValues, "Positif : " + results.get(results.size()-1).getPositif());
            d1.setLineWidth(2.5f);
            d1.setCircleRadius(4.5f);
            d1.setHighLightColor(Color.rgb(244, 117, 117));
            d1.setDrawFilled(true);
            d1.setDrawValues(true);


            LineDataSet d2 = new LineDataSet(deathValues, "Meninggal : " + results.get(results.size()-1).getCovidMeninggal());
            d2.setLineWidth(2.5f);
            d2.setCircleRadius(4.5f);
            d2.setHighLightColor(Color.rgb(244, 117, 117));
            d2.setColor(Color.RED);
            d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            d2.setFillColor(Color.RED);
            d2.setDrawFilled(true);
            d2.setDrawValues(true);
            d2.setValueTextColor(Color.RED);

            LineDataSet d3 = new LineDataSet(recoveryValues, "sembuh : " + results.get(results.size()-1).getCovidSembuh());
            d3.setLineWidth(2.5f);
            d3.setCircleRadius(4.5f);
            d3.setHighLightColor(Color.rgb(244, 117, 117));
            d3.setColor(Color.GREEN);
            d3.setFillColor(Color.GREEN);
            d3.setDrawFilled(true);
            d3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            d3.setDrawValues(true);


            ArrayList<ILineDataSet> sets = new ArrayList<>();
            sets.add(d1);
            sets.add(d2);
            sets.add(d3);
            LineData data = new LineData(sets);
            lineChart.setData(data);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setValueFormatter(new MyAxisFormat(xValues));
            xAxis.setPosition(XAxis.XAxisPosition.TOP);

        }else{
            Toast.makeText(getActivity(), "Data null", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyAxisFormat extends ValueFormatter {
        ArrayList<String> mValues;

        public MyAxisFormat(ArrayList<String> mValues) {
            this.mValues = mValues;
        }

        @Override
        public String getFormattedValue(float value) {
            return mValues.get((int) value);
        }
    }

    private void showLoading(boolean status) {
        if(status){
            dialog.show();
        }else{
            dialog.dismiss();
        }
    }
}