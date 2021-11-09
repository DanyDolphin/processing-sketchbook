/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingsketchbook;
import java.util.Random;
import processing.core.PApplet;

/**
 *
 * @author dany
 */
public class RandomDashes extends PApplet {
    final Random random = new Random();
    final int DASH_COUNT = 100;
    final int BACKGROUND_ALFA = 20;
    final int ROTATION_INCREMENT = 1;
    final int FRAMERATE = 60;

    RandomDash[] dashes;
    int currentDashCount;
    int currentRotation;


    public void settings() {
        size(600, 600);
    }

    public void setup() {
        frameRate(FRAMERATE);
        background(224, 224, 224);
        currentDashCount = 0;
        currentRotation = 0;
        dashes = new RandomDash[DASH_COUNT];
        for (int i = 0; i < DASH_COUNT; i++) {
            dashes[i] = new RandomDash();
        }
    }

    public void draw() {
        rotate(currentRotation++);
        fill(224, 224, 224, BACKGROUND_ALFA);
        stroke(255);
        rect(0, 0, width, height);

        stroke(0);
        for (int i = 0; i < currentDashCount; i++) {
            dashes[i].fall();
        }

        if (currentDashCount < DASH_COUNT - 1) {
            currentDashCount++;
        }

    }

    class RandomDash {
        static final float VELOCITY = 30;
        float x;
        float y;

        RandomDash() {
            x = random.nextInt(width);
            y = 0;
        }

        public void fall() {
            RandomDashes.this.line(x, y, x, y + VELOCITY);
            y += VELOCITY;
            if (y > height) {
                y = 0;
                x = random.nextInt(width);
            }
        }
    }

    public static void main(String[] args) {
        String[] appletArgs = new String[] { "processingsketchbook.RandomDashes" };
        PApplet.main(appletArgs);
    }
}
