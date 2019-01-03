package com.ciarasouthgate.studybreak;
import android.os.Parcel;
import android.os.Parcelable;

public class Interruption implements Parcelable {
    /** The name of interruption. */
    private String name;
    /** The interval between interruptions. */
    private long interval;
    /** The duration of interruption. */
    private long duration;

    public Interruption(String name, long interval, long duration) {
        this.name = name;
        this.interval = interval;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    public long getInterval() {
        return interval;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(interval);
        dest.writeLong(duration);
    }

    private Interruption(Parcel in) {
        name = in.readString();
        interval = in.readLong();
        duration = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Interruption> CREATOR
            = new Parcelable.Creator<Interruption>() {

        @Override
        public Interruption createFromParcel(Parcel in) {
            return new Interruption(in);
        }

        @Override
        public Interruption[] newArray(int size) {
            return new Interruption[size];
        }
    };
}
