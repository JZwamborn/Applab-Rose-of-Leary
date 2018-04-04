package org.billthefarmer.miditest;


import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import static org.billthefarmer.miditest.TrichordType.MAJ;

/**
 * Created by Berend on 16-8-2017.
 */

public class GenerateQuiz {
    public static Quiz generate3partMajorOnePartTooHighQuiz(){
        Quiz quiz = new Quiz();
        int root_range_min = 42;
        int root_range_max = 72;
        int detune_range_min = 45;
        int detune_range_max = 55;
        int root = ThreadLocalRandom.current().nextInt(root_range_min, root_range_max + 1);
        int inversion = ThreadLocalRandom.current().nextInt(0, 2 + 1);
        quiz.setChord(MidiGenerator.generateTrichord(MAJ, root, inversion));
        int detune = ThreadLocalRandom.current().nextInt(detune_range_min, detune_range_max + 1);
        int voice = ThreadLocalRandom.current().nextInt(0, 2 + 1);
        quiz.setTuneList(new LinkedList<Integer>());
        quiz.getTuneList().add(0);
        quiz.getTuneList().add(0);
        quiz.getTuneList().add(0);
        quiz.getTuneList().add(voice, detune);
        quiz.setAnswer(voice);
        quiz.setVelocities(new LinkedList<Integer>());
        return quiz;

    }
}
