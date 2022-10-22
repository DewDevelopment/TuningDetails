package com.example.tuningdetails.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuningdetails.Model.SpisokModel;
import com.example.tuningdetails.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class SpisokFragment extends Fragment {

    private FirestoreRecyclerAdapter adapter;
    CardView cardView;

    public SpisokFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spisok, container, false);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        RecyclerView mFirestore_list = view.findViewById(R.id.recycler1);
        Query query = firebaseFirestore.collection("list");

        FirestoreRecyclerOptions<SpisokModel> options = new FirestoreRecyclerOptions.Builder<SpisokModel>()
                .setQuery(query,SpisokModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<SpisokModel, SpisokViewHolder>(options) {

            @NonNull
            @Override
            public SpisokViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new SpisokViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SpisokViewHolder holder, int position, @NonNull SpisokModel model) {
                holder.DetailName.setText(model.getDetailName());
                holder.CompanyName.setText(model.getCompanyName());
                holder.year1.setText(model.getYear1());
                holder.desc.setText(model.getdesc());
            }
        };
        mFirestore_list.setHasFixedSize(true);
        mFirestore_list.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirestore_list.setAdapter(adapter);
        return view;
    }

    private static class SpisokViewHolder extends RecyclerView.ViewHolder{

        private final TextView DetailName , CompanyName , year1 , desc;

        public SpisokViewHolder(@NonNull View itemView) {
            super(itemView);
            DetailName = itemView.findViewById(R.id.NameDetail);
            CompanyName = itemView.findViewById(R.id.CompanyName);
            year1 = itemView.findViewById(R.id.year1);
            desc = itemView.findViewById(R.id.desc);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnCreate = view.findViewById(R.id.BtnCreate);
        Button BtnHome = view.findViewById(R.id.BtnHome);
        Button BtnSpisok = view.findViewById(R.id.BtnSpisok);
        Button BtnSettings = view.findViewById(R.id.BtnSettings);
        BtnCreate.setOnClickListener(viewCreate -> {
            Bundle bundleCreate = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_spisokFragment_to_createFragment, bundleCreate);
        });
        BtnHome.setOnClickListener(viewCreate -> {
            Bundle bundleHome = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_spisokFragment_to_mainFragment, bundleHome);
        });
        BtnSpisok.setOnClickListener(viewCreate -> {
            Bundle bundleSpisok = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_spisokFragment_self, bundleSpisok);
        });
        BtnSettings.setOnClickListener(viewCreate -> {
            Bundle bundleSettings = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_spisokFragment_to_settingsFragment, bundleSettings);
        });
    }
}
