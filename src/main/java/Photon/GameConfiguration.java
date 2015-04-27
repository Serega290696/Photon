package Photon;

/**
 * Created by Serega on 05.04.2015.
 */
public class GameConfiguration {

    public int playersAmount = 1;

    public float scoreBonusByPrism = 100;
    public float scoreBonusByObstacle = -50;
    public float defaultGravitationPower = 5;
//    public float gravitationParameter = defaultGravitationPower;
    public float gravitationParameter[] = new float[playersAmount];
    public float defPrismGravitationParameter ;
    public float defObstacleGravitationParameter ;
    public float prismGravitationParameter = defPrismGravitationParameter;
    public float obstacleGravitationParameter =  defObstacleGravitationParameter;
    public float amplitude = 40 * Main.ratio / 2;

    public static float minFreak = 0.12f;
    public static float maxFreak = 0.5f;
    public float freakChanger = (float) (Math.PI / Main.fps /6);

    public float defMoveOnStep = 20f/ Main.em;
    public float moveOnStep = defMoveOnStep;
    public float obstSx = 1;
    public float prismSx = 1;
    public float timeToObst;
    public float timeToPrism;

    public GameConfiguration() {
        scoreBonusByPrism = 100;
        scoreBonusByObstacle = -50;
        defaultGravitationPower = 5;
        for(int i = 0; i < playersAmount; i++) {
            gravitationParameter[i] = defaultGravitationPower;
        }
//        gravitationParameter = defaultGravitationPower;
        defPrismGravitationParameter = defaultGravitationPower * 1.5f;
        defObstacleGravitationParameter =  - defaultGravitationPower * 0.5f;
        prismGravitationParameter = defPrismGravitationParameter;
        obstacleGravitationParameter =  defObstacleGravitationParameter;

        defMoveOnStep = 10f/ Main.em;
        moveOnStep = 10f/ Main.em;
        obstSx = 1;
        prismSx = 1;
        timeToObst = 1.25f;
        timeToPrism = 6f;
//        moveOnStep = 3;
    }

    public void update() {
        int level = Game.level;
        if(moveOnStep <= 1.8f)
            moveOnStep = defMoveOnStep + 0.20f * level; // 0.15 -> 2.8
        if(minFreak < 0.16f)
            minFreak = 0.12f + 0.004f * level; // 0.12 -> 0.3
        if(minFreak < 0.7f)
            maxFreak = 0.4f + 0.03f * level; // 0.4 -> 0.7
//        prismGravitationParameter = (float) (defPrismGravitationParameter * (1 + 0.2 * level));
        if(level <= 10) {
            obstacleGravitationParameter = (float) -(prismGravitationParameter * (0.3f + 0.07f * level));
            obstSx = (float) (1 + 1*Math.random() + level * 0.1f);
            prismSx = (float) (2 + 1*Math.random() + level * 0.1f);
            freakChanger = (float) (Math.PI / Main.fps /6 * (0.8 + level*0.2));
            timeToObst = 1.75f - level * 0.075f;
            timeToObst = 1.75f - level * 0.125f;
            timeToPrism = 4f - level * 0.3f;
//            gravitationParameter -= (0.02f + level * 0.02f) / Main.fps;
            for(int i = 0; i < playersAmount; i++) {
                if(playersAmount < 2)
                    gravitationParameter[i] -= (0.02f + level * 0.02f) / Main.fps;
//                if(gravitationParameter[i] < 0)
//                    gravitationParameter[i] = 0;
            }
        }
        else {
            for(int i = 0; i < playersAmount; i++) {
                if(playersAmount < 2)
                    gravitationParameter[i] -= (0.22f + level * 0.01f) / Main.fps;
            }
//            gravitationParameter -= (0.22f + level * 0.01f) / Main.fps;
        }
//        obstacleGravitationParameter = (float) (defObstacleGravitationParameter * (1 + 0.25 * level));

    }


}
