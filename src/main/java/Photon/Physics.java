package Photon;

import java.awt.*;
/**
 * Created by Serega on 13.03.2015.
 */

public class Physics {


    public static float sx1;
    public static float sy1;
    public static float sx2;
    public static float sy2;
    public static float x1;
    public static float x2;
    public static float y1;
    public static float y2;
    public static boolean checkCollisions(GO go1, GO go2) {


        sx1 = go1.getSx();
        sy1 = go1.getSy();
        sx2 = go2.getSx();
        sy2 = go2.getSy();

        x1 = go1.getX();
        y1 = go1.getY();
        x2 = go2.getX();
        y2 = go2.getY();

        x1 *= Main.em;
        y1 *= Main.em;
        sx1 *= Main.em;
        sy1 *= Main.em;
        x2 *= Main.em;
        y2 *= Main.em;
        sx2 *= Main.em;
        sy2 *= Main.em;

        Rectangle r1 = new Rectangle((int)(x1-sx1/2), (int)(y1-sy1/2), (int)sx1, (int)sy1);
        Rectangle r2 = new Rectangle((int)(x2-sx2/2), (int)(y2-sy2/2), (int)sx2, (int)sy2);
        return r1.intersects(r2);
    }
    public static boolean checkCollisionsWithLog(GO go1, GO go2) {
        sx1 = go1.getSx();
        sy1 = go1.getSy();
        sx2 = go2.getSx();
        sy2 = go2.getSy();

        x1 = go1.getX();
        y1 = go1.getY();
        x2 = go2.getX();
        y2 = go2.getY();

        x1 *= Main.em;
        y1 *= Main.em;
        sx1 *= Main.em;
        sy1 *= Main.em;
        x2 *= Main.em;
        y2 *= Main.em;
        sx2 *= Main.em;
        sy2 *= Main.em;
        System.out.println("Rect #1.");
        System.out.println("X: " + x1 + ".  \t(int)x: " + (int)x1 + ".  \ty: " + y1 + ".  \tsx: " + sx1  + ".  \tsy: " + sy1);
        System.out.println("Rect #2.");
        System.out.println("X: " + x2 + ".  \t(int)x: " + (int)x2 + ".  \ty: " + y2 + ".  \tsx: " + sx2  + ".  \tsy: " + sy2);
        Rectangle r1 = new Rectangle((int)(go1.getX()-sx1/2), (int)(go1.getY()-sy1/2), (int)sx1, (int)sy1);
        Rectangle r2 = new Rectangle((int)(go2.getX()-sx2/2), (int)(go2.getY()-sy2/2), (int)sx2, (int)sy2);
        return r1.intersects(r2);
    }


}
