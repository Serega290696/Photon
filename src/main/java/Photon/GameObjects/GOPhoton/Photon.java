package Photon.GameObjects.GOPhoton;

import Photon.GO;
import Photon.GameObjects.Enemy.GOObstacle;

import java.util.ArrayList;
import Photon.Main;

/**
 * Created by Serega on 01.04.2015.
 */
public abstract class Photon extends GO {


    public float defAmplitude = 20 * Main.ratio / 2;
    public float amplitude = 20 * Main.ratio / 2;
    public float freak = 0.12f;
    public float playerYShift = 50 * Main.ratio;

    public int immortal  = 0;
    public int timeToRecovery  = 1000;
    public float shiftObAlongX = 0;
//    public int hitPoints  = 0;

    public ArrayList<GOPoint> path = new ArrayList();
    public float lengthTrajectory = 30;

    public boolean die = false;
    public static float minFreak = 0.12f;
    public static final float maxFreak = 0.5f;
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

    public void setX(float newX) {
        x = newX;
    }
}
