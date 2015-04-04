package Photon.GameObjects.Enemy;

import Photon.*;
import Photon.GameObjects.GOPhoton.GOPlayer;

/**
 * Created by Serega on 22.03.2015.
 */
public class GOBlackHole extends GO {

    public float gravitationPower = 0;
    public float maxSpeedGravitation = 25;
    public float gravitationParameter = 0;
    public float defaultGravitationPower = 1;

    public GOBlackHole() {

        x = 0;
        y = 100/2*Main.ratio;
        sx = 30;
        x = sx/2;
        sy = 100*Main.ratio;
        figure = DrawFigure.RECT;
        color = 7;
        opacity = 1.0f;
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
            setGravitationPower();
            if(Physics.checkCollisions(this, obPlayer)) {
                Game.gameOver(obPlayer);
                Main.restartGame();
            }
        }
//        sx = gravitationPower * 10;
    }

    private void setGravitationPower() {
        gravitationPower = defaultGravitationPower;
        float blackHoleSize = (this.x + this.sx/2);
        if( (Main.game.player.x - blackHoleSize) / (100-blackHoleSize) > 0.6) {
            Main.game.player.superBonus = true;
            if( (Main.game.player.x - blackHoleSize) / (100-blackHoleSize) > 0.8) {
                gravitationPower = (float) ((Main.game.player.x - blackHoleSize) / (100 - blackHoleSize) - 0.7); // 0.1 ->0.3
                gravitationPower *= 10; // 3 -> 12
                gravitationPower *= defaultGravitationPower;
                gravitationPower = (float) Math.pow(gravitationPower, 4);
            }
        }
        else {
            Main.game.player.superBonus = false;
            gravitationPower = defaultGravitationPower;
        }
        gravitationPower -= gravitationParameter;
//        gravitationParameter *=
    }

    @Override
    public void render() {
        Draw.draw(figure, x, y, sx*1.1f, sy, rotate, color, 0.8f);
        Draw.draw(figure, x, y, sx, sy, rotate, color, 1);
    }

    @Override
    public void collision() {

    }
}
