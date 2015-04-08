package Photon;

import Photon.DataBase.DBWorker;
import Photon.DataBase.ListWorker;
import Photon.DataBase.User;
import Photon.Enums.DrawFigure;
import Photon.Enums.Music;
import Photon.Exceptions.PlayerDoNotExist;
import Photon.GameObjects.Bonus.GOPrism;
import Photon.GameObjects.Enemy.GOBlackHole;
import Photon.GameObjects.Enemy.GOObstacle;
import Photon.GameObjects.GOPhoton.GOPhotonFon;
import Photon.GameObjects.GOPhoton.GOPoint;
import Photon.GameObjects.GOPhoton.GOPlayer;
import org.lwjgl.input.Keyboard;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Serega on 03.03.2015.
 */
public class Game implements IGame{

    public static GameConfiguration gameConfiguration = new GameConfiguration();
    public static GOPlayer player;
    public static GOPlayer player2;
    public static GOBlackHole blackHole = new GOBlackHole();
    public static ArrayList<GOPlayer> players = new ArrayList<GOPlayer>();
    public static ArrayList<GOPhotonFon> fon = new ArrayList<GOPhotonFon>();
    public static ArrayList<GO> allObjects = new ArrayList<GO>();
    public static ArrayList<GOObstacle> obstacles = new ArrayList<GOObstacle>();
    public static ArrayList<GOPrism> bonuses = new ArrayList<GOPrism>();
//    public static GOPrism prism;
    public static int controlMode = 1;
    public static float freakChanger = (float) (Math.PI / Main.fps /6);

    public static float distance = 0;

    public static float time = 0;
    public static int integerTime= 0;
    public static int oldTime= 0;
    public static float timeCfCreationObstacle = 0;
    public static float timeCfCreationBonus = 0;

//    public static float moveOnStep = 20f/ Main.em;

    public static float defMoveOnStep = gameConfiguration.defMoveOnStep;
    public static float moveOnStep;
    public static boolean somethingWasChanged = false;
    public static int level = 1;
    public static boolean nowNewSecond = false;
    public static boolean mute = true;
    public static boolean mouseGrabbed = true;
    public static boolean pause = false;


    public Game() {
        clear();
        controlMode = 2;
        try {
//            GOPlayer massOfPlayers[] = new GOPlayer()[];
            GOPlayer massOfPlayers[] = new GOPlayer[gameConfiguration.playersAmount];
//            for(int i = 0; i < GameConfiguration.playersAmount; i++) {
//                massOfPlayers[i] = player = new GOPlayer(50 + 10*i, 50*Main.ratio, 2, DrawFigure.CIRCLE, User.defaultName, 2 + i, false);
//            }
            if(gameConfiguration.playersAmount >= 1) {
                massOfPlayers[0] =
                        (player = new GOPlayer(55, 50 * Main.ratio, 2, DrawFigure.CIRCLE, User.defaultName, 2, false));

            }
            if(gameConfiguration.playersAmount >= 2) {
                massOfPlayers[1] =
                        (player2 = new GOPlayer(45, 50 * Main.ratio, 2, DrawFigure.CIRCLE, User.defaultName + "_2", 5, false));
            }
//            massOfPlayers[] = {
//                    player = new GOPlayer(60, 50*Main.ratio, 2, DrawFigure.CIRCLE, User.defaultName, 2, false),
//                    player2 = new GOPlayer(40, 50*Main.ratio, 2, DrawFigure.CIRCLE, User.defaultName + "_2", 5, false),
////                    player2 = new GOPlayer(20, 50*Main.ratio, 2, DrawFigure.CIRCLE, "Player#2", 6, false),
////                    new GOPlayer(30, 50*Main.ratio, 1, DrawFigure.CIRCLE, "miniBot", 3, true),
////                    new GOPlayer(20, 50*Main.ratio, 1, DrawFigure.CIRCLE, "angryBot >.<", 3, true),
////                    null
////                    player = new GOPlayer(50, 50*Main.ratio, 3, DrawFigure.CIRCLE, "Serega", 2, false),
//            };
            addAllPlayers(massOfPlayers);
        } catch(PlayerDoNotExist myE) {
            System.err.print(myE);
        }
        allObjects.add(blackHole);
        if(!Draw.musicIsPlaying(Music.FON1) && !mute) {
            Draw.musicPlay(Music.FON1);
        }
//        prism = new GOPrism();
    }

    @Override
    public void restartGame() {

    }

    @Override
        public void clear() {
        players.clear();
        allObjects.clear();
        obstacles.clear();
        bonuses.clear();
        fon.clear();
//        gameConfiguration.defaultGravitationPower = 0;
        gameConfiguration.gravitationParameter = 5;
        level = 1;
        time = 0;
        integerTime = 0;
        timeCfCreationObstacle = 0;
        timeCfCreationBonus = 0;
        moveOnStep = defMoveOnStep;
//        if(Draw.musicIsPlaying(Music.FON1))
//            Draw.musicStop(Music.FON1);

    }

    @Override
    public void getInput() {
        float b = (float) (15f / Main.fps);
        switch(controlMode) {
            case 3:
                if(Keyboard.isKeyDown(Keyboard.KEY_UP) && !player.isBot){
                    if(player.y >= player.amplitude*2 + player.sy)
                        player.playerYShift-=b;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !player.isBot){
                    if(player.y <= 100*Main.ratio - player.amplitude*2 - player.sy)
                        player.playerYShift+=b;
                }
            case 1:
                if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !player.isBot){
                    if(player.freak >= player.minFreak)
                        player.freak -= freakChanger;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !player.isBot){
                    if(player.freak <= player.maxFreak)
                        player.freak += freakChanger;
                }
                if (player2 != null) {
                    if(Keyboard.isKeyDown(Keyboard.KEY_A) && !player2.isBot){
                        if(player2.freak >= player.minFreak)
                            player2.freak -= freakChanger;
                    }
                    if(Keyboard.isKeyDown(Keyboard.KEY_D) && !player2.isBot){
                        if(player2.freak <= player.maxFreak)
                            player2.freak += freakChanger;
                    }
                }
                break;
            case 2:
                if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !player.isBot){
                    if(player.freak <= player.maxFreak)
                        player.freak += freakChanger;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !player.isBot){
                    player.x += -1;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !player.isBot){
                    player.x += 1;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_UP) && !player.isBot){
                    player.collisionWithPrism();
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !player.isBot){
                    player.collisionWithObstacle();
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_1)){
                    ListWorker.getList();
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_M)){
                    mute = true;
                    if(Draw.musicIsPlaying(Music.FON1))
                        Draw.musicStop(Music.FON1);
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_N)){
                    mute = false;
                    if(!Draw.musicIsPlaying(Music.FON1))
                        Draw.musicPlay(Music.FON1);
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_0)){
                    mouseGrabbed = true;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_9)){
                    mouseGrabbed = false;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_W)){
                    level++;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_S)){
                    level--;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_P)){
                    pause = true;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_O)){
                    pause = false;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_I)){
                    player.immortal = true;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_U)){
                    player.immortal = false;
                }
                if (player2 != null) {
                    if(Keyboard.isKeyDown(Keyboard.KEY_Q) && !player2.isBot){
                        if(player2.freak <= player2.maxFreak)
                            player2.freak += freakChanger;
                    }
                }
                break;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_I)){
            player.immortalityDie = 99999999;
            player.die = false;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
            player.immortalityDie = 0;
            player.die = false;
        }
    }
    @Override
    public void update() {
        Main.mouseGrabbed(mouseGrabbed);
        if(pause) {
            mouseGrabbed = false;
            return;
        }
        else {
            mouseGrabbed = true;
        }
        Main.mouseGrabbed(mouseGrabbed);
        script();
        gameConfiguration.update();
//        prism.update();
        if(blackHole != null)
            blackHole.update();

        for(GO ob : allObjects) {
            ob.update();
        }
        for(GO ob : fon) {
            ob.update();
        }
        for(GOPlayer obPlayer : players) {
            blackHole.setGravitationPower(obPlayer);
            obPlayer.update();
            for(GOPoint point : obPlayer.path) {
                point.update();
            }
            obPlayer.danger = false;
        }
        do {
            somethingWasChanged = false;
            for (GO p : obstacles) {
                p.update();
                if (somethingWasChanged) break;
            }
            for(GOPrism bonus : bonuses) {
                bonus.update();
                if (somethingWasChanged) break;
            }
        }while(somethingWasChanged);

    }

    @Override
    public void script() {
        time+=Main.delay/1000f;
        integerTime = (int)time;
        if(oldTime == integerTime) {
            nowNewSecond = false;
            return;
        }
        else {
            nowNewSecond = true;
            oldTime = integerTime;
        }

        if(nowNewSecond) {
//            fon.add(new GOPhotonFon(100, (float)Math.random()*100f*Main.ratio, (float)(0.5 + 2.0*Math.random()), DrawFigure.CIRCLE, 3));
//            fon.add(new GOPhotonFon(100, (float)Math.random()*100f*Main.ratio, (float)(0.5 + 2.0*Math.random()), DrawFigure.CIRCLE, 3));
//            fon.add(new GOPhotonFon(100, (float)Math.random()*100f*Main.ratio, (float)(0.5 + 2.0*Math.random()), DrawFigure.CIRCLE, 3));
        }
        if(integerTime%5 == 0) {
            level++;
        }
        moveOnStep = gameConfiguration.moveOnStep;

        if(time - timeCfCreationObstacle >= 0.5 + (0.75f / Math.pow(level, 0.5f))) {
            obstacles.add(new GOObstacle());
            timeCfCreationObstacle = time;
        }
//        if(time - timeCfCreationBonus >= 1.0 + (5.0f / Math.pow(level, 0.5f))) {
        if(time - timeCfCreationBonus >= 1.0 + (5.0f / Math.pow(level, 0.5f))) {
            if(!obstacles.isEmpty()) {
                bonuses.add(new GOPrism());
                while(Physics.checkCollisions(obstacles.get(obstacles.size() - 1), bonuses.get(bonuses.size() - 1))) {
                    bonuses.remove(bonuses.size() - 1);
                    bonuses.add(new GOPrism());
                }
            }
            else
                bonuses.add(new GOPrism());
            timeCfCreationBonus = time;
        }
//        if(integerTime % 2 == 0) {
//            obstacles.add(new GOObstacle());
//        }
//        if(Math.random() < 0.05) {
//            obstacles.add(new GOObstacle());
//        }
    }

    @Override
    public void render() {
//        distance += 0.2;
        if(Main.game.player.x >= 65) {
            Draw.xshift = (float) Math.pow(Main.game.player.x - 65, 1f);
        }
        else
            Draw.xshift = 0;
        if(distance*moveOnStep >= 20) distance = 0;
//        Draw.draw(DrawFigure.FON, 10 - distance*moveOnStep, 50 * Main.ratio, 20, 100 * Main.ratio);
//        Draw.draw(DrawFigure.FON, 30 - distance*moveOnStep, 50 * Main.ratio, 20, 100 * Main.ratio);
//        Draw.draw(DrawFigure.FON, 50 - distance*moveOnStep, 50 * Main.ratio, 20, 100 * Main.ratio);
//        Draw.draw(DrawFigure.FON, 70 - distance*moveOnStep, 50 * Main.ratio, 20, 100 * Main.ratio);
//        Draw.draw(DrawFigure.FON, 90 - distance*moveOnStep, 50 * Main.ratio, 20, 100 * Main.ratio);
//        Draw.draw(DrawFigure.FON, 110- distance*moveOnStep, 50 * Main.ratio, 20, 100 * Main.ratio);
        Draw.draw(DrawFigure.FON2, 0, 20 * Main.ratio, 125, 60 * Main.ratio);

        for(GO ob : allObjects) {
            ob.render();
        }
        for(GO ob : fon) {
            ob.render();
        }
        for(GOPlayer obPlayer : players) {
            for(GOPoint point : obPlayer.path) {
                point.render();
            }
            obPlayer.render();
        }
        for(GO ob : obstacles) {
            ob.render();
        }
        for(GOPrism bonus : bonuses) {
            bonus.render();
        }
        Draw.gameInterface();
//        prism.render();
    }
    @Override
    public void delObj(GO removeOb) {
//        allObjects.remove(removeOb);
        obstacles.remove(removeOb);
    }
    private void addAllPlayers(GOPlayer massOfPlayers[]) throws PlayerDoNotExist{
        for(int i = 0; i < massOfPlayers.length; i++) {
            if(massOfPlayers[i] == null)
                throw new PlayerDoNotExist("Player do not exist! Player #" + (i+1)+ " = NULL. ");
        }
        for(int i = 0; i < massOfPlayers.length; i++) {
            players.add(massOfPlayers[i]);
        }
    }


    public static void gameOver(GOPlayer player) {
//        String nameOfPlayer = "Pashok";
//        player.setName(nameOfPlayer);

//        DBWorker.getAll().toString();
        User user = new User(player.name, (int)player.score, new Time((integerTime/3600), (integerTime/60)%60, integerTime%60), (new java.sql.Date( new Date().getTime())));
        DBWorker.insert(user);
        DBWorker.getAll().toString();
        if(Game.players.size() <= 1)
            Main.restartGame();// = true;
        else
            players.remove(player);
//        DBWorker.insert(player.name, (int)player.score, 1000, new Time(integerTime), (new java.sql.Date( new Date().getTime() )), 0);

//        DBWorker.update();
    }
}
