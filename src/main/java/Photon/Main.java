package Photon;

import Photon.DataBase.DBWorker;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.Project.gluPerspective;

/**
 * Created by Serega on 03.03.2015.
 */
public class Main {


        public static final String GAME_TITLE = "Photon";
        public static Game game;
        public static DBWorker dbWorker;

        public static final int delay = 40;
        public static final int fps = (int) (1000 / delay);

        public static final float ratio = 0.5f;
        public static final int dWidth = 1600;
        public static final int dHeight = (int) (dWidth * ratio);
        public static final int em = dWidth / 100;


        public static void main(String[] args) {

            initDisplay();
            initGL();
            initDBWorker();
            Draw.init();

            restartGame();

        }


    public static void restartGame() {
            if(game != null)
                game.clear();
            initGame();

            gameLoop();

            cleanUp();
        }

        private static void initDisplay() {

            try {
                Display.setDisplayMode(new DisplayMode(dWidth, dHeight));
//            setDisplayMode(dWidth, dHeight, true);
//            Display.setFullscreen(false);
                Display.create();
                Display.setVSyncEnabled(true);
                Display.setTitle(GAME_TITLE);
//                Display.setIcon();
//            Display.setResizable(false);

                Keyboard.create();
                Mouse.create();
                mouseGrabbed(false);
//                Mouse.setGrabbed(true);
            } catch (LWJGLException e) {
                e.printStackTrace();
            }

        }
    public static void mouseGrabbed(boolean mouseGrabbed) {
        Mouse.setGrabbed(mouseGrabbed);
    }

    private static void initGL2() {

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
//        glOrtho(0, dWidth, dHeight, 0, 1, -1);
        GL11.glOrtho(0, dWidth, dHeight, 0, dWidth, -dWidth);
        glMatrixMode(GL_MODELVIEW);
    }
    private static void initGL3() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(90, (float) dWidth / dHeight, 0.01F, 250.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_ALPHA_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glClearColor(0.0f, 0.0f, 0.002f, 1);

        glEnable(GL_FOG);
        Color fogColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);

        FloatBuffer fogColours = BufferUtils.createFloatBuffer(4);
        fogColours.put(new float[]{fogColor.r, fogColor.g, fogColor.b, fogColor.a});
        glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
        fogColours.flip();
        glFog(GL_FOG_COLOR, fogColours);
        glFogi(GL_FOG_MODE, GL_LINEAR);
        glHint(GL_FOG_HINT, GL_NICEST);
        glFogf(GL_FOG_START, 10.0f);
        glFogf(GL_FOG_END, 250.0f);
        glFogf(GL_FOG_DENSITY, 0.005f);
    }
    private static void initGL4() {
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();

        GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix
        GLU.gluPerspective(45.0f, ((float) width / (float) height), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
        GL11.glLoadIdentity(); // Reset The Modelview Matrix

        GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity(); // Reset The View
        GL11.glTranslatef(-1.5f, 0.0f, -6.0f); // Move Left And Into The Screen

    }
    private static void initGL() {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glClearDepth(1);
        //NEW
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glViewport(0, 0, dWidth, dHeight);
        glMatrixMode(GL_MODELVIEW);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        //GL11.glOrtho(0, dWidth, 0, dHeight, -1, 1);
        GL11.glOrtho(0, dWidth, dHeight, 0, dWidth, -dWidth);
        glMatrixMode(GL_MODELVIEW);
        glClearColor(0.0f, 0.0f, 0.002f, 1);
    }

    private static void initGL5() {

    }

    private static void initDBWorker() {
        dbWorker = new DBWorker();
    }
        private static void initGame() {
            game = new Game();
        }

        private static void gameLoop() {
            while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                getInput();
                update();
                render();
                if (Keyboard.isKeyDown(Keyboard.KEY_R))
                    restartGame();
            }
            cleanUp();
        }

        private static void getInput() {
            game.getInput();
        }

        private static void update() {
            game.update();
        }

        private static void render() {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//            glClear(GL_COLOR_BUFFER_BIT);
            glLoadIdentity();

            game.render();

            Display.update();
            Display.sync(fps);
        }

        public static void cleanUp() {
            Display.destroy();
            Keyboard.destroy();
            Mouse.destroy();
        }


}
