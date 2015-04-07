package Photon;

/**
 * Created by Serega on 05.04.2015.
 */
public class GameConfiguration {

    public int playersAmount = 1;

    public float scoreBonusByPrism = 100;
    public float scoreBonusByObstacle = -50;
    public float defaultGravitationPower = 5;
    public float gravitationParameter = 5;
    public float defPrismGravitationParameter = defaultGravitationPower * 0.25f;
    public float defObstacleGravitationParameter =  - defaultGravitationPower * 0.1f;
    public float prismGravitationParameter = defPrismGravitationParameter;
    public float obstacleGravitationParameter =  defObstacleGravitationParameter;
    public float amplitude = 40 * Main.ratio / 2;



    public float defMoveOnStep = 20f/ Main.em;
    public float moveOnStep = defMoveOnStep;

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

    public void update() {

        if(moveOnStep <= 8)
            moveOnStep = defMoveOnStep + 0.05f * Game.level;
        prismGravitationParameter = (float) (defPrismGravitationParameter * (1 + 0.2 * Game.level));
        obstacleGravitationParameter = (float) (defObstacleGravitationParameter * (1 + 0.25 * Game.level));

        gravitationParameter -= (Game.level * 0.02f) / Main.fps;
    }


}
