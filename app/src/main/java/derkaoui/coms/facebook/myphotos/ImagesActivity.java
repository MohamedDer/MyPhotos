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
    String default_alb_cover = "https://static.xx.fbcdn.net/rsrc.php/v3/yO/r/7q6AXSKeuBG.png";
    String album_name;
    String emtpy_alb_toast = "                Empty Album :/ \n Go get a life and take some pics !! ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        photos_urls = getIntent().getStringArrayListExtra("photos_urls");
        album_name = getIntent().getStringExtra("album_name");


        TextView album_nameTextView = (TextView) findViewById(R.id.alb_name);
        album_nameTextView.setText(album_name + "  : ");
        album_nameTextView.setTypeface(EasyFonts.walkwayBold(this));

        if (photos_urls.get(0).equals(default_alb_cover)) {
            Toast.makeText(this, emtpy_alb_toast, Toast.LENGTH_SHORT).show();

        }
        else{
            GridView gr = (GridView) findViewById(R.id.grid2) ;
            ImagesAdapter im_adapter = new ImagesAdapter(this, photos_urls);
            gr.setAdapter(im_adapter);
        }


    }
}
