package com.example.projectcovid_19.network;

import com.example.projectcovid_19.model.ResponseKabupaten;
import com.example.projectcovid_19.model.ResponseProvinsi;
import com.example.projectcovid_19.model.kabupaten.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Service {

    @GET("getCoronaLast/{provinsi}")
    Call<ResponseProvinsi> getCoronaProv(@Path("provinsi") String provinsi);

    @GET("getDayEachKab")
    Call<ResponseKabupaten> getMapsCov();

    @Headers("Accept-Encoding: identity")
    @GET("getDayKab/{kab}")
    Call<Response> getKab(@Path("kab") String kab);


}
