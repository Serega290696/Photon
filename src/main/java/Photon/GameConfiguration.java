package Photon;

/**
 * Created by Serega on 05.04.2015.
 */
public class GameConfiguration {


    public static float scoreBonusByPrism = 100;
    public static float scoreBonusByObstacle = -50;
    public static float defaultGravitationPower = 5;
    public static float gravitationParameter = 5;
    public static float defPrismGravitationParameter = defaultGravitationPower * 0.25f;
    public static float defObstacleGravitationParameter =  - defaultGravitationPower * 0.1f;
    public static float prismGravitationParameter = defPrismGravitationParameter;
    public static float obstacleGravitationParameter =  defObstacleGravitationParameter;

    public static float defMoveOnStep = 20f/ Main.em;
    public static float moveOnStep = defMoveOnStep;

    public GameConfiguration() {
        scoreBonusByPrism = 100;
        scoreBonusByObstacle = -50;
        defaultGravitationPower = 5;
        gravitationParameter = 5;
        defPrismGravitationParameter = defaultGravitationPower * 1.0f;
        defObstacleGravitationParameter =  - defaultGravitationPower * 0.5f;
        prismGravitationParameter = defPrismGravitationParameter;
        obstacleGravitationParameter =  defObstacleGravitationParameter;

        defMoveOnStep = 10f/ Main.em;
        moveOnStep = 13f/ Main.em;
    }

    public static void update() {

        if(moveOnStep <= 5)
            moveOnStep = defMoveOnStep + 0.01f * Game.level;
        prismGravitationParameter = (float) (defPrismGravitationParameter * (1 + 0.2 * Game.level));
        obstacleGravitationParameter = (float) (defObstacleGravitationParameter * (1 + 0.25 * Game.level));

        gravitationParameter -= (Game.level * 0.02f) / Main.fps;
    }


}
