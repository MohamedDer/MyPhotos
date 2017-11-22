package derkaoui.coms.facebook.myphotos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity {


    ArrayList<Album> myAlbums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        //get Albums
        myAlbums = getIntent().getParcelableArrayListExtra("Albums");

        TextView hiuser = (TextView) findViewById(R.id.hi_user);
        hiuser.setText(R.string.albums_head);
        hiuser.setTypeface(EasyFonts.walkwayBold(this));

        GridView grid = (GridView) findViewById(R.id.grid);
        AlbumsAdapter alb_adapter = new AlbumsAdapter(this, myAlbums);
        grid.setAdapter(alb_adapter);

    }

    }

