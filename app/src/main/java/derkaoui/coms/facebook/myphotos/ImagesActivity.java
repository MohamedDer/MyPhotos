package derkaoui.coms.facebook.myphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

public class ImagesActivity extends AppCompatActivity {
    String[] images = {"http://static.ccm2.net/www.commentcamarche.net/_skin/_local/img/logo.png?201402061805 ","http://static.ccm2.net/www.commentcamarche.net/_skin/_local/img/logo.png?201402061805 ","http://static.ccm2.net/www.commentcamarche.net/_skin/_local/img/logo.png?201402061805 ","http://static.ccm2.net/www.commentcamarche.net/_skin/_local/img/logo.png?201402061805 ","http://static.ccm2.net/www.commentcamarche.net/_skin/_local/img/logo.png?201402061805 ",  };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        String albumn = getIntent().getStringExtra("album");

        TextView tvi = (TextView) findViewById(R.id.tvi);
        tvi.setText(albumn +"  : ");
        tvi.setTypeface(EasyFonts.walkwayBold(this));

        GridView gr = (GridView) findViewById(R.id.grid2) ;
        CustomAdapter2 adapter = new CustomAdapter2(this, images);
        gr.setAdapter(adapter);

    }
}
