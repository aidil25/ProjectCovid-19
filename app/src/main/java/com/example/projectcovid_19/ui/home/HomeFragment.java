package com.example.projectcovid_19.ui.home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectcovid_19.R;
import com.example.projectcovid_19.model.ResponseKabupaten;
import com.example.projectcovid_19.model.ResponseProvinsi;
import com.example.projectcovid_19.model.ResultsItem;
import com.example.projectcovid_19.network.Client;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    TextView sembuh,positif,meninggal,time;
    AppCompatSpinner spinner;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private GoogleMap mMap;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private AlertDialog dialog;
    float zoomRadius = 15;
    SupportMapFragment mapFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        sembuh = root.findViewById(R.id.tv_sembuh);
        meninggal = root.findViewById(R.id.tv_meninggal);
        positif = root.findViewById(R.id.tv_positif);
        spinner = root.findViewById(R.id.spinner);
        time = root.findViewById(R.id.time);
        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment.getMapAsync(this);
        getSebaran();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showLoading();
                String kodeKab = getResources().getStringArray(R.array.endKota)[spinner.getSelectedItemPosition()];
                getdata(kodeKab);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getdata(String kodeKab) {
        Client.getInstance().getCoronaProv(kodeKab).enqueue(new Callback<ResponseProvinsi>() {
            @Override
            public void onResponse(Call<ResponseProvinsi> call, Response<ResponseProvinsi> response) {
                if(response.isSuccessful()){
                    sembuh.setText(response.body().getCovidSembuh());
                    positif.setText(response.body().getPositif());
                    meninggal.setText(response.body().getCovidMeninggal());
                    time.setText("Terakhir di update "+response.body().getWaktu());
                }else{
                    Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProvinsi> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading() {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        resultLocation();
    }



    public void resultLocation() {
        LatLng sydney = new LatLng(-0.8940217,100.3676151);
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker())
                .position(sydney).title("Hello")
                .draggable(true)
                .flat(true)
                .visible(true));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,7.0f));
    }

    public void getSebaran(){
        Client.getInstance().getMapsCov().enqueue(new Callback<ResponseKabupaten>() {
            @Override
            public void onResponse(Call<ResponseKabupaten> call, Response<ResponseKabupaten> response) {
                if(response.isSuccessful()){
                    if (response.body() != null){
                       showData(response.body().getResults());
                    }else{
                        message("data not found");
                    }
                }else{
                    message("data not found");
                }
            }

            @Override
            public void onFailure(Call<ResponseKabupaten> call, Throwable t) {
                message(t.getMessage());
            }
        });
    }


    public void showData(List<ResultsItem> list) {
        for(int i =0; i<list.size(); i++){
            LatLng dangerous_area = new LatLng(Float.parseFloat(list.get(i).getLatitude()),Float.parseFloat(list.get(i).getLongitude()));
            mMap.addCircle(new CircleOptions()
                    .center(dangerous_area)
                    .radius(1000*zoomRadius)
                    .strokeColor(Color.RED)
                    .fillColor(Color.parseColor("#2DE31818"))
                    .strokeWidth(5.0f)
                    .visible(true)
            );
        }
    }




    public void message(String message) {
        Log.d(TAG, "message: "+message);
        Toast.makeText(getActivity(), "error :"+message, Toast.LENGTH_SHORT).show();

    }

}