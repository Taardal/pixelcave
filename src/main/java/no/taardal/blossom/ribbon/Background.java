package no.taardal.blossom.ribbon;

import no.taardal.blossom.camera.Camera;

import java.awt.image.BufferedImage;

public class Background {

    private BufferedImage bufferedImage;
    private double x;
    private double y;

    public Background(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void update(double x, double y, double velocityX, double velocityY) {
        this.x = (x * velocityX) % bufferedImage.getWidth();
        this.y = (y * velocityY) % bufferedImage.getHeight();
    }

    public void draw(Camera camera) {
        camera.drawImage1(bufferedImage, (int) x, (int) y);

        if (x < 0) {
            camera.drawImage1(bufferedImage, (int) x + (int) camera.getWidth(), (int) y);
        }
        if (x > 0) {
            camera.drawImage1(bufferedImage, (int) x - (int) camera.getWidth(), (int) y);
        }
        if (y < 0) {
            camera.drawImage1(bufferedImage, (int) x, (int) y + (int) camera.getHeight());
        }
        if (y > 0) {
            camera.drawImage1(bufferedImage, (int) x, (int) y - (int) camera.getHeight());
        }

//        if (x < 0) {
//            camera.drawImage1(bufferedImage, (int) x + bufferedImage.getWidth(), (int) y);
//        }
//        if (x > 0) {
//            camera.drawImage1(bufferedImage, (int) x - bufferedImage.getWidth(), (int) y);
//        }
//        if (y < 0) {
//            camera.drawImage1(bufferedImage, (int) x, (int) y + bufferedImage.getHeight());
//        }
//        if (y > 0) {
//            camera.drawImage1(bufferedImage, (int) x, (int) y - bufferedImage.getHeight());
//        }
    }

}
