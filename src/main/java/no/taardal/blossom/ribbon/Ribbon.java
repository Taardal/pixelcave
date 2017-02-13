package no.taardal.blossom.ribbon;

import no.taardal.blossom.camera.Camera;

import java.util.List;

public class Ribbon {

    private List<RibbonItem> ribbonItems;
    private double x;
    private double y;

    public Ribbon(List<RibbonItem> ribbonItems) {
        this.ribbonItems = ribbonItems;
//        Collections.reverse(this.ribbonItems);
    }

    public List<RibbonItem> getRibbonItems() {
        return ribbonItems;
    }

    public int getWidth() {
        return ribbonItems.get(0).getWidth();
    }

    public int getHeight() {
        return ribbonItems.get(0).getHeight();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void update(double x, double y) {
        this.x = x;
        this.y = y;
        double velocityX = 0.1;
        double velocityY = 0.1;
        for (RibbonItem ribbonItem : ribbonItems) {
            ribbonItem.update(x, y, velocityX, velocityY);
            velocityX += 0.1;
//            velocityY += 0.1;
        }
    }

    public void draw(Camera camera) {
        for (int i = 0; i < ribbonItems.size(); i++) {
            ribbonItems.get(i).draw(camera);
        }
    }

}
