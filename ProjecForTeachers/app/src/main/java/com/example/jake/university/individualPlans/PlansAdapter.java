package com.example.jake.university.individualPlans;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.jake.university.API.postReq;
import com.example.jake.university.R;
import com.example.jake.university.profile.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PlansAdapter extends ArrayAdapter<Plan> {

    private ArrayList<Plan> plansItems;
    private int layout;
    private LayoutInflater inflater;
    public PlansAdapter(Context context, int resource, ArrayList<Plan> plans) {
        super(context, resource, plans);
        this.plansItems = plans;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(this.layout,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();
        final Plan item = plansItems.get(position);
        viewHolder.name.setText(item.getName());
        viewHolder.dateCafCoop.setText(item.getCafConsDate());
        viewHolder.dateAssig.setText(item.getConfirmDate());
        viewHolder.stavka.setText(item.getCharge());
        viewHolder.status.setText(item.getStatus());
        viewHolder.institute.setText(item.getInstitute());
        viewHolder.akp.setText(item.getEKP());
        viewHolder.typeDol.setText(item.getPostType());
        viewHolder.cafedra.setText(item.getCafedra());
        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String ppp = item.getID();
                String sss = item.getStatus();
                int i =5;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://hqvla2222w02/api/api/DownloadPrepPlan?IdParus=281474976716985&IdPlan="
                        +item.getID()+"&Status="+item.getStatus()+"/DownloadPrepPlan.pdf"));
//                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lk.www1.vlsu.ru/Home/DownloadPrepPlan?IdParus=281474976716985&IdPlan="+item.getID()+"&Status="+item.getStatus()));
                getContext().startActivity(browserIntent);
            }
        });
        viewHolder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!item.isVisibleFlag()){
                    viewHolder.moreInfoCV.setVisibility(View.VISIBLE);
                    item.setVisibleFlag(true);
                }
                else {
                    viewHolder.moreInfoCV.setVisibility(View.GONE);
                    item.setVisibleFlag(false);
                }
            }
        });
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Добавить удаление плана
            }
        });
        viewHolder.viewBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Добавить просмотр плана
            }
        });
        viewHolder.assig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Добавить подписание плана
            }
        });
        return convertView;
    }
    private class ViewHolder {
        final TextView institute, cafedra, dateCafCoop, stavka, akp, status, dateAssig, typeDol, name;
        final Button download, moreInfo, del, assig, viewBut;
        final CardView moreInfoCV;
        ViewHolder(View view){
            institute = (TextView) view.findViewById(R.id.planInstitute2);
            moreInfoCV = (CardView) view.findViewById(R.id.innerCV);
            name = (TextView) view.findViewById(R.id.planName);
            dateCafCoop = (TextView) view.findViewById(R.id.planDateKafCoop2);
            stavka = (TextView) view.findViewById(R.id.planStavka3);
            akp = (TextView) view.findViewById(R.id.planAKP2);
            status = (TextView) view.findViewById(R.id.planStatus2);
            dateAssig = (TextView) view.findViewById(R.id.platDateAssigned2);
            typeDol = (TextView) view.findViewById(R.id.planType3);
            cafedra = (TextView) view.findViewById(R.id.planKafedra2);
            download = (Button) view.findViewById(R.id.buttonDownloadPlan);
            moreInfo = (Button) view.findViewById(R.id.buttonMoreInfo);
            del = (Button) view.findViewById(R.id.buttonDelPlan);
            assig = (Button) view.findViewById(R.id.buttonAssignPlan);
            viewBut = (Button) view.findViewById(R.id.buttonViewPlan);
        }
    }
}
