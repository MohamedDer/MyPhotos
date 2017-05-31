package derkaoui.coms.facebook.myphotos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Mohamed Derkaoui on 24/05/2017.
 */

public class Photo implements Parcelable {
    String urlphoto;
    String idphoto;

    public Photo (String url,String idp){
        urlphoto=url;
        idphoto = idp;

    }

    protected Photo(Parcel in) {
        urlphoto = in.readString();
        idphoto = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(urlphoto);
        dest.writeString(idphoto);
    }
}
