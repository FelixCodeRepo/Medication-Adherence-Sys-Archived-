package com.example.easylearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class DateAddInfo extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "DateAddInfo";

    private EditText year, month, date;
    private Button createBtn, cancelBtn;
    private String name, price, dosage;

    public DateAddInfo(String name, String price, String dosage) {
        this.name = name;
        this.price = price;
        this.dosage = dosage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_add_info, container, false);
        year = view.findViewById(R.id.YearEditText);
        month = view.findViewById(R.id.MonthEditText);
        date = view.findViewById(R.id.DateEditText);

        createBtn = view.findViewById(R.id.createButton);
        createBtn.setOnClickListener(this);
        cancelBtn = view.findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(this);

        getDialog().setTitle("Add Date for Medication");

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.createButton) {
            String UserYear = year.getText().toString();
            String UserMonth = month.getText().toString();
            String UserDate = date.getText().toString();

            if(!UserYear.equals("") && !UserMonth.equals("") && !UserDate.equals("")){
                //Send created Date with Medicine Info to FireStore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference addMedInfoToCloud = db.collection("TestMed").document();

                MedicationInfo mInfo = new MedicationInfo(name, price, dosage);
                mInfo.setDate(UserDate);
                mInfo.setMonth(UserMonth);
                mInfo.setYear(UserYear);

                addMedInfoToCloud.set(mInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            getDialog().dismiss();
                        }
                    }
                });

            }
            else {
                Toast.makeText(getActivity(), "Not enough information",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == R.id.cancelButton) {
            getDialog().dismiss();
        }
    }
}
