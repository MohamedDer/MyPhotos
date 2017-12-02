package derkaoui.coms.facebook.myphotos.images;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import derkaoui.coms.facebook.myphotos.R;

public class ImagesActivity extends AppCompatActivity {

    ArrayList<String> photos_urls;
    String album_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        photos_urls = getIntent().getStringArrayListExtra(getApplicationContext().getResources().getString(R.string.photos_urls));
        album_name = getIntent().getStringExtra(getApplicationContext().getResources().getString(R.string.album_name));

        //Display album's name
        TextView album_nameTextView = (TextView) findViewById(R.id.alb_name);
        album_nameTextView.setText(album_name + "  : ");
        album_nameTextView.setTypeface(EasyFonts.walkwayBold(this));


        if (photos_urls.get(0).equals(getApplicationContext().getResources().getString(R.string.default_album_cover))) {
            //Empty album
            Toast.makeText(this, getApplicationContext().getResources().getString(R.string.empty_album_toast), Toast.LENGTH_SHORT).show();

        } else {
            //Display album photos
            GridView gr = (GridView) findViewById(R.id.grid2);
            ImagesAdapter im_adapter = new ImagesAdapter(this, photos_urls);
            gr.setAdapter(im_adapter);
        }


    }
}
