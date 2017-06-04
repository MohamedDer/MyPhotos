package derkaoui.coms.facebook.myphotos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity {


    ArrayList<Album> MyAlbum;
    String[] names ;
    String[] image ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        //get Albums
        MyAlbum = getIntent().getParcelableArrayListExtra("albums");

        names = new String[MyAlbum.size()];
        image = new String[MyAlbum.size()];

        for (int i=0;i<MyAlbum.size();i++){
            names[i]=MyAlbum.get(i).name;
            image[i]=MyAlbum.get(i).photosurl.get(0);
        }


        TextView hiuser = (TextView) findViewById(R.id.hi_user);
        hiuser.setText(" Here are your albums ! ");
        hiuser.setTypeface(EasyFonts.walkwayBold(this));

        GridView grid = (GridView) findViewById(R.id.grid);
        CustomAdapter adapter = new CustomAdapter(this,names,image,MyAlbum);
        grid.setAdapter(adapter);

    }

    }

