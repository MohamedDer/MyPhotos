package derkaoui.coms.facebook.myphotos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.R.attr.start;

/**
 * Created by Mohamed Derkaoui on 25/05/2017.
 */

public class CustomAdapter extends BaseAdapter {

        String [] names;
        Context context;
        String [] imageurl;
        private static LayoutInflater inflater=null;
        public CustomAdapter(AlbumsActivity mainActivity, String[] names, String[] imageurl) {
            // TODO Auto-generated constructor stub
            this.names=names;
            context=mainActivity;
            this.imageurl=imageurl;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder
        {
            TextView alb_name;
            ImageView alb_img;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.sample_gridlayout, null);
            holder.alb_name =(TextView) rowView.findViewById(R.id.albname);
            holder.alb_img =(ImageView) rowView.findViewById(R.id.albimage);

            holder.alb_name.setText(names[position]);
            Picasso.with(context).load(imageurl[position]).into(holder.alb_img);;

            rowView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "You selected "+names[position], Toast.LENGTH_SHORT).show();
                    Intent viewimages =  new Intent(context, ImagesActivity.class);
                    viewimages.putExtra("album",names[position]);
                    context.startActivity(viewimages);



                }
            });

            return rowView;
        }
}
