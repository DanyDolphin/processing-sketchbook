/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingsketchbook.utilities;

import gifAnimation.GifMaker;
import processing.core.PApplet;

/**
 *
 * @author dany
 */
public class GifFactory {
    GifMaker gif;
    public GifFactory(PApplet app, String filename) {
        gif = new GifMaker(app, filename, 100);
        gif.setRepeat(0); // 0 means endless loop
    }

    public void addFrame(int delay_in_frames) {
        gif.setDelay(delay_in_frames);
        gif.addFrame();
    }

    public void save() {
        gif.finish();
    }
}
