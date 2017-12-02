package derkaoui.coms.facebook.myphotos.albums;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import derkaoui.coms.facebook.myphotos.R;
import derkaoui.coms.facebook.myphotos.images.ImagesActivity;


public class AlbumsAdapter extends BaseAdapter {

    private static final int DEFAULT_IMAGE_WIDTH = 410;
    private static final int DEFAULT_IMAGE_HEIGHT = 450;
    private LayoutInflater inflater = null;
    private String[] names;
    private Context context;
    private String[] defaultImageUrl;
    private ArrayList<Album> Albums;


    AlbumsAdapter(AlbumsActivity mainActivity, ArrayList<Album> myAlbums) {

        this.context = mainActivity.getApplicationContext();
        this.Albums = myAlbums;

        this.names = new String[myAlbums.size()];
        this.defaultImageUrl = new String[myAlbums.size()];

        for (int i = 0; i < myAlbums.size(); i++) {
            names[i] = myAlbums.get(i).getAlbumName();
            defaultImageUrl[i] = myAlbums.get(i).getPhotosUrls().get(0);
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return names == null ? 0 : names.length;
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
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout, null);
        holder.alb_name = (TextView) rowView.findViewById(R.id.albname);
        holder.alb_img = (ImageView) rowView.findViewById(R.id.albimage);

        //Setting album covers
        holder.alb_name.setText(names[position]);
        Picasso.with(context).load(defaultImageUrl[position]).resize(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT).into(holder.alb_img);
        // Starting ImageActivity to show the photos of the selected album
        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You selected " + names[position], Toast.LENGTH_SHORT).show();
                Intent viewimages = new Intent(context, ImagesActivity.class);
                viewimages.putExtra("album_name", Albums.get(position).getAlbumName());
                viewimages.putExtra("photos_urls", Albums.get(position).getPhotosUrls());
                context.startActivity(viewimages);

            }
        });

        return rowView;
    }

    public class Holder {
        TextView alb_name;
        ImageView alb_img;
    }
}
