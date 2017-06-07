package derkaoui.coms.facebook.myphotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessTokenTracker;
import com.vstechlab.easyfonts.EasyFonts;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import derkaoui.coms.facebook.myphotos.Album;

import static derkaoui.coms.facebook.myphotos.R.id.imageView;

public class LoginActivity extends AppCompatActivity {
    static ArrayList<Album>  Albums = new ArrayList<Album>();

    CallbackManager callbackManager = CallbackManager.Factory.create();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set components, GoToAlbums button invisible while user not logged in
        TextView tv = (TextView) findViewById(R.id.tv2);
        tv.setTypeface(EasyFonts.walkwayBold(this));
        final Button gotoalbums = (Button) findViewById(R.id.btt);
        gotoalbums.setVisibility(View.INVISIBLE);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos"));

        // Callback registration, includes album's processing
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker userTrack;
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Albums = new ArrayList<Album>();
                                try {
                                    JSONArray albums = object.getJSONObject("albums").getJSONArray("data");
                                    for (int i=0; i< albums.length();i++){
                                        //getting album names and id
                                        LoginActivity.Albums.add( new Album(albums.getJSONObject(i).getString("name"),albums.getJSONObject(i).getString("id")));
                                        if ( albums.getJSONObject(i).has("photos")){
                                            JSONArray photos = albums.getJSONObject(i).getJSONObject("photos").getJSONArray("data");
                                            for (int j=0;j<photos.length();j++){
                                                //getting album photos
                                                Albums.get(i).photosurl.add(j,photos.getJSONObject(j).getJSONArray("images").getJSONObject(0).getString("source"));
                                            }
                                        }
                                        else {
                                            //setting a default icon if the album is empty
                                            Albums.get(i).photosurl.add("https://static.xx.fbcdn.net/rsrc.php/v3/yO/r/7q6AXSKeuBG.png");
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                finally {
                                    //go to Albums activity
                                    final Intent gotoAlbumActivity = new Intent(LoginActivity.this,AlbumsActivity.class);
                                    gotoAlbumActivity.putParcelableArrayListExtra("albums",Albums);
                                    gotoalbums.setVisibility(View.VISIBLE);
                                    gotoalbums.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(gotoAlbumActivity);
                                        }
                                    });
                                    startActivity(gotoAlbumActivity);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "albums{name,photos{link,images}}");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Log.d("user loggin ","cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("user","err");
            }
        });

        //if the user is logged out, the GoToAlbums button is hidden and the Albums are deleted
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if (currentAccessToken == null){
                    Albums = new ArrayList<Album>();
                    gotoalbums.setVisibility(View.INVISIBLE);
                }
            }
        };

    }

    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

