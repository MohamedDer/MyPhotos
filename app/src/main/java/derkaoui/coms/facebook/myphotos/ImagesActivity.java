package derkaoui.coms.facebook.myphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity {

    ArrayList<Album> albums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        albums = getIntent().getParcelableArrayListExtra("albums");
        int pos = getIntent().getIntExtra("position",0);
        String albumname = albums.get(pos).name;
        Log.d("hoooo "+ albums.get(pos).photosurl.size()  ,"");


        TextView tvi = (TextView) findViewById(R.id.tvi);
        tvi.setText(albumname +"  : ");
        tvi.setTypeface(EasyFonts.walkwayBold(this));

        GridView gr = (GridView) findViewById(R.id.grid2) ;
        CustomAdapter2 adapter = new CustomAdapter2(this, albums.get(pos).photosurl);
        gr.setAdapter(adapter);

    }
}
