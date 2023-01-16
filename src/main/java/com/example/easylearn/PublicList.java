package com.example.easylearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class PublicList extends AppCompatActivity {

    private static final String TAG = "PublicList";
    private static final String MEDCINE_TAG = "Medicine";
    private static final String PRICE_TAG = "Price";
    private static final String METHOD_TAG = "Method";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference MedicineInfoFireStore = db.collection("AllUSER");

    private MedicationList_Adapter adapter;
    private ArrayList<MedicationInfo> medInfo = new ArrayList<>();
    private ListView MedList;
    private SearchView theFilter;

    //3.11
    private TypedArray Medicine_Picture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_list);
        MedList = findViewById(R.id.MedListView);
        MedList.setTextFilterEnabled(true);
        theFilter = findViewById(R.id.SearchBarView);

        //3.11
        Context context = getApplicationContext();
        Resources resources = context.getResources();
        Medicine_Picture = resources.obtainTypedArray(R.array.Medicine_Picture);

        //Log.d(TAG, "on Create: started");
        //Medicine   Price   Method
//        MedicationInfo tylenol = new MedicationInfo("Tylenol",
//                "13.99$","Once a day, 2 pills each time");
//        MedicationInfo advil = new MedicationInfo("Advil",
//                "9.99$","Once a day, 1 pill each time");
//        MedicationInfo motrin = new MedicationInfo("Motrin",
//                "17.50$","Twice a day, 2 pills each time");
//        MedicationInfo be_koool = new MedicationInfo("Be koool",
//                "7.99$","Twice a day, 3 pills each time");
//        MedicationInfo imodium = new MedicationInfo("Imodium",
//                "6.50$","Twice a day, 1 pills each time");
//        MedicationInfo pepto_Bismol = new MedicationInfo("Pepto-Bismol",
//                "8.99$","Three times a day, 2 pills each time");
//        MedicationInfo culturelle = new MedicationInfo("Culturelle",
//                "15.50$","Every 6 hours, 2 pills each time");
//        MedicationInfo benefiber = new MedicationInfo("Benefiber",
//                "13.99$","Every 12 hours, 1 pills each time");
//        MedicationInfo dulcolax = new MedicationInfo("Dulcolax",
//                "12.50$","Before bedtime everyday");
//        MedicationInfo senokot = new MedicationInfo("Senokot",
//                "10.50$",
//                "Before breakfast and 30 minutes before bedtime, 1 pills each time");
//        MedicationInfo tums = new MedicationInfo("Tums",
//                "4.50$", "After each meals every day, 2 pills each time");
//        MedicationInfo zantac = new MedicationInfo("Zantac",
//                "6.99$", "2 or 3 times a day, 1 pills each time");


//        medInfo.add(tylenol);
//        medInfo.add(advil);
//        medInfo.add(motrin);
//        medInfo.add(be_koool);
//        medInfo.add(imodium);
//        medInfo.add(pepto_Bismol);
//        medInfo.add(culturelle);
//        medInfo.add(benefiber);
//        medInfo.add(dulcolax);
//        medInfo.add(senokot);
//        medInfo.add(tums);
//        medInfo.add(zantac);

        //3.11
        Query MedQuery = MedicineInfoFireStore.orderBy("Medicine", Query.Direction.ASCENDING);
        MedQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String medicine = documentSnapshot.getString(MEDCINE_TAG);
                        String price = documentSnapshot.getString(PRICE_TAG);
                        String method = documentSnapshot.getString(METHOD_TAG);
                        MedicationInfo med_type = new MedicationInfo(medicine, price, method);
                        medInfo.add(med_type);
                }

                //3.11
                adapter = new MedicationList_Adapter(PublicList.this,
                        R.layout.adapter_view_layout, medInfo, Medicine_Picture);
                MedList.setAdapter(adapter);

                theFilter.setImeOptions(EditorInfo.IME_ACTION_DONE);

                theFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }

                });
            }
        });

    }
}
