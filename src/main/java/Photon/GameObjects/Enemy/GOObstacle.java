package Photon.GameObjects.Enemy;

import Photon.*;
import Photon.Enums.DrawFigure;
import Photon.GameObjects.GOPhoton.GOPlayer;

/**
 * Created by Serega on 13.03.2015.
 */
public class GOObstacle extends GO{
    public int defaultColor = 4;
    public boolean addToDodgedObstacle = false;
    public float maxSize = 7;
    public boolean die = false;

    public GOObstacle() {
        this.figure = DrawFigure.RECT;
        this.sx = Main.game.gameConfiguration.obstSx;
//        this.sx = (float) (Math.pow(Game.level, 0.5f) + Math.random() * (2+Math.pow(Game.level, 0.5f)));
//        if(this.sx >= maxSize) this.sx = maxSize;
//        this.sx *= Game.gameConfiguration.moveOnStep * 0.4;
        this.sy = sx;
        this.defaultSx = sx;
        this.y = (float) (Game.players.get(0).playerYShift + Game.players.get(0).defAmplitude * 2*(Math.random() - 0.5f));
        this.x = 100 + Draw.xshift;
        this.rotate = 0;
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
        for(GOPlayer obPlayer : Game.players) {
//            if(obPlayer.danger) continue;
            if(x < obPlayer.x) {
                color = defaultColor;
                if(defaultColor != 0 && defaultColor != 1 && defaultColor == obPlayer.color && !this.addToDodgedObstacle && obPlayer.immortalityDie <= 0) {
                    this.addToDodgedObstacle = true;
                    obPlayer.setDodgedObstacle();
                }
//                if(defaultColor == obPlayer.color && obPlayer.immortalityDie > 0)
//                    defaultColor = 1;
                continue;
            }
            if(obPlayer.isClashWith(this) && Math.abs(x - obPlayer.getX()) < obPlayer.lengthTrajectory) {
                color = 1;
                break;
            }
            else
                color = defaultColor;
        }
        if(x < -sx*2)
            delGO(this);
        rotate += (float)(90 / Main.fps);
//        rotate += (float)(5f/Math.pow(3f, 1.4f));
        move();
    }

    public void interactionWithPlayer(GO ob) {
        for(GOPlayer obPlayer : Game.players) {
//            if(obPlayer.danger) continue;
            if(x < obPlayer.x) {
                color = defaultColor;
                if(defaultColor != 0 && defaultColor != 1 && defaultColor == obPlayer.color && !this.addToDodgedObstacle && obPlayer.immortalityDie <= 0) {
                    this.addToDodgedObstacle = true;
                    obPlayer.setDodgedObstacle();
                }
                if(defaultColor == obPlayer.color && obPlayer.immortalityDie > 0)
                    defaultColor = 1;
                continue;
            }
            if(obPlayer.isClashWith(this) && Math.abs(x - obPlayer.getX()) < obPlayer.lengthTrajectory) {
                color = 1;
                if(defaultColor != 1)
                    defaultColor = obPlayer.color;
                break;
            }
            else
                color = defaultColor;
        }
    }
    public void delGO(GO ob) {
        Game.somethingWasChanged = true;
        Game.obstacles.remove(ob);
    }

    @Override
    public void collision() {
        defaultColor = 1;
        color = defaultColor;
        die = true;

    }
    @Override
    public void render() {
        Draw.draw(figure, x, y, sx, sy, (float) (rotate + Math.random() * 25), color, opacity);
    }

}
