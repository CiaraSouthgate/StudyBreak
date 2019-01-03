package com.ciarasouthgate.studybreak;


import android.os.Parcel;
import android.os.Parcelable;

public class StudySession implements Parcelable{
//    public int numInterruptions;
    public Interruption[] interruptions;

    public StudySession(int numInterruptions) {
        this.interruptions = new Interruption[numInterruptions];
    }

    public StudySession(Interruption[] i) {
        interruptions = i;
    }

    public Interruption[] getInterruptions() {
        return interruptions;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(numInterruptions);
        dest.writeTypedArray(interruptions, flags);
    }

    private StudySession(Parcel in) {
//        numInterruptions = in.readInt();
        interruptions = in.createTypedArray(Interruption.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<StudySession> CREATOR
            = new Parcelable.Creator<StudySession>() {

        @Override
        public StudySession createFromParcel(Parcel in) {
            return new StudySession(in);
        }

        @Override
        public StudySession[] newArray(int size) {
            return new StudySession[size];
        }
    };
}
