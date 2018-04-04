package org.billthefarmer.miditest;

import android.content.res.Resources;

import org.billthefarmer.mididriver.MidiDriver;

import java.util.List;
import java.util.Locale;

/**
 * Created by Berend on 16-8-2017.
 */

public class MidiPlayer implements MidiDriver.OnMidiStartListener{
    protected MidiDriver midi;
    private int standardVelocity = 70;
    public MidiPlayer(){
        midi = new MidiDriver();
        midi.setOnMidiStartListener(this);
    }

    //if velocity per note is not defined as list, 70 is standard velocity for each note
    //each note is played on a different channel for detuning reasons
    public void playChord(List<Integer> chord, List<Integer> detuneList){
        for(int i = 0; i < detuneList.size(); i++){
            sendPitchBendMidi(0xE0 + i, (int) (0.32 * detuneList.get(i)) + 64);
        }
        for(int i = 0; i < chord.size(); i++){
            sendMidi(0x90 + i, chord.get(i), standardVelocity);
        }
    }

    public void playChord(List<Integer> chord){
        for(int i = 0; i < chord.size(); i++){
            sendMidi(0x90 + i, chord.get(i), standardVelocity);
        }
    }

    //ideetje om een persoon in de sectie het hardst te laten spelen
//    public void playChord(List<Integer> chord, List<Integer> velocity){
//        if(velocity.size() != chord.size()){
//            for (int i =0; i < chord.size(); i++){
//                velocity.add(70);
//            }
//        }
//        for(int i = 0; i < chord.size(); i++){
//            sendMidi(0x90 + i, chord.get(i), velocity.get(i));
//        }
//    }

    public void stopChord(List<Integer> chord){
        for(int i = 0; i < chord.size(); i++){
            sendMidi(0x80 + i, chord.get(i), 0);
        }
        for(int i = 0; i < 16; i++){
            sendPitchBendMidi(0xE0 + i, 64);
        }
    }

    // Send a midi message
    protected void sendMidi(int m, int p)
    {
        byte msg[] = new byte[2];

        msg[0] = (byte) m;
        msg[1] = (byte) p;

        midi.write(msg);
    }

    // Send a midi message
    protected void sendMidi(int m, int n, int v)
    {
        byte msg[] = new byte[3];

        msg[0] = (byte) m;
        msg[1] = (byte) n;
        msg[2] = (byte) v;

        midi.write(msg);
    }

    protected void sendPitchBendMidi(int command, int value)
    {
        byte msg[] = new byte[3];

        msg[0] = (byte) command;
        //msg[1] = (byte) (byte) (value & 0xFF);
        msg[1] = (byte) 0;
        msg[2] = (byte) value;

        midi.write(msg);
    }

    @Override
    public void onMidiStart()
    {
        // Program change - harpsicord

        sendMidi(0xc0, 57);
        sendMidi(0xc1, 57);
        sendMidi(0xc2, 57);

        // Get the config

        int config[] = midi.config();

        //String info = String.format(Locale.getDefault(), format, config[0],
         //       config[1], config[2], config[3]);
    }

    public void onResume(){
        if (midi != null)
            midi.start();
    }

    public void onPause(){
        // Stop midi

        if (midi != null)
            midi.stop();
    }

}
