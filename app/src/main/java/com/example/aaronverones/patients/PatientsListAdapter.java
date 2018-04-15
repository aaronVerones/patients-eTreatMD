package com.example.aaronverones.patients;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class PatientsListAdapter extends ArrayAdapter<JSONObject>{

    private int vg;
    private ArrayList<JSONObject> list;
    private Context context;

    public static final String EXTRA_ID = "com.example.aaronverones.patients.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.aaronverones.patients.EXTRA_NAME";

    public PatientsListAdapter(Context context, int vg, int id, ArrayList<JSONObject> list){
        super(context,vg, id,list);
        this.context = context;
        this.vg = vg;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(vg, parent, false);
        TextView pname = itemView.findViewById(R.id.pname);
        TextView pid = itemView.findViewById(R.id.pid);
        try {
            final String id = list.get(position).getString("id");
            final String name = list.get(position).getString("name");
            pid.setText(id);
            pname.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPatientProfile(id, name);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemView;
    }
    private void openPatientProfile(String id, String name) {
        Intent intent = new Intent(context, PatientProfileActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_NAME, name);
        context.startActivity(intent);
    }
}
