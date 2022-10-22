package com.example.tuningdetails.Fragments;


import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tuningdetails.R;


public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button BtnHome = view.findViewById(R.id.BtnHome);
        Button BtnSpisok = view.findViewById(R.id.BtnSpisok);
        Button BtnSettings = view.findViewById(R.id.BtnSettings);
        Button BtnProfile = view.findViewById(R.id.BtnProfile);
        Button BtnDevs = view.findViewById(R.id.BtnDevs);
        BtnHome.setOnClickListener(viewCreate -> {
            Bundle bundleHome = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_mainFragment, bundleHome);
        });
        BtnSpisok.setOnClickListener(viewCreate -> {
            Bundle bundleSpisok = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_spisokFragment, bundleSpisok);
        });
        BtnSettings.setOnClickListener(viewCreate -> {
            Bundle bundleSettings = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_self, bundleSettings);
        });
        BtnProfile.setOnClickListener(viewCreate -> {
            Bundle bundleProfile = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_profileFragment, bundleProfile);
        });
        BtnDevs.setOnClickListener(viewCreate -> {
            Bundle bundleDevs = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_devsFragment, bundleDevs);
        });
    }
}