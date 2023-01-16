package com.example.easylearn;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class MedicationList_Adapter extends ArrayAdapter<MedicationInfo> implements Filterable,
        View.OnClickListener
{
    private ArrayList<MedicationInfo> medList;
    private ArrayList<MedicationInfo> medListFull;
    private Context mContext;
    int mResource;
    //3.11
    private TypedArray medPic;

    //3.11
    public MedicationList_Adapter(@NonNull Context context, int resource,
                                  @NonNull ArrayList<MedicationInfo> objects,
                                  @NonNull TypedArray typedArray) {
        super(context, resource, objects);
        this.medList = objects;
        mContext = context;
        mResource = resource;
        medListFull = new ArrayList<>(objects);
        //3.11
        medPic = typedArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nameMED = getItem(position).getMedname();
        String priceINFO = getItem(position).getPrice();
        String dosageINFO = getItem(position).getMethods();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //3.11
        ImageView imageV = convertView.findViewById(R.id.MedPicView);
        imageV.setImageDrawable(medPic.getDrawable(position));

        TextView tvMedName = convertView.findViewById(R.id.textViewID1);
        TextView tvPrice = convertView.findViewById(R.id.textViewID2);
        TextView tvDosage = convertView.findViewById(R.id.textViewID3);
        Button addButton = convertView.findViewById(R.id.addBtn);

        tvMedName.setText(nameMED);
        tvPrice.setText(priceINFO);
        tvDosage.setText(dosageINFO);
        addButton.setTag(position);

        addButton.setOnClickListener(this);

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return MedFilter;
    }

    private Filter MedFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<MedicationInfo> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(medListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(MedicationInfo item : medListFull) {
                    if(item.getMedname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            medList.clear();
            medList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

    };

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        String nameMed = getItem(position).getMedname();
        String priceInfo = getItem(position).getPrice();
        String dosageInfo = getItem(position).getMethods();
        DateAddInfo userDateInfo = new DateAddInfo(nameMed, priceInfo, dosageInfo);
        userDateInfo.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "AddDateInfo");
    }
}
