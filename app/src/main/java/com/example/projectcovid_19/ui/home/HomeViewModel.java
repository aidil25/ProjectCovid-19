package com.example.projectcovid_19.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectcovid_19.model.ResponseProvinsi;
import com.example.projectcovid_19.network.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ResponseProvinsi> provinsi;

    public HomeViewModel() {
        provinsi = new MutableLiveData<>();
    }

    public void setProvinsi(String prov) {
        Client.getInstance().getCoronaProv(prov).enqueue(new Callback<ResponseProvinsi>() {
            @Override
            public void onResponse(Call<ResponseProvinsi> call, Response<ResponseProvinsi> response) {
                if(response.isSuccessful()){
                    provinsi.setValue(response.body());
                }else{

                }
            }

            @Override
            public void onFailure(Call<ResponseProvinsi> call, Throwable t) {

            }
        });
    }


}