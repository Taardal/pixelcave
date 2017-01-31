package no.taardal.blossom.view;


import no.taardal.blossom.input.Keyboard;

public class Camera extends Viewport {

    public Camera(int width, int height) {
        super(width, height);
    }

    public int getTop() {
        return yOffset;
    }

    public int getLeft() {
        return xOffset;
    }

    public int getRight() {
        return xOffset + getWidth();
    }

    public int getBottom() {
        return yOffset + getHeight();
    }

    public void update(Keyboard keyboard) {
        if (keyboard.isUp()) {
            yOffset--;
        }
        if (keyboard.isLeft()) {
            xOffset--;
        }
        if (keyboard.isRight()) {
            xOffset++;
        }
        if (keyboard.isDown()) {
            yOffset++;
        }
    }
}
