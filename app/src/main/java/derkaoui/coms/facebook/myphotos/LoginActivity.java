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
    static String User;

    CallbackManager callbackManager = CallbackManager.Factory.create();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tv = (TextView) findViewById(R.id.tv2);
        tv.setTypeface(EasyFonts.walkwayBold(this));

        final Button gotoalbums = (Button) findViewById(R.id.btt);
        gotoalbums.setVisibility(View.INVISIBLE);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions("user_photos");
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","user_photos"));
        // Callback registration

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker userTrack;
            @Override
            public void onSuccess(LoginResult loginResult) {

                //get current user
                 userTrack = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Log.d("facebook - profile  : ", currentProfile.getFirstName());userTrack.stopTracking();
                        LoginActivity.User = currentProfile.getFirstName()+"  ";
                    }};

                //get user albums and send them to the Albums Activity

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                getAlbums();

                Log.d("Albums size" ,""+LoginActivity.Albums.size());

                final Intent gotoAlbumAct = new Intent(LoginActivity.this,AlbumsActivity.class);
                gotoAlbumAct.putExtra("User",LoginActivity.User);
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

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    gotoalbums.setVisibility(View.INVISIBLE);
                }
            }
        };






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


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


                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                getPhotos(i);
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

    public void getPhotos(final int j){

        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");
        new GraphRequest(AccessToken.getCurrentAccessToken(),"/10208596975422212/photos", parameters, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                try {
                    if (response.getError() == null) {
                        JSONObject respJSONObj = response.getJSONObject(); //convert GraphResponse response to JSONObject
                        JSONArray respJSONData = respJSONObj.optJSONArray("data"); //find JSONArray from JSONObject
                        for (int i = 0; i < respJSONData.length(); i++) {
                            String url = respJSONData.getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("source");
                            LoginActivity.Albums.get(j).photosurl.add(url);
                            Log.d("Added Photo"+LoginActivity.Albums.get(j).photosurl ,"   Album name"+LoginActivity.Albums.get(j).name);
                        }
                    }

                } catch (JSONException e) {
                    Log.d("JSON ERROR", "");
                    e.printStackTrace();
                }

            }  }).executeAndWait();

    }



}

