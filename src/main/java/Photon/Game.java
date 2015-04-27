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
import Photon.GameObjects.GOPhoton.GOPlayer;
import Photon.GameObjects.GOPhoton.GOPoint;
import org.lwjgl.input.Keyboard;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Serega on 03.03.2015.
 */
public class Game implements IGame{

    public static GameConfiguration gameConfiguration = new GameConfiguration();
    public GOPlayer player;
    public static GOPlayer player2;
    public static GOBlackHole blackHole = new GOBlackHole();
    public static ArrayList<GOPlayer> players = new ArrayList<GOPlayer>();
    public static ArrayList<GOPhotonFon> fon = new ArrayList<GOPhotonFon>();
    public static ArrayList<GO> allObjects = new ArrayList<GO>();
    public static ArrayList<GOObstacle> obstacles = new ArrayList<GOObstacle>();
    public static ArrayList<GOPrism> bonuses = new ArrayList<GOPrism>();
//    public static GOPrism prism;
    public static int controlMode = 1;
    public static float freakChanger = Main.game.gameConfiguration.freakChanger;

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
    public static int level;
    public static boolean nowNewSecond = false;
    public static boolean mute = true;
    public static boolean mouseGrabbed = true;
    public static boolean pause = false;
    private static float timeToPrism;
    private static float timeToObst;
    private static boolean restartGame = false;
    private static long lastFrame = 0;

    private boolean isEnableCheats = true;
    private int toEnableCheats[] = new int[]{5, 5, 6, 7, 2, 5};
    private int toEnableCheatN = 0;


    public Game() {
        clear();
        controlMode = 2;
        try {
//            GOPlayer massOfPlayers[] = new GOPlayer()[];
            GOPlayer massOfPlayers[] = new GOPlayer[gameConfiguration.playersAmount];
//            for(int i = 0; i < GameConfiguration.playersAmount; i++) {
//                massOfPlayers[i] = player = new GOPlayer(50 + 10*i, 50*Main.ratio, 2, DrawFigure.CIRCLE, User.defaultName, 2 + i, false);
//            }
            if(gameConfiguration.playersAmount == 1) {
                massOfPlayers[0] =
                        (player = GOPlayer.newBuilder()
                                        .setXStart(GOPlayer.beginX)
                                        .setYStart(50 * Main.ratio)
                                        .setSize(2)
                                        .setFigure(DrawFigure.CIRCLE)
                                        .setName(User.defaultName)
                                        .setColor(2)
                                        .isBot(false)
                                        .build()
                        );
                System.out.println("A: "  + player.x);
            }
            if(gameConfiguration.playersAmount == 2) {
                massOfPlayers[0] =
                        (player = GOPlayer.newBuilder()
                                .setXStart(45)
                                .setYStart(40 * Main.ratio)
                                .setSize(2)
                                .setFigure(DrawFigure.CIRCLE)
                                .setName(User.defaultName)
                                .setColor(2)
                                .isBot(false)
                                .build()
                        );
                massOfPlayers[1] =
                        (player = GOPlayer.newBuilder()
                                .setXStart(GOPlayer.beginX)
                                .setYStart(60 * Main.ratio)
                                .setSize(2)
                                .setFigure(DrawFigure.CIRCLE)
                                .setName(User.defaultName + "_2")
                                .setColor(4)
                                .isBot(false)
                                .build()
                        );
            }
            if(gameConfiguration.playersAmount >= 3) {
                massOfPlayers[0] =
                        (player = GOPlayer.newBuilder()
                                .setXStart(GOPlayer.beginX)
                                .setYStart(35 * Main.ratio)
                                .setSize(2)
                                .setFigure(DrawFigure.CIRCLE)
                                .setName(User.defaultName)
                                .setColor(2)
                                .isBot(false)
                                .build()
                        );
                massOfPlayers[1] =
                        (player = GOPlayer.newBuilder()
                                .setXStart(GOPlayer.beginX)
                                .setYStart(50 * Main.ratio)
                                .setSize(2)
                                .setFigure(DrawFigure.CIRCLE)
                                .setName(User.defaultName + "_2")
                                .setColor(4)
                                .isBot(false)
                                .build()
                        );
                massOfPlayers[2] =
                        (player = GOPlayer.newBuilder()
                                .setXStart(GOPlayer.beginX)
                                .setYStart(65 * Main.ratio)
                                .setSize(2)
                                .setFigure(DrawFigure.CIRCLE)
                                .setName(User.defaultName + "_3")
                                .setColor(3)
                                .isBot(true)
                                .build()
                        );
            }
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

    public void restartGame() {

    }

    public void clear() {
        Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        while(true) {
                            Draw.writeFramesPerSecond((int)(1000f/ (new Date().getTime()-lastFrame)));
                            lastFrame = new Date().getTime();
//                            System.out.println("a");
                        }
                    }
                }
        );
        thread.start();
        players.clear();
        allObjects.clear();
        obstacles.clear();
        bonuses.clear();
        fon.clear();
//        gameConfiguration.defaultGravitationPower = 0;
//        gameConfiguration.gravitationParameter = gameConfiguration.defaultGravitationPower;
            for(int i = 0; i < gameConfiguration.playersAmount; i++) {
                gameConfiguration.gravitationParameter[i] = gameConfiguration.defaultGravitationPower;
            }
        level = 1;
        time = 0;
        integerTime = 0;
        timeCfCreationObstacle = 0;
        timeCfCreationBonus = 0;
        moveOnStep = defMoveOnStep;
        restartGame = false;
//        if(Draw.musicIsPlaying(Music.FON1))
//            Draw.musicStop(Music.FON1);

    }

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
                if(isEnableCheats)
                    getInputCheats();
                if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !player.isBot){
                    if(player.freak <= player.maxFreak)
                        player.freak += freakChanger;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_5))
                {
                    if(toEnableCheatN > toEnableCheats.length-1) {
                        isEnableCheats = true;
                        break;
                    }
                    if(toEnableCheats[toEnableCheatN] == 5) {
                        toEnableCheatN++;
                    }
                    else if(toEnableCheats[toEnableCheatN-1] == 5){}
                    else toEnableCheatN = 0;

                }
                if(Keyboard.isKeyDown(Keyboard.KEY_6))
                {
                    if(toEnableCheats[toEnableCheatN] == 6)
                        toEnableCheatN++;
                    else if(toEnableCheats[toEnableCheatN-1] == 6){}
                    else toEnableCheatN = 0;
                    if(toEnableCheatN > toEnableCheats.length-1) {
                        isEnableCheats = true;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_7))
                {
                    if(toEnableCheats[toEnableCheatN] == 7)
                        toEnableCheatN++;
                    else if(toEnableCheats[toEnableCheatN-1] == 7){}
                    else toEnableCheatN = 0;
                    if(toEnableCheatN > toEnableCheats.length-1) {
                        isEnableCheats = true;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_2))
                {
                    if(toEnableCheatN > toEnableCheats.length-1) {
                        isEnableCheats = true;
                    }
                    if(toEnableCheats[toEnableCheatN-1] == 2){break;}
                    else if(toEnableCheats[toEnableCheatN] == 2)
                        toEnableCheatN++;
                    else toEnableCheatN = 0;
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
                if(Keyboard.isKeyDown(Keyboard.KEY_P)){
                    pause = true;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_O)){
                    pause = false;
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
    public void getInputCheats() {
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
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            level++;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            level--;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_I)){
            player.immortal = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_U)){
            player.immortal = false;
        }
    }
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
        gameConfiguration.update();
        script();
//        prism.update();
        if(blackHole != null)
            blackHole.update();

        for(GO ob : allObjects) {
            ob.update();
        }
        for(GOPlayer obPlayer : players) {
            blackHole.setGravitationPower(obPlayer);
            obPlayer.update();
            if(restartGame)
                break;
            else
                for(GOPoint point : obPlayer.path) {
                    point.update();
                }
//            obPlayer.danger = false;
        }
        if(restartGame) {
            Main.restartGame();
            return;
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

    public void script() {
        time+=Main.delay/1000f;
        integerTime = (int)time;
        if(oldTime == integerTime) {
            nowNewSecond = false;
//            return;
        }
        else {
            nowNewSecond = true;
            oldTime = integerTime;
        }

        if(integerTime%6 == 0 && nowNewSecond) {
            level++;
        }
        moveOnStep = gameConfiguration.moveOnStep;
        freakChanger = gameConfiguration.freakChanger;
        timeToPrism = gameConfiguration.timeToPrism;
        timeToObst = gameConfiguration.timeToObst;
        if(time - timeCfCreationBonus >= timeToPrism && Main.game.gameConfiguration.playersAmount <= 1) {
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

        if(level > 7) {
            if (time - timeCfCreationObstacle >= timeToObst) {
                obstacles.add(new GOObstacle());
                timeCfCreationObstacle = time;
            }
        }
        else {
            if(time - timeCfCreationObstacle >= timeToObst && time - timeCfCreationBonus > 0.2f) {
                obstacles.add(new GOObstacle());
                timeCfCreationObstacle = time;
            }
        }
    }

    public void render() {
//        distance += 0.2;
        if(Main.game.player.funX >= GOPlayer.beginX && gameConfiguration.playersAmount < 2) {
            Draw.xshift = (float) Math.pow(Main.game.player.funX - GOPlayer.beginX, 1f);
        }
        else
            Draw.xshift = 0;
        if(distance*moveOnStep >= 20) distance = 0;
        Draw.draw(DrawFigure.FON2, 0, 20 * Main.ratio, 150, 60 * Main.ratio);

        for(GO ob : allObjects) {
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
    }
    public void delObj(GO removeOb) {
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


    public static void gameOver() {
        for(GOPlayer player : players) {
            User user = new User(player.name, (int)player.score, new Time((integerTime/3600), (integerTime/60)%60, integerTime%60), (new java.sql.Date( new Date().getTime())));
            DBWorker.insert(user);
        }
        System.out.println("\n********************");
        DBWorker.getAll().toString();
        System.out.println("********************\n");
        restartGame = true;
    }
}
