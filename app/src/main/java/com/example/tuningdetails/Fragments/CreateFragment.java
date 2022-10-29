package com.example.tuningdetails.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tuningdetails.Dialogs.LoadingDialog;
import com.example.tuningdetails.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class CreateFragment extends Fragment {

    ImageView img;
    Boolean imgSet = false;
    Uri imgUri;
    Button submitBtn;
    EditText DetailName, CompanyName, desc, year1;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageRef;
    LoadingDialog loadingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create, container, false);
        img = root.findViewById(R.id.imageView2);
        submitBtn = root.findViewById(R.id.button2);
        DetailName = root.findViewById(R.id.NameDetail);
        CompanyName = root.findViewById(R.id.NameCompany);
        year1 = root.findViewById(R.id.year);
        desc = root.findViewById(R.id.desc);
        storage = FirebaseStorage.getInstance();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        loadingDialog = new LoadingDialog(getActivity());

        img.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("tuningdetails/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "tuningdetails"), 1);
        });
        submitBtn.setOnClickListener(v -> {
            String NameDetail = DetailName.getText().toString();
            if(NameDetail.isEmpty()){
                DetailName.setError("Name needed");
                DetailName.requestFocus();
                return;
            }
            String NameCompany = CompanyName.getText().toString();
            if(NameCompany.isEmpty()){
                CompanyName.setError("Company Name needed");
                CompanyName.requestFocus();
                return;
            }
            String year = year1.getText().toString();
            if(year.isEmpty()){
                year1.setError("Year needed");
                year1.requestFocus();
                return;
            }
            String description = desc.getText().toString();
            if(description.isEmpty()){
                desc.setError("Please provide description");
                desc.requestFocus();
                return;
            }
            if(!imgSet){
                Toast.makeText(getContext(), "Please add image", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> docData = new HashMap<>();
            docData.put("DetailName", NameDetail);
            docData.put("CompanyName", NameCompany);
            docData.put("year1", year);
            docData.put("desc", description);
            uploadImage(docData);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) return;
        try{
            if(requestCode == 1){
                assert data != null;
                imgUri = data.getData();
                Transformation transformation = new RoundedCornersTransformation(30, 0);
                Picasso.get().load(imgUri).centerCrop().resize(360, 360).transform(transformation).into(img);
                imgSet = true;
            }
        } catch(Error e) {
            e.printStackTrace();
        }

    }

    private void uploadImage(Map<String, Object> docData){
        loadingDialog.startLoading();

        DocumentReference doc = db.collection("tuningdetails").document();
        String docId = doc.getId();
        StorageReference imageRef = storageRef.child("tuningdetails/"+docId);
        imageRef.putFile(imgUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        docData.put("image", uri.toString());
                        uploadDocument(docData, docId);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Upload failed! try again", Toast.LENGTH_SHORT).show();
                    loadingDialog.stopLoading(false);
                });

    }
    private void uploadDocument(Map<String, Object> docData, String docId){
        loadingDialog.startLoading();
        db.collection("list")
                .document(docId)
                .set(docData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Period added successfully", Toast.LENGTH_SHORT).show();
                    clearForm();
                    loadingDialog.stopLoading(false);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed! try again", Toast.LENGTH_SHORT).show();
                    loadingDialog.stopLoading(false);
                });
    }
    private void clearForm(){
        img.setImageResource(R.drawable.ic_lmovie_placeholder);
        imgSet = false;
        DetailName.getText().clear();
        CompanyName.getText().clear();
        year1.getText().clear();
        desc.getText().clear();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button BtnHome = view.findViewById(R.id.BtnHome);
        Button BtnSpisok = view.findViewById(R.id.BtnSpisok);
        Button BtnSettings = view.findViewById(R.id.BtnSettings);
        BtnHome.setOnClickListener(viewCreate -> {
            Bundle bundleHome = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_createFragment_to_mainFragment, bundleHome);
        });
        BtnSpisok.setOnClickListener(viewCreate -> {
            Bundle bundleSpisok = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_createFragment_to_spisokFragment, bundleSpisok);
        });
        BtnSettings.setOnClickListener(viewCreate -> {
            Bundle bundleSettings = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_createFragment_to_settingsFragment, bundleSettings);
        });
    }
}