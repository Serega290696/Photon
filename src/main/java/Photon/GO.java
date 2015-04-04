package Photon;

import Photon.Enums.DrawFigure;

/**
 * Created by Serega on 03.03.2015.
 */
public abstract class GO {

    public float t;
    public float x;
    public float y;
    public float sx;
    public float sy;
    public float defaultX;
    public float defaultY;
    public float defaultSx;
    public DrawFigure figure = DrawFigure.RECT;
    public float rotate = 0;
    public int color = 0;
    public int defaultColor = 0;
    public float opacity = 1;

    public abstract void move() throws CloneNotSupportedException;
    public abstract void update();
    public void render() {
        if(x -sx/2 <= 100 && x + sx/2 >= 0 && y -sy/2 <= 100*Main.ratio && y + sy/2 >= 0 )
            Draw.draw(figure, x, y, sx, sy, rotate, color, opacity);
    }
    public abstract void collision();
    public float getX() { return x; }
    public float getY() { return y; }
    public float getSx() { return sx; }
    public float getSy() { return sy; }

}
