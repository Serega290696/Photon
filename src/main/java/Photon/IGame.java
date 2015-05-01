package Photon;

/**
 * Created by Serega on 22.03.2015.
 */
public interface IGame {

//    public static GOPlayer player = null;

//    public static ArrayList<GOPlayer> players = new ArrayList<GOPlayer>();
//    public static ArrayList<GO> allObjects = new ArrayList<GO>();
//    public static int controlMode = 1;

//    public static float time = 0;
//    public static int integerTime= 0;
//    public static int oldTime= 0;
//    public static boolean nowNewSecond = false;

//    public static int level = 5;

    public void restartGame();
    public void clear();
    public void getInput();
    public void update();
    public void render();
    public void script();
    public void delObj(GO go);

}
