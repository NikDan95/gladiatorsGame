package model;

import java.util.Random;

public class Caesar {
    private static Caesar caesar = new Caesar();

    private Caesar() {
    }

    public static Caesar getCaesar() {
        return caesar;
    }

    public boolean getVerdict() {
        return new Random().nextBoolean();
    }

}
