package com.ciarasouthgate.studybreak;


public class StudySession {

    private static int numInterruptions;
    private static Interruption[] inter;
    public StudySession(int numInterruptions, Interruption[] inter){
        this.numInterruptions = numInterruptions;
        this.inter = inter;
        }

    public void setUp(){

        numInterruptions = 2; //ToDo - get data from layout
        String[] taskName = new String[numInterruptions];
        long[] taskInterval = new long[numInterruptions];
        long[] taskDuration = new long[numInterruptions];
        Interruption[] inter = new Interruption[numInterruptions];

        for (int i = 0; i < numInterruptions; i++){
            Interruption temp = new Interruption(taskName[i], taskInterval[i],
                    taskDuration[i]);
            inter[i] = temp;
        }


        StudySession studyObject= new StudySession(numInterruptions, inter);
        //ToDo - need timer countdown or countup (use mod boolean to call methods)
    }
}
