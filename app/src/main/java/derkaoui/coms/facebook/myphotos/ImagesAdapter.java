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


public class ImagesAdapter extends BaseAdapter {

    private final int DEFAULT_IMAGE_WIDTH = 410;
    private final int DEFAULT_IMAGE_HEIGHT = 450;
    LayoutInflater inflater = null;
    Context context;
    private ArrayList<String> imagesUrls;

    public ImagesAdapter(ImagesActivity mainActivity, ArrayList<String> imagesurls) {
        context=mainActivity;
        this.imagesUrls = imagesurls;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return imagesUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.image_item, null);
        holder.chk_img =(CheckBox) rowView.findViewById(R.id.chekcboxshw);
        holder.alb_img =(ImageView) rowView.findViewById(R.id.imageshw);

        Picasso.with(context).load(imagesUrls.get(position)).resize(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT).into(holder.alb_img);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

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
