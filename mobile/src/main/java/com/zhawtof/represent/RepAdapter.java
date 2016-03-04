package com.zhawtof.represent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhawtof on 3/2/16.
 */
public class RepAdapter extends BaseAdapter{
    //takes in parameters in constructor
    Context context;
    List<Representative> repList;

    public RepAdapter(Context c, List r){
        context = c;
        repList = r;
    }

    @Override
    public int getCount() {
        return repList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View repRow = inflater.inflate(R.layout.representative_list_item, parent, false);

        final Representative rep = repList.get(position);

        TextView repName = (TextView) repRow.findViewById(R.id.repName);
        repName.setText(rep.name);

        final String locationOfImages = "drawable/";

        ImageView repPic = (ImageView) repRow.findViewById(R.id.repPicture);
//        repPic.setImageResource(context.getResources().getIdentifier(
//                locationOfImages.concat(rep.pictureName), null, context.getPackageName()));

        TextView partyAffiliation = (TextView) repRow.findViewById(R.id.partyAffiliation);
        partyAffiliation.setText(rep.partyAffiliation);
        if (rep.partyAffiliation.equals("D")) {
            partyAffiliation.setBackgroundColor(context.getResources().getColor(R.color.democrat));
        } else{
            partyAffiliation.setBackgroundColor(context.getResources().getColor(R.color.republican));
        }

        Button moreInfo = (Button) repRow.findViewById(R.id.moreInfoButton);

        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(context.getApplicationContext(), RepDetails.class);
                next.putExtra("rep", rep);
                context.startActivity(next);
            }
        });

        return repRow;
    }
}
