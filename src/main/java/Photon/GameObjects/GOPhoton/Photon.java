package Photon.GameObjects.GOPhoton;

import Photon.GO;
import Photon.Game;
import Photon.Main;

import java.util.ArrayList;

/**
 * Created by Serega on 01.04.2015.
 */
public abstract class Photon extends GO {


    public float defAmplitude = 20 * Main.ratio / 2;
    public float amplitude = Game.gameConfiguration.amplitude;
    public float freak = 0.12f;
    public float playerYShift = 50 * Main.ratio;

    public int immortalityDie = 0;
    public boolean immortal = false;
    public int timeToRecovery  = 1000;
    public float shiftObAlongX = 0;
//    public int hitPoints  = 0;

    public ArrayList<GOPoint> path = new ArrayList();
    public float lengthTrajectory = 30;

    public boolean die = false;
    public static float minFreak = Main.game.gameConfiguration.minFreak;
    public static float maxFreak = Main.game.gameConfiguration.maxFreak;
    public float speed = 0; // 0

    @Override
    public abstract void move() throws CloneNotSupportedException;
    @Override
    public abstract void update();
    @Override
    public abstract void collision();


    public float myFunction(float tempT) {
        float tempY = (float) Math.sin(tempT);
        tempY *= amplitude;
        tempY += playerYShift;
        return tempY;
    }

    public float myFunction(float tempT, float amplitudeT) {
        float tempY = (float) Math.sin(tempT);
        tempY *= amplitudeT;
        tempY += playerYShift;
        return tempY;
    }

    public void setX(float newX) {
        x = newX;
    }
    public float getXForPoint() {
        return x;
    }
}
