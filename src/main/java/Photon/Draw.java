package Photon;

import org.newdawn.slick.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;



public class Draw {

    public static float xshift = 0;
    public static float yshift = 0;
    private static final float Pi = 180f;

    private static boolean antiAlias = true;
    static Font awtFont1 = new Font("Times New Roman", Font.BOLD, 24);
    static Font awtFont2 = new Font("Times New Roman", Font.BOLD, 30);
    static Font dsCrystal = new Font("DS Crystal", Font.BOLD, 76);
    private static TrueTypeFont font1_1 = new TrueTypeFont(awtFont1, antiAlias);
    private static TrueTypeFont font1_2 = new TrueTypeFont(awtFont2, antiAlias);
    private static TrueTypeFont font2 = new TrueTypeFont(awtFont2, antiAlias);
    private static TrueTypeFont font3 = new TrueTypeFont(dsCrystal, antiAlias);

    private static final String CONTENT_PATH = "content/";
    private static final String TEXTURE_PATH = CONTENT_PATH+"images/";
    private static final String SOUND_PATH = CONTENT_PATH+"sound/";
    private static final String FONTS_PATH = CONTENT_PATH+"fonts/";


    private static Texture fon1;

//    public static long curTime;

    public static void draw(DrawFigure figure, float x, float y, float sx, float sy, float rotate, int color, float opacity) {

        glDisable(GL_TEXTURE_2D);


        x -= xshift;
        y -= yshift;
        x *= Main.em;
        y *= Main.em;

        sx *= Main.em;
        sy *= Main.em;

        switch(figure) {
            case RECT: rect(x, y, sx, sy, rotate, color, opacity);
                break;
            case TRIANGLE: triangle(x, y, sx, sy, rotate, color, opacity);
                break;
            case CIRCLE: circle(x, y, sx, sy, color, opacity);
                break;
            case FON: fon(x, y, sx, sy, rotate);
                break;
            default:
                break;
        }
    }



    public static void draw(DrawFigure figure, float x, float y, float sx, float sy) {
        draw(figure, x, y, sx, sy, 0, 0, 1);
    }
    public static void rect(float x, float y, float sx, float sy, float rotate, float opacity) {
        rect(x, y, sx, sy, rotate, 0);
    }
    public static void rect(float x, float y, float sx, float sy, float rotate, int color, float opacity) {
        glPushMatrix();
        {
            chooseColor(color, opacity);
//            glColor3f(0.5f,0.5f,0.5f);
//            glColor4f(0.8f, 0.2f, 0.2f, opacity);
            glTranslatef(x, y, 0);
            glRotatef(-rotate, 0, 0, 1);
            glEnable(GL_POLYGON_SMOOTH);
            glBegin(GL_QUADS);
            {
                glVertex2f(-sx / 2, -sy / 2);
                glVertex2f(-sx/2, sy/2);
                glVertex2f(sx / 2, sy / 2);
                glVertex2f(sx/2,-sy/2);
            }
            glEnd();
        }
        glPopMatrix();
    }
    public static void triangle(float x, float y, float sx, float sy, float rotate, int color, float opacity) {
        glPushMatrix();
        {
            chooseColor(color, opacity);
//            glColor3f(0.5f,0.5f,0.5f);
            glTranslatef(x, y, 0);
            glRotatef(-rotate, 0, 0, 1);
            glEnable(GL_POLYGON_SMOOTH);
            glBegin(GL_POLYGON);
            {
                glVertex2f(-sx / 2, -sy / 2);
                glVertex2f(-sx/2, sy/2);
                glVertex2f((float) ((Math.pow(3, 0.5f)-1)/2*sx), 0);
            }
            glEnd();
        }
        glPopMatrix();
    }
    public static void fon(float x, float y, float sx, float sy, float rotate) {
        glPushMatrix();
        {
//            glEnable(GL_TEXTURE_2D);
            glDisable(GL_TEXTURE_2D);
            glColor3f(0.215f, 0.155f, 0.430f);//55, 43, 109
            glTranslatef(x, y, 0);
            sx*=0.9;
            glRotatef(-rotate, 0, 0, 1);
            glDisable(GL_POLYGON_SMOOTH);
            glBegin(GL_QUADS);
            {
                glVertex2f(-sx / 2, -sy / 2);
                glVertex2f(-sx / 2, sy / 2);
                glVertex2f(sx / 2, sy / 2);
                glVertex2f(sx/2,-sy/2);
            }
            glEnd();
        }
        glPopMatrix();
    }
    public static void gameInterface() {
        glEnable(GL_TEXTURE_2D);
        font1_2.drawString((10)*Main.em, 95*Main.em*Main.ratio,
                "Level: " + String.valueOf(Game.level) +
                "        Time: " + Game.integerTime +
                "        Speed: " + (int)(Game.moveOnStep*100) +
                "        Factor: " + (int)(Main.game.player.factor) +
                "        Gravity: " + (Main.game.blackHole.gravitationPower) +
                "       " + (Main.game.player.prism) + " : " + (Main.game.player.obstacles)
                , org.newdawn.slick.Color.white);
        for(int i = 0; i < Game.players.size(); i++) {
            font1_1.drawString((10 + (100 / Game.players.size() * i))*Main.em, 5*Main.em*Main.ratio, Game.players.get(i).name, org.newdawn.slick.Color.white);
            font1_1.drawString((10 + (100 / Game.players.size() * i))*Main.em, 10*Main.em*Main.ratio, "Score: " + String.valueOf(Game.players.get(i).score), org.newdawn.slick.Color.white);
            font1_1.drawString((10 + (100 / Game.players.size() * i))*Main.em, 15*Main.em*Main.ratio, "Score: " + String.valueOf(Game.players.get(i).score2), org.newdawn.slick.Color.white);
            }
    }
    public static void fon2(float x, float y, float sx, float sy, float rotate) {
        glPushMatrix();
        {
            glEnable(GL_TEXTURE_2D);
//            glBindTexture(GL_TEXTURE_2D, 0);
            glColor3f(0.5f,0.5f,0.5f);
            glTranslatef(x, y, 0);
            sx*=0.9;
            glRotatef(-rotate, 0, 0, 1);
            fon1.bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(0, 0);
                glVertex2f(-sx / 2, -sy / 2);
                glTexCoord2f(0, 1);
                glVertex2f(-sx / 2, sy / 2);
                glTexCoord2f(1, 1);
                glVertex2f(sx / 2, sy / 2);
                glTexCoord2f(1, 0);
                glVertex2f(sx/2,-sy/2);
            }
            glEnd();
        }
        glPopMatrix();
//        fon1.release();
    }


    public static void circle(float x, float y, float sx, float sy, int color, float opacity) {
        glPushMatrix();
        {
            chooseColor(color, opacity);
            glTranslatef(x, y, 0);
            if(sx < 200) {
                glEnable(GL_POINT_SMOOTH);
                glPointSize(sx);
                glBegin(GL_POINTS);
                    glVertex2d(0, 0); // первая точка
                glEnd();
            }
            else {
//            glHint( GL_LINE_SMOOTH_HINT, GL_NICEST );
//            glEnable( GL_LINE_SMOOTH );
//            glEnable( GL_BLEND );
//            glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
//            glEnable(GL_POLYGON_SMOOTH);
                glBegin(GL_POLYGON);
                {
                    int amountPoints = (int) (20 * sx);
                    for (int i = 0; i <= amountPoints; i++) {
                        glVertex2d(sx / 2 * Math.cos((float) i / amountPoints * 2 * Math.PI), sy / 2 * Math.sin((float) i / amountPoints * 2 * Math.PI));
                    }
                }
                glEnd();
            }
        }
        glPopMatrix();
    }

    public static void chooseColor(int color, float opacity) {
        switch(color) {
            case 0:
                glColor4f(0.8f, 0.8f, 0.8f, opacity);
                break;
            case 1:
                glColor4f(1.0f, 0.0f, 0.0f, opacity);
                glColor4f(0.733f, 0.223f, 0.168f, opacity);
                break;
            case 2:
                glColor4f(0.478f, 0.737f, 0.345f, opacity);
                break;
            case 3:
                glColor4f(0.247f, 0.494f, 1.0f, opacity);
                break;
            case 4:
//                glColor4f(1.0f, 0.0f, 0.0f, opacity);
                glColor4f(0.9f, 0.2f, 0.2f, opacity);
                break;
            case 5:
                glColor4f(0.0f, 1.0f, 0.0f, opacity);
                break;
            case 6:
                glColor4f(0f, 0.0f, 1.0f, opacity);
                break;
            case 7:
                glColor4f(0f, 0.0f, 0.0f, opacity);
                break;
        }
    }
    public static void init() {
        try {
            fon1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(TEXTURE_PATH + "fon/fon1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
