package com.example.projectcovid_19.ui.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projectcovid_19.R;

public class InfoFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel notificationsViewModel;

    CardView cvCall;
    CardView cvHospital;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cvCall = view.findViewById(R.id.cv_phone);
        cvHospital = view.findViewById(R.id.cv_hospital);

        cvCall.setOnClickListener(this);
        cvHospital.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_phone:
                Intent panggil = new Intent(Intent. ACTION_DIAL);
                panggil.setData(Uri. fromParts("tel","199",null));
                startActivity(panggil);
                break;
            case R.id.cv_hospital:
                String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
                Uri gmmIntentUri;
                Intent mapIntent;
                String location_rumah_sakit = -0.943385+","+100.368504;

                gmmIntentUri = Uri.parse("google.navigation:q=" + location_rumah_sakit);

                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                mapIntent.setPackage(goolgeMap);
                startActivity(mapIntent);
                break;
        }
    }
}