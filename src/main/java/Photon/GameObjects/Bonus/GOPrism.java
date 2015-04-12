package Photon.GameObjects.Bonus;

import Photon.*;
import Photon.Enums.DrawFigure;

/**
 * Created by Serega on 21.03.2015.
 */
public class GOPrism extends GO {

    public float maxSize = 7;
    public boolean die = false;


    public GOPrism() {
        this.figure = DrawFigure.TRIANGLE;
//        this.sx = (float) (Math.pow(Game.level, 0.5f) + Math.random() * (2+Math.pow(Game.level, 0.5f)));
        this.sx = Main.game.gameConfiguration.prismSx;
//        if(this.sx >= maxSize) this.sx = maxSize;
//        this.sx *= 0.6;
        this.sy = sx;
        this.defaultSx = sx;
        this.y = (float) (Game.players.get(0).playerYShift + Game.players.get(0).defAmplitude * 2*(Math.random() - 0.5f));
        this.x = 100 + Draw.xshift;
        this.rotate = (float)Math.random()*360;
    }

    @Override
    public void move() {
        this.x -= Game.moveOnStep;
    }

    @Override
    public void update() {

        if(die) {
            float sizeStep = 10f / Main.fps;
            float opacityStep = - 1f / Main.fps * 2;
            sx += sizeStep;
//            sx = defaultSx + defaultSx / sx / 4f * defaultSx;
            sy = sx;
            opacity += opacityStep;
            if(opacity == 0) {
                Game.obstacles.remove(this);
            }
        }
        if(x < -sx*2)
            delGO(this);
        rotate += (float)(90 / Main.fps);
//        rotate += (float)(1f/Math.pow(3f, 1.4f));
        move();
    }

    @Override
    public void render() {
        Draw.draw(figure, x, y, sx, sy, rotate, color, opacity);
    }

    public void delGO(GO ob) {
        Game.somethingWasChanged = true;
        Game.bonuses.remove(ob);
    }
    @Override
    public void collision() {
        defaultColor = 2;
        color = defaultColor;
        die = true;
    }
}
