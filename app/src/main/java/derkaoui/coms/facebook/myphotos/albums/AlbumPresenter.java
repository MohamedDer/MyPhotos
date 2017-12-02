package derkaoui.coms.facebook.myphotos.albums;

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

import static derkaoui.coms.facebook.myphotos.LoginActivity.Albums;

/**
 * Created by MedDer on 02/12/2017.
 */

public class AlbumPresenter {

    public static void getAlbums(final Activity activity) {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Albums = new ArrayList<Album>();
                        try {
                            JSONArray albums = object.getJSONObject("albums").getJSONArray("data");
                            getAlbumsArray(albums, Albums, activity);

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
    public static void getAlbumsArray(JSONArray albums, ArrayList<Album> Albums, Activity activity) {
        for (int i = 0; i < albums.length(); i++) {
            //getting Albums names and id
            try {
                Albums.add(new Album(albums.getJSONObject(i).getString("name"), albums.getJSONObject(i).getString("id")));
                if (albums.getJSONObject(i).has("photos")) {
                    JSONArray photos = albums.getJSONObject(i).getJSONObject("photos").getJSONArray("data");
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
