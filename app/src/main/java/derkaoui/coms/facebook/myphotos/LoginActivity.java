package derkaoui.coms.facebook.myphotos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    static ArrayList<Album> Albums = new ArrayList<>();
    CallbackManager callbackManager = CallbackManager.Factory.create();
    String DEFAULT_ALBUM_COVER = "https://static.xx.fbcdn.net/rsrc.php/v3/yO/r/7q6AXSKeuBG.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // setting components, GoToAlbums button invisible while user not logged in
        TextView appname = (TextView) findViewById(R.id.appnameTextView);
        appname.setTypeface(EasyFonts.walkwayBold(this));

        final Button gotoalbumsButton = (Button) findViewById(R.id.gotoalbumsButton);
        gotoalbumsButton.setVisibility(View.INVISIBLE);
        gotoalbumsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAlbumActivity = new Intent(LoginActivity.this, AlbumsActivity.class);
                gotoAlbumActivity.putParcelableArrayListExtra("Albums", Albums);
                startActivity(gotoAlbumActivity);
            }
        });
        //Facebook login button
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        // If the user disconnected, updating UI
        final AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Albums = new ArrayList<Album>();
                    updateButtonVisibility(gotoalbumsButton);
                }
            }
        };
        accessTokenTracker.isTracking();

        // Logging button callback registration
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker userTrack;
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateButtonVisibility(gotoalbumsButton);
                //Getting user's albums
                getAlbums();
            }

            @Override
            public void onCancel() {
                Log.d("user loggin ", "cancelled");
                Toast.makeText(getApplicationContext(), "Loggin cancelled", Toast.LENGTH_SHORT).show();
                if (!accessTokenTracker.equals(null))
                    gotoalbumsButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("user", "err");
            }
        });

    }

    void updateButtonVisibility(Button button) {
        if (button.getVisibility() == View.VISIBLE)
            button.setVisibility(View.INVISIBLE);
        else
            button.setVisibility(View.VISIBLE);
    }

    void getAlbums() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Albums = new ArrayList<Album>();
                        try {
                            JSONArray albums = object.getJSONObject("albums").getJSONArray("data");
                            getAlbumsArray(albums, Albums);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{name,photos{link,images}}");
        request.setParameters(parameters);
        request.executeAsync();
    }

    // Extract albums' data from JSON Arrays to Album ArrayList
    void getAlbumsArray(JSONArray albums, ArrayList<Album> Albums) {
        for (int i = 0; i < albums.length(); i++) {
            //getting Albums names and id
            try {
                LoginActivity.Albums.add(new Album(albums.getJSONObject(i).getString("name"), albums.getJSONObject(i).getString("id")));
                if (albums.getJSONObject(i).has("photos")) {
                    JSONArray photos = albums.getJSONObject(i).getJSONObject("photos").getJSONArray("data");
                    for (int j = 0; j < photos.length(); j++) {
                        //getting Album photos
                        Albums.get(i).getPhotosUrls().add(j, photos.getJSONObject(j).getJSONArray("images").getJSONObject(0).getString("source"));
                    }
                } else {
                    //setting a default icon if the Album is empty
                    Albums.get(i).getPhotosUrls().add(DEFAULT_ALBUM_COVER);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

