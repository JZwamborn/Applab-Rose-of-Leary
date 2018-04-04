package org.billthefarmer.miditest;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MidiGenerator {
    public static List<Integer> generateTrichord(TrichordType type, int root, int inversion){

        List<Integer> chord = new LinkedList<>();
        switch(type){
            default:
                break;
            case MAJ:
                chord.add(root);
                chord.add(root + 4);
                chord.add(root + 7);
                break;
            case MIN:
                chord.add(root);
                chord.add(root + 3);
                chord.add(root + 7);
                break;
            case DIM:
                chord.add(root);
                chord.add(root + 3);
                chord.add(root + 6);
                break;
            case AUG:
                chord.add(root);
                chord.add(root + 4);
                chord.add(root + 8);
                break;
        }
        return getInversion(chord, inversion);

    }

    private static List<Integer> getInversion(List<Integer> chord, int inversion) {
        switch (inversion){
            default:
                break;
            case 0:
                break;
            case 1:
                chord.add(3, chord.get(0) + 12);
                chord.remove(0);
                break;
            case 2:
                chord.add(3, chord.get(0) + 12);
                chord.add(4, chord.get(1) + 12);
                chord.remove(0);
                chord.remove(0);
                break;
        }
        return chord;
    }
}
