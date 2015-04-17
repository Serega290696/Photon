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
//        for(int i = 0; i < Game.gameConfiguration.playersAmount; i++) {
            gravitationParameter = defaultGravitationPower;
//        }
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
        gravitationParameter = Game.gameConfiguration.gravitationParameter[0];
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
        for(GOPlayer obPlayer : Game.players) {
//            setGravitationPower(obPlayer);
//            if(Physics.checkCollisions(this, obPlayer)) {
//                Game.gameOver(obPlayer);
//            }
        }
//        sx = gravitationPower * 10;
    }

    public void setGravitationPower(GOPlayer curPlayer) {
        gravitationParameter = Game.gameConfiguration.gravitationParameter[Main.game.players.indexOf(curPlayer)];
        gravitationPower = defaultGravitationPower;
            specialGravityParameter = 1;
            gravitationPower = defaultGravitationPower;
        if(defaultGravitationPower-gravitationParameter < 0) {
                gravitationParameter -= gravitationParameter*0.1f/Main.fps;
            Game.gameConfiguration.gravitationParameter[Main.game.players.indexOf(curPlayer)] = gravitationParameter;
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
