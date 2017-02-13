package no.taardal.blossom.ribbon;

import no.taardal.blossom.camera.Camera;

import java.awt.image.BufferedImage;

public class RibbonItem {

    private BufferedImage bufferedImage;
    private double x;
    private double y;

    public RibbonItem(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }


    public void update(double x, double y, double velocityX, double velocityY) {
        this.x = (x * velocityX) % bufferedImage.getWidth();
//        this.y = (y * velocityY) % bufferedImage.getHeight();
        this.y = y;
    }

    public void draw(Camera camera) {
        camera.drawImage(bufferedImage, (int) x, (int) y);

//        if (x < 0) {
//            camera.drawImage(bufferedImage, (int) x + (int) camera.getWidth(), (int) y);
//        }
//        if (x > 0) {
//            camera.drawImage(bufferedImage, (int) x - (int) camera.getWidth(), (int) y);
//        }
//        if (y < 0) {
//            camera.drawImage(bufferedImage, (int) x, (int) y + (int) camera.getHeight());
//        }
//        if (y > 0) {
//            camera.drawImage(bufferedImage, (int) x, (int) y - (int) camera.getHeight());
//        }

        if (x < 0) {
            camera.drawImage(bufferedImage, (int) x + bufferedImage.getWidth(), (int) y);
        }
        if (x > 0) {
            camera.drawImage(bufferedImage, (int) x - bufferedImage.getWidth(), (int) y);
        }
//        if (y < 0) {
//            camera.drawImage(bufferedImage, (int) x, (int) y + bufferedImage.getHeight());
//        }
//        if (y > 0) {
//            camera.drawImage(bufferedImage, (int) x, (int) y - bufferedImage.getHeight());
//        }
    }

}
