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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.Arrays;

import derkaoui.coms.facebook.myphotos.albums.Album;
import derkaoui.coms.facebook.myphotos.albums.AlbumPresenter;
import derkaoui.coms.facebook.myphotos.albums.AlbumsActivity;

public class LoginActivity extends AppCompatActivity {

    public static ArrayList<Album> Albums = new ArrayList<>();
    CallbackManager callbackManager = CallbackManager.Factory.create();

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

            @Override
            public void onSuccess(LoginResult loginResult) {
                updateButtonVisibility(gotoalbumsButton);
                //Getting user's albums
                AlbumPresenter.getAlbums(LoginActivity.this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}

