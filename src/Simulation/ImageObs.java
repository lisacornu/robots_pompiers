package Simulation;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;

public class ImageObs implements ImageObserver {
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if((infoflags & HEIGHT) !=0){
            System.out.println("Image height = " + height );
        }
        if((infoflags & WIDTH) !=0){
            System.out.println("Image width = " + width );
        }
        if((infoflags & FRAMEBITS) != 0){
            System.out.println("Another frame finished.");
        }
        if((infoflags & SOMEBITS) != 0){
            System.out.println("Image section :" + new Rectangle( x, y, width, height ) );
        }
        if((infoflags & ALLBITS) != 0){
            System.out.println("Image finished");
            return false;
        }
        if((infoflags & ABORT) != 0){
            System.out.println("Image aborted");
            return false;
        }
        return true;

    }
}
