package com.zhawtof.represent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
        ImageView repPic = (ImageView) repRow.findViewById(R.id.repPicture);
        TextView partyAffiliation = (TextView) repRow.findViewById(R.id.partyAffiliation);
        ImageView email = (ImageView) repRow.findViewById(R.id.emailIcon);
        ImageView website = (ImageView) repRow.findViewById(R.id.websiteIcon);

        repName.setText(rep.name);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_EMAIL, new String[]{rep.email});
                context.startActivity(Intent.createChooser(i, "Send mail..."));
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(rep.website));
                context.startActivity(i);
            }
        });


        final String locationOfImages = "drawable/";

//        repPic.setImageResource(context.getResources().getIdentifier(
//                locationOfImages.concat(rep.pictureName), null, context.getPackageName()));

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


                Intent next = new Intent(context, RepDetails.class);
                next.putExtra("rep", rep);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(next);
            }
        });

        return repRow;
    }
}
