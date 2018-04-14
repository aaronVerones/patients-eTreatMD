package com.example.aaronverones.patients;

import android.content.Context;
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

    public PatientsListAdapter(Context context, int vg, int id, ArrayList<JSONObject> list){
        super(context,vg, id,list);
        this.context = context;
        this.vg = vg;
        this.list = list;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(vg, parent, false);
        TextView pname = (TextView)itemView.findViewById(R.id.pname);
        TextView pid = (TextView)itemView.findViewById(R.id.pid);
        try {
            pid.setText(list.get(position).getString("id"));
            pname.setText(list.get(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemView;
    }
}
