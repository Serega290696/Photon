package Photon.GameObjects.GOPhoton;

import Photon.*;
import Photon.Enums.DrawFigure;

/**
 * Created by Serega on 03.03.2015.
 */
public class GOPoint extends Photon {

    public float defaultSx;
    public float defaultSy;

    public static float moveOnStep = Game.moveOnStep;
    public Photon player;
    public float playerOldX;

    public float shiftObAlongX = 0;
    private int curPointNumber;

    public GOPoint(float x, float y, float sx, float sy, GOPlayer curPlayer) {
        player = curPlayer;
        playerOldX = curPlayer.x;
        this.figure = DrawFigure.CIRCLE;
        this.sx = curPlayer.defaultSx / 3;
        this.sy = this.sx;
        this.x = x;
        this.y = y;
        curPointNumber = player.path.size();
    }

    public GOPoint(float x, float y, Photon curPlayer) {

        this.figure = DrawFigure.CIRCLE;
        player = curPlayer;
        playerOldX = curPlayer.x;
        color = player.color;
        sx = Game.moveOnStep;
        this.defaultSx = curPlayer.defaultSx / 3;
        this.defaultSy = defaultSx;
        sy = 1;
        this.x = x;
        this.y = y;
        curPointNumber = player.path.size();

    }

    @Override
    public void move() {
//        if(x <= Game.blackHole.sx) {
//            amplitude *= 0.9f;
//        }
        if(x > player.getX()) {
            y = player.myFunction(player.t + (x - player.x) / moveOnStep * player.freak);
//            System.out.println(player.t + (x - player.x) / moveOnStep * player.freak);
        }
        else {
            float blSx = Game.blackHole.sx;
            y = player.path.get(curPointNumber + 1).y - playerYShift;
            y *= 0.97;
            y += playerYShift;
//            y = player.path.get(curPointNumber + 1).y;
        }
        if(player.getClass().getSimpleName().equals("GOPhotonFon")) {
            x -= player.speed;
        }
    }

    @Override
    public void update() {

        moveOnStep = Game.moveOnStep;
        float temp;//a
        temp = 1 / player.lengthTrajectory;
        if(x > player.getX()) {
            sy = player.lengthTrajectory - (this.x - player.x);
            if(sy <= 0)
                sy = 0;
            sy *= temp * defaultSy;
            opacity = sy / (player.lengthTrajectory*temp)/3;
            if(player.immortalityDie > 0) opacity *= 0.3;

//            x = Game.player.x + moveOnStep / Game.player.freak;
//            y = player.myFunction(player.t + (x - player.x)/moveOnStep*player.freak);
        }
        else {
//            y = player.path.get(curPointNumber+1).y;
            sy = (x+1) / player.x * defaultSy;
            opacity = (x + 1) / player.x;
            if(player.immortalityDie > 0)
                opacity *= (x + 1) / player.x *0.5;
        }
        sx = sy;
        if(Physics.checkCollisions(this, player)) {
            opacity = 0;
        }
        color = player.color;
    }

    public void delGO() {
        player.path.remove(this);
    }
    @Override
    public void render() {
        if(!Physics.checkCollisions(player, this)) {
            Draw.draw(figure, x, y, sx, sy, 0, color, opacity);
        }
    }

    @Override
    public void collision() { }
}
