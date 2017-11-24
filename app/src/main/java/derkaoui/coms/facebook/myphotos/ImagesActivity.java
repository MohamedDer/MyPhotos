package derkaoui.coms.facebook.myphotos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity {

    ArrayList<String> photos_urls;
    String DEFAULT_ALBUM_COVER = "https://static.xx.fbcdn.net/rsrc.php/v3/yO/r/7q6AXSKeuBG.png";
    String EMPTY_ALBUM_TOAST = "                Empty Album :/ \n Go get a life and take some pics !! ";
    String PHOTOS_URLS = "photos_urls";
    String ALBUM_NAME = "album_name";
    String album_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        photos_urls = getIntent().getStringArrayListExtra(PHOTOS_URLS);
        album_name = getIntent().getStringExtra(ALBUM_NAME);


        TextView album_nameTextView = (TextView) findViewById(R.id.alb_name);
        album_nameTextView.setText(album_name + "  : ");
        album_nameTextView.setTypeface(EasyFonts.walkwayBold(this));


        if (photos_urls.get(0).equals(DEFAULT_ALBUM_COVER)) {
            //Empty album
            Toast.makeText(this, EMPTY_ALBUM_TOAST, Toast.LENGTH_SHORT).show();

        }
        else{
            //Passing the photos' urls the the image adapter if the album isn't emplty
            GridView gr = (GridView) findViewById(R.id.grid2) ;
            ImagesAdapter im_adapter = new ImagesAdapter(this, photos_urls);
            gr.setAdapter(im_adapter);
        }


    }
}
