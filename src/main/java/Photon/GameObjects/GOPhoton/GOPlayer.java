package Photon.GameObjects.GOPhoton;

import Photon.*;
import Photon.Enums.DrawFigure;
import Photon.GameObjects.Bonus.GOPrism;
import Photon.GameObjects.Enemy.GOObstacle;

/**
 * Created by Serega on 03.03.2015.
 */
public class GOPlayer extends Photon {

//    public float defAmplitude = 20 * Main.ratio / 2;
//    public float amplitude = 20 * Main.ratio / 2;
//    public float freak = 0.12f;
//    public float playerYShift = 50 * Main.ratio;
    public float score = 0;
    public float scoreBonusBySecond = 1;
    public float scoreBonusByPrism = Game.gameConfiguration.scoreBonusByPrism;
    public float scoreBonusByObstacle = Game.gameConfiguration.scoreBonusByObstacle;
    public float factor = 1;
    public boolean superBonus = false;
    private float prismGravitationParameter = Game.gameConfiguration.prismGravitationParameter;
    private float obstacleGravitationParameter =  Game.gameConfiguration.obstacleGravitationParameter;
    public int prism = 0;
    public int obstacles = 0;
    public float score2 = 0;
//    public int immortalityDie  = 0;
//    public int timeToRecovery  = 3000;
    public int hitPoints  = 0;
//    public ArrayList<GOPoint> path = new ArrayList();


//    public float lengthTrajectory = 30;
    public boolean danger = false;
    private float penalty = 4;
    public String name;


    public boolean isBot = false;
    public boolean needToUpFreak = true;
    public int botLevel = 22;
    public int dodgedObstacle = 0;
    public int comboBonus = 0;


//    public static float minFreak = 0.15f;
//    public static final float maxFreak = 0.5f;


    public GOPlayer(float x, float y, float sx, DrawFigure figure, String name, int color, boolean isBot) {
        this.x = x;
        this.y = y;
        this.defaultY = y;
        this.sx = sx;
        this.sy = sx;
        defaultSx = sx;
        this.figure = figure;
        this.name = name;
        this.color = color;
        this.defaultColor = color;
        this.isBot = isBot;
//        this.gameOver();
//        this.x -= 10 * hitPoints;
//        this.t = (float) (200*Math.random());
        this.t = 0;

        float temp = lengthTrajectory / Game.moveOnStep -1;
        for(int i = 0; ; i++) {
            path.add(new GOPoint(i * Game.moveOnStep*1.5f, defaultY, this));
//            path.add(new GOPoint(i * 0.75f, defaultY, this));
            if(i * Game.moveOnStep > Main.dWidth)
                break;
        }
    }

    private void gameOver() {
        Game.gameOver(this);
    }

    public GOPlayer() {
        figure = DrawFigure.CIRCLE;
        this.x = 60;
        sx = 3;
        sy = 3;
        defaultSx = sx;
        name = "Player " + Game.players.size() + "";
//        x -= 10 * hitPoints;
        setX(x - 10 * hitPoints);
    }

    public float myFunction(float tempT) {
        float tempY = (float) Math.sin(tempT);
        tempY *= amplitude;
        tempY += playerYShift;
        return tempY;
    }



    public void update() {
        updateLvlConfiguration();

        if(immortalityDie > 0) {
            immortalityDie -= Main.delay;
        }
        else {
            die = false;
            color = defaultColor;
            opacity = 1f;
        }
        if(die) {
            opacity = 0.5f;
                if(immortalityDie > timeToRecovery/2)
                    sx = defaultSx + timeToRecovery/500f - immortalityDie /500f * (1);
                else {
                    sx = defaultSx + immortalityDie /500f;
                }
            sy = sx;
        }
        else {
            setScore(scoreBonusBySecond);
            score2 = dodgedObstacle + hitPoints;


        }

        if(Game.controlMode == 2 && freak >= minFreak)
            freak -= 0.01;



//        if(Game.nowNewSecond) {
//            System.out.print(Game.integerTime + ". lvl:" + Game.level + ". " + name + ".\t\tScore: " + (int) score + ".   Dodged obstacles: " + dodgedObstacle + ".   Clash with obstacles: " + Math.abs(hitPoints) +
//                            "\t\t ||| \t\t Color: " + color + ". Freak: " + freak + ". IsBot: " + isBot
//            );
//            if (isBot)
//                System.out.print("\t\t ||| \t\t BOT: TRUE" + ". BotLevel: " + botLevel + ". NeedToUpFreak: " + needToUpFreak);
//            System.out.println();
//        }

        checkCollisions();
        move();
        for (GOPoint p : path) {
            p.move();
        }
    }
    public void updateLvlConfiguration() {
        minFreak = Main.game.gameConfiguration.minFreak;
        maxFreak = Main.game.gameConfiguration.maxFreak;
        prismGravitationParameter = Game.gameConfiguration.prismGravitationParameter;
        obstacleGravitationParameter =  Game.gameConfiguration.obstacleGravitationParameter;
        Game.blackHole.gravitationParameter = Game.gameConfiguration.gravitationParameter;
    }

    @Override
    public void move() {
        if (isBot) {
            for (GOObstacle obstacle : Game.obstacles) {
                danger = isClashWith(obstacle);
                if (isClashWith(obstacle)) {
                    for (int i = 0; i < botLevel && danger; i++) {
                        if (danger && needToUpFreak && freak < maxFreak) {
                            freak += 2*Game.freakChanger;
                            if (freak >= maxFreak)
                                needToUpFreak = false;
                        }
                        else if (danger && !needToUpFreak) {
                            if (freak <= minFreak)
                                needToUpFreak = true;
                        }
                        danger = false;
                        for (GOObstacle obstacle2 : Game.obstacles) {
                            if (isClashWith(obstacle2)) {
                                danger = true;
                                break;
                            }
                        }
                        if(danger == false) break;
                    }
                    needToUpFreak = true;
                    break;
                }
            }
        }
        if(Game.players.size() <= 5) {
            setX(x - (Main.game.blackHole.gravitationPower) / Main.fps * (1 + 1/this.x));
        }
        t += freak;
        y = myFunction(t);
//        if(x <= Main.game.blackHole.sx + 20) {
//            y -= playerYShift;
//            y *=  x / (Main.game.blackHole.sx + 20);
//            y += playerYShift;
//        }
        if (Math.abs(shiftObAlongX) > 0) {
            if (Math.abs(shiftObAlongX) < 0.5)
                shiftObAlongX = 0;
            setX((float) (x - 0.2 * Math.signum(shiftObAlongX)));
            shiftObAlongX -= 0.2 * Math.signum(shiftObAlongX);
        }

    }
    public void setX(float newX) {
        x = newX;
}
    public void checkCollisions() {
        for(GOObstacle ob : Game.obstacles) {
            if(Physics.checkCollisions(this, ob) && !die && !ob.die && !immortal) {
                ob.collision();
                collisionWithObstacle();
                break;
            }
        }
        for(GOPrism bonus : Game.bonuses) {
            if(Physics.checkCollisions(this, bonus) && !die && !bonus.die && !immortal) {
                bonus.collision();
                collisionWithPrism();
                break;
            }
        }
    }
    @Override
    public void collision() {

    }
    public void collisionWithObstacle() {
        obstacles++;
        immortalityDie = timeToRecovery;
        die = true;
        color = 1;
        Game.gameConfiguration.gravitationParameter += obstacleGravitationParameter;
//        setScore(scoreBonusByObstacle);
        comboBonus = 0;
    }
    public void collisionWithPrism() {
        prism++;
        comboBonus++;
        setScore(scoreBonusByPrism);
        Game.gameConfiguration.gravitationParameter += prismGravitationParameter;
//        immortalityDie = timeToRecovery;
//        setScore(scoreBonusByPrism);
//        shiftObAlongX += -penalty*2;
    }


    public void render() {
//        System.out.println("X: " + x + ".  Y: " + y + ".  Freak: " + freak + "SX/SY: " + sx + "/" + sy);
        Draw.draw(figure, x, y, sx, sy, 0, color, opacity);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
        this.defaultColor = color;
    }
    public boolean isClashWith(GOObstacle obstacle) {
        return isClashWith(this, obstacle);
    }
    public boolean isClashWith(GOPlayer player, GOObstacle obstacle) {
        for (GOPoint point : this.path) {
            if (Physics.checkCollisions(obstacle, new GOPoint(point.getX(), point.getY(), this.getSx()*1.5f, this.getSy()*1.5f, this))) {
                return true;
            }
        }
        return false;
    }

    public void setScore(float a) {
        float delta = Main.game.blackHole.x + Main.game.blackHole.sx/2;
//        factor = (float) (1 + (x-delta) / (100-delta) * 2 * (superBonus?2:1) * (Math.pow(Game.level, 0.3f)/3)); //
        factor = 0.1f;
        score+=a*factor*(comboBonus+1)*(int)(0.7f+Main.game.level * 0.3f);
    }
    public void setDodgedObstacle() {
        if(immortalityDie <= 0) {
            dodgedObstacle++;
//            score += 50;
        }
    }
}
