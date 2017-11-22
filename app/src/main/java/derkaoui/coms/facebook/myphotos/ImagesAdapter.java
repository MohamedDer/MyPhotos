package derkaoui.coms.facebook.myphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mohamed Derkaoui on 25/05/2017.
 */

public class ImagesAdapter extends BaseAdapter {


    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<String> imagesUrls;

    public ImagesAdapter(ImagesActivity mainActivity, ArrayList<String> imagesurls) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.imagesUrls = imagesurls;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagesUrls.size();
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.image_item, null);
        holder.chk_img =(CheckBox) rowView.findViewById(R.id.chekcboxshw);
        holder.alb_img =(ImageView) rowView.findViewById(R.id.imageshw);

        Picasso.with(context).load(imagesUrls.get(position)).resize(410, 450).into(holder.alb_img);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (holder.chk_img.isChecked()){
                    Toast.makeText(context, "Image unselected ! ", Toast.LENGTH_SHORT).show();
                    holder.chk_img.setChecked(false);
                }

                else{
                    Toast.makeText(context, "Image selected ! ", Toast.LENGTH_SHORT).show();
                    holder.chk_img.setChecked(true);
                }
            }
        });

        return rowView;
    }

    public class Holder {

        ImageView alb_img;
        CheckBox chk_img;
    }
}
