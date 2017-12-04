package derkaoui.coms.facebook.myphotos.login;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import derkaoui.coms.facebook.myphotos.R;
import derkaoui.coms.facebook.myphotos.albums.Album;


/**
 * Created by MedDer on 02/12/2017.
 */

public class LoginPresenter {


    void getAlbums(final Activity activity) {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        ArrayList<Album> Albums = new ArrayList<>();
                        try {
                            JSONArray albumsJSArray = object.getJSONObject("albums").getJSONArray("data");
                            getAlbumsArray(albumsJSArray, Albums, activity);

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
    private void getAlbumsArray(JSONArray albumsJSArray, ArrayList<Album> Albums, Activity activity) {
        for (int i = 0; i < albumsJSArray.length(); i++) {
            //getting Albums names and id
            try {
                Albums.add(new Album(albumsJSArray.getJSONObject(i).getString("name"), albumsJSArray.getJSONObject(i).getString("id")));
                if (albumsJSArray.getJSONObject(i).has("photos")) {
                    JSONArray photos = albumsJSArray.getJSONObject(i).getJSONObject("photos").getJSONArray("data");
                    for (int j = 0; j < photos.length(); j++) {
                        //getting Album photos
                        Albums.get(i).getPhotosUrls().add(j, photos.getJSONObject(j).getJSONArray("images").getJSONObject(0).getString("source"));
                    }
                } else {
                    //setting a default icon if the Album is empty
                    Albums.get(i).getPhotosUrls().add(activity.getApplicationContext().getResources().getString(R.string.default_album_cover));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
