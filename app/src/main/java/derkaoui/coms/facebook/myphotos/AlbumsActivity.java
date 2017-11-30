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

        TextView hiuser = (TextView) findViewById(R.id.albums_head);
        hiuser.setTypeface(EasyFonts.walkwayBold(this));
        GridView grid = (GridView) findViewById(R.id.grid);

        //getting Albums and passing them to the  AlbumAdapter
        userAlbums = getIntent().getParcelableArrayListExtra("Albums");
        AlbumsAdapter albumAdapter = new AlbumsAdapter(this, userAlbums);
        grid.setAdapter(albumAdapter);

    }

}

