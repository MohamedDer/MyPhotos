package derkaoui.coms.facebook.myphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity {

    String[] urls;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        urls = getIntent().getStringArrayExtra("urls");
        name = getIntent().getStringExtra("name");


        TextView tvi = (TextView) findViewById(R.id.tvi);
        tvi.setText(name +"  : ");
        tvi.setTypeface(EasyFonts.walkwayBold(this));

        if (urls[0].equals("https://static.xx.fbcdn.net/rsrc.php/v3/yO/r/7q6AXSKeuBG.png")){
            Toast.makeText(this, " Empty album :/ Go get a life and take some pics !! ", Toast.LENGTH_SHORT).show();

        }
        else{
            GridView gr = (GridView) findViewById(R.id.grid2) ;
            CustomAdapter2 adapter = new CustomAdapter2(this, urls);
            gr.setAdapter(adapter);
        }


    }
}
