package Photon.GameObjects.GOPhoton;

/**
 * Created by Serega on 01.04.2015.
 */
public class GOPhotonFon/* extends Photon*/{
/*
//    public float defAmplitude = 20 * Main.ratio / 2;
//    public float amplitude = 20 * Main.ratio / 2;
//    public float freak = 0.12f;
    public float photonYShift = 50 * Main.ratio;
//    public ArrayList<GOPoint> path = new ArrayList();
//
//
//    public float lengthTrajectory = 0;
//    public boolean danger = false;
//    public float shiftObAlongX = 0;
//
//    public boolean die = false;
    public static final float maxSpeed = 20;




//    private float speed;

    public GOPhotonFon(float x, float y, float sx, DrawFigure figure, int color) {
        this.x = x;
        this.y = y;
        this.defaultY = y;
        this.photonYShift = y;
        this.sx = sx;
        this.sy = sx;
        defaultSx = sx;
        this.figure = figure;
        this.color = color;
        this.defaultColor = color;
        this.speed = (float) (Math.pow(sx, 2) * (1 + 9*Math.random()) * 2);
        if(Math.random() > 0.9)
            speed *= -1;
        if(speed >= maxSpeed)
            speed = maxSpeed;
        this.freak = (float) (GOPlayer.minFreak/10f + 0.8f*Math.random() * (GOPlayer.maxFreak - GOPlayer.minFreak));
        lengthTrajectory = 0;
//        this.x -= 10 * hitPoints;
//        this.t = (float) (200*Math.random());
        this.t = 0;
        opacity = 0.8f/sx;

        float temp = lengthTrajectory / Game.moveOnStep -1;
        for(int i = 0; ; i++) {
            path.add(new GOPoint(i * Game.moveOnStep, defaultY, this));
            if(i * Game.moveOnStep > Main.dWidth)
                break;
        }
    }


    @Override
    public void move() throws CloneNotSupportedException {

        if(Game.players.size() == 1) {
//            x += penalty / (Main.fps * 8); // число - количество секуд за которые компенсирует пенальти
//            setX(x - (100 - x) / (100-(Main.game.blackHole.sx-100)/2) * Main.game.blackHole.maxSpeedGravitation / Main.fps);
            setX(x - (Main.game.blackHole.gravitationPower) / Main.fps * (1 + 1/this.x));
//            System.out.println( (Main.game.blackHole.gravitationPower) / Main.fps * (1 + 1/this.x) );
        }
        t += freak;
        y = myFunction(t);
        if (Math.abs(shiftObAlongX) > 0) {
            if (Math.abs(shiftObAlongX) < 0.5)
                shiftObAlongX = 0;
//                x -= 0.2 * Math.signum(shiftObAlongX);
            setX((float) (x - 0.2 * Math.signum(shiftObAlongX)));
            shiftObAlongX -= 0.2 * Math.signum(shiftObAlongX);
        }
//        x -= speed/Main.fps;
        setX(x - speed/Main.fps);
    }

    public float myFunction(float tempT) {
        float tempY = (float) Math.sin(tempT);
        tempY *= amplitude;
        tempY += photonYShift;
        return tempY;
    }
    public void setX(float newX) {
//        for(GOPoint point : path) {
//            point.x += newX - x;
//        }
        x = newX;
    }

    @Override
    public void update() {
        try {
            move();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        sx = defaultSx * (x+50)/150;

        if(die) {
            float sizeStep = 10f / Main.fps;
            float opacityStep = - 1f / Main.fps * 2;
            sx += sizeStep;
//            sx = defaultSx + defaultSx / sx / 4f * defaultSx;
            sy = sx;
            opacity += opacityStep;
            if(opacity == 0) {
                Game.obstacles.remove(this);
            }
        }
        for(GOPoint point : path) {
            point.update();
        }
    }

    @Override
    public void render() {
        if(x -sx/2 <= 100 && x + sx/2 >= 0 && y -sy/2 <= 100*Main.ratio && y + sy/2 >= 0 ) {
            Draw.draw(figure, x, y, sx, sy, rotate, color, opacity);
            for(GOPoint point : path) {
                point.render();
            }
        }
    }
    @Override
    public void collision() {
//        defaultColor = 1;
//        color = defaultColor;
        die = true;
    }*/
}
