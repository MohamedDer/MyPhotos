package derkaoui.coms.facebook.myphotos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity {


    ArrayList<Album> userAlbums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        //getting Albums
        userAlbums = getIntent().getParcelableArrayListExtra("Albums");

        TextView hiuser = (TextView) findViewById(R.id.albums_head);
        hiuser.setTypeface(EasyFonts.walkwayBold(this));
        GridView grid = (GridView) findViewById(R.id.grid);
        AlbumsAdapter albumAdapter = new AlbumsAdapter(this, userAlbums);
        grid.setAdapter(albumAdapter);

    }

    }

