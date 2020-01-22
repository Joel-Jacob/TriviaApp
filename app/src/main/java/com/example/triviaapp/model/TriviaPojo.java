
package com.example.triviaapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TriviaPojo implements Parcelable {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    protected TriviaPojo(Parcel in) {
        if (in.readByte() == 0) {
            responseCode = null;
        } else {
            responseCode = in.readInt();
        }
        results = in.createTypedArrayList(Result.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (responseCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(responseCode);
        }
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TriviaPojo> CREATOR = new Creator<TriviaPojo>() {
        @Override
        public TriviaPojo createFromParcel(Parcel in) {
            return new TriviaPojo(in);
        }

        @Override
        public TriviaPojo[] newArray(int size) {
            return new TriviaPojo[size];
        }
    };

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
