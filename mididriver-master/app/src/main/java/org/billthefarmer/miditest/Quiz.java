package org.billthefarmer.miditest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Berend on 16-8-2017.
 */

public class Quiz{
    private List<Integer> chord;
    //detuning is in cents
    private List<Integer> tuneList;
    private int answer;
    private List<Integer> velocities;

    public Quiz(){
        tuneList = new LinkedList<>();
        answer = -1;
        velocities = new LinkedList<>();

    }

    public List<Integer> getVelocities() {
        return velocities;
    }

    public void setVelocities(List<Integer> velocities) {
        this.velocities = velocities;
    }

    public void setChord(List<Integer> chord) {
        this.chord = chord;
    }

    public void setTuneList(List<Integer> tuneList) {
        this.tuneList = tuneList;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public List<Integer> getChord() {
        return chord;
    }

    public List<Integer> getTuneList() {
        return tuneList;
    }

    public int getAnswer() {
        return answer;
    }


}
