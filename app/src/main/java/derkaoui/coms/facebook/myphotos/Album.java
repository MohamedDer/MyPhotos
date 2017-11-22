package derkaoui.coms.facebook.myphotos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Mohamed Derkaoui on 23/05/2017.
 */

public class Album implements Parcelable {
    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
    String name_Alb;
    String id_Alb;
    ArrayList<String> photosUrl;

    public Album(String name, String id) {
        this.name_Alb = name;
        this.id_Alb = id;
        this.photosUrl = new ArrayList<String>();
    }

    protected Album(Parcel in) {
        name_Alb = in.readString();
        id_Alb = in.readString();
        photosUrl = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name_Alb);
        dest.writeString(id_Alb);
        dest.writeStringList(photosUrl);
    }
}
