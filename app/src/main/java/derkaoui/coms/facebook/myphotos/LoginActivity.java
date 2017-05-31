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
        //loginButton.setReadPermissions("user_photos");
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","user_photos"));

        // Callback registration, includes album's processing
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker userTrack;
            @Override
            public void onSuccess(LoginResult loginResult) {

                //get user albums
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                getAlbums();

                //Send albums to Album Activity
                final Intent gotoAlbumAct = new Intent(LoginActivity.this,AlbumsActivity.class);
                gotoAlbumAct.putParcelableArrayListExtra("Albums",LoginActivity.Albums);
                gotoalbums.setVisibility(View.VISIBLE);
                gotoalbums.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(gotoAlbumAct);
                    }
                });
                startActivity(gotoAlbumAct);

            }

            @Override
            public void onCancel() {
                Log.d("user","cancelled");
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


    //get current user's albums
    public void getAlbums(){
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me/albums", null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                try {
                    if (response.getError() == null) {
                        JSONObject respJSONObj = response.getJSONObject(); //convert GraphResponse response to JSONObject
                        if (respJSONObj.has("data")) {
                            JSONArray respJSONData = respJSONObj.optJSONArray("data"); //find JSONArray from JSONObject
                            for (int i = 0; i < respJSONData.length(); i++) {//find no. of album using jaData.length()
                                JSONObject AlbumJSON = respJSONData.getJSONObject(i); //convert perticular album into JSONObject
                                LoginActivity.Albums.add(new Album(AlbumJSON.getString("name"),AlbumJSON.getString("id")));
                                Log.d("Added album  name  ", LoginActivity.Albums.get(i).name);

                                /* Get photos of the i th album
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                getPhotos(i);  */
                            }
                        }
                    } else {
                        Log.d("ERROR response  ", response.getError().toString());
                    }
                } catch (JSONException e) {
                    Log.d("JSON ERROR", "");
                    e.printStackTrace();
                }
            }
        }
        ).executeAndWait();
    }

    //get current album's photos
    public void getPhotos(final int j){

        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");
        new GraphRequest(AccessToken.getCurrentAccessToken(),"/"+Albums.get(j).id +"/photos", parameters, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                try {
                    if (response.getError() == null) {
                        JSONObject respJSONObj = response.getJSONObject(); //convert GraphResponse response to JSONObject
                        JSONArray respJSONData = respJSONObj.optJSONArray("data"); //find JSONArray from JSONObject
                        for (int i = 0; i < respJSONData.length(); i++) {
                            String url = respJSONData.getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("source");
                            LoginActivity.Albums.get(j).photosurl.add(url);
                            Log.d("Added Photo"+LoginActivity.Albums.get(j).photosurl ,"   ");
                        }
                    }

                } catch (JSONException e) {
                    Log.d("JSON ERROR", "");
                    e.printStackTrace();
                }

            }  }).executeAndWait();
    }





}

