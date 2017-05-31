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

    GridView gridview;

    public static String[] AlbumName ;
    public static String[] AlbumImage ;
    ArrayList<Album> MyAlbum;
    String[] names ;
    String[] images ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        MyAlbum = getIntent().getParcelableArrayListExtra("Albums");
        String user = getIntent().getStringExtra("User");

        names = new String[MyAlbum.size()];
        images = new String[MyAlbum.size()];

        for (int i=0;i<MyAlbum.size();i++){
            names[i]=MyAlbum.get(i).name;
            images[i]="http://static.ccm2.net/www.commentcamarche.net/_skin/_local/img/logo.png?201402061805 ";

        }

        TextView hiuser = (TextView) findViewById(R.id.hi_user);
        hiuser.setText(" Here are your albums "+user);
        hiuser.setTypeface(EasyFonts.walkwayBold(this));

        GridView grid = (GridView) findViewById(R.id.grid);
        CustomAdapter adapter = new CustomAdapter(this,names,images);
        grid.setAdapter(adapter);



        }



    }

