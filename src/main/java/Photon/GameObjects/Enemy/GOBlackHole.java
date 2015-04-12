package Photon.GameObjects.Enemy;

import Photon.*;
import Photon.Enums.DrawFigure;
import Photon.GameObjects.GOPhoton.GOPlayer;

/**
 * Created by Serega on 22.03.2015.
 */
public class GOBlackHole extends GO {

    public float specialGravityParameter = 0;
    public float gravitationPower = 0;
    public float maxSpeedGravitation = 25;
    public float gravitationParameter = 0;
    public float defaultGravitationPower = Game.gameConfiguration.defaultGravitationPower;

    public GOBlackHole() {

        x = 0;
        y = 100/2*Main.ratio;
        sx = 30;
        x = sx/2;
        sy = 100*Main.ratio;
        figure = DrawFigure.RECT;
        color = 7;
        opacity = 1.0f;
        defaultGravitationPower = Game.gameConfiguration.defaultGravitationPower;
        gravitationParameter = defaultGravitationPower;
//        x = -100/2;
//        y = 100/2*Main.ratio;
//        sx = 100*1.2f;
//        sy = 100*3*Main.ratio;
//        figure = DrawFigure.RECT;
//        color = 7;
//        opacity = 1.0f;
    }

    @Override
    public void move() throws CloneNotSupportedException {

    }

    @Override
    public void update() {
        gravitationParameter = Game.gameConfiguration.gravitationParameter;
//        float growth = 0.1f;
//        if(gravitationPower < maxSpeedGravitation)
//            gravitationPower += growth / Main.fps;
        gravitationPower = 1;

        for(GO ob : Game.obstacles) {
            if(Physics.checkCollisions(this, ob)) {
                ob.collision();
//                break;
            }
        }
        for(GO bonus : Game.bonuses) {
            if(Physics.checkCollisions(this, bonus)) {
                bonus.collision();
//                break;
            }
        }
        for(GO photonFon : Game.fon) {
            if(Physics.checkCollisions(this, photonFon)) {
                photonFon.collision();
//                break;
            }
        }
        for(GOPlayer obPlayer : Game.players) {
//            setGravitationPower(obPlayer);
            if(Physics.checkCollisions(this, obPlayer)) {
                Game.gameOver(obPlayer);
            }
        }
//        sx = gravitationPower * 10;
    }

    public void setGravitationPower(GOPlayer curPlayer) {
        gravitationParameter = Game.gameConfiguration.gravitationParameter;
        gravitationPower = defaultGravitationPower;
        float blackHoleSize = (this.x + this.sx/2);
        if( (curPlayer.x) / (100) > 0.45) {
//            curPlayer.superBonus = true;
            if( (curPlayer.x) / (100) > 0.5 && defaultGravitationPower - gravitationParameter < 0) {
                specialGravityParameter = gravitationPower;
                gravitationPower = (float) ((Main.game.player.x ) / (100) - 0.4); // 0.1 ->0.6
                gravitationPower *= 10; // 10 -> 60
//                gravitationPower *= defaultGravitationPower;
                gravitationPower = (float) Math.pow(gravitationPower, 5.0f);
                specialGravityParameter = specialGravityParameter / gravitationPower;
                specialGravityParameter = 1 / specialGravityParameter;
            }
            else {
                specialGravityParameter = 1;
            }
        }
        else {
            specialGravityParameter = 1;
//            Main.game.player.superBonus = false;
            gravitationPower = defaultGravitationPower;
        }
        gravitationPower -= gravitationParameter;
    }

    @Override
    public void render() {
        Draw.draw(figure, x, y, sx*1.1f, sy, rotate, color, 0.3f);
        Draw.draw(figure, x, y, sx, sy, rotate, color, 1);
    }

    @Override
    public void collision() {

    }
}
