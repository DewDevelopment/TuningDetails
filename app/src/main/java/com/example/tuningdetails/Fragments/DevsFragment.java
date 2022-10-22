package com.example.tuningdetails.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.example.tuningdetails.Model.GitWebClient;
import com.example.tuningdetails.R;

public class DevsFragment extends Fragment {

    WebView webView;
    WebView webView2;

    public DevsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devs, container, false);
        webView=view.findViewById(R.id.webview);
        webView.loadUrl("https://github.com/DewDeveloper");
        webView.setWebViewClient(new GitWebClient());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnHome = view.findViewById(R.id.BtnHome);
        Button BtnSpisok = view.findViewById(R.id.BtnSpisok);
        Button BtnSettings = view.findViewById(R.id.BtnSettings);
        BtnHome.setOnClickListener(viewCreate -> {
            Bundle bundleHome = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_devsFragment_to_mainFragment, bundleHome);
        });
        BtnSpisok.setOnClickListener(viewCreate -> {
            Bundle bundleSpisok = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_devsFragment_to_spisokFragment, bundleSpisok);
        });
        BtnSettings.setOnClickListener(viewCreate -> {
            Bundle bundleSettings = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_devsFragment_to_settingsFragment, bundleSettings);
        });
    }
}