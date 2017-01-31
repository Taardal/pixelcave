package no.taardal.blossom.coordinate;

import no.taardal.blossom.builder.XYCoordinateBuilder;

import java.awt.*;

public class XYCoordinate {

    private Point point;
    private Precision precision;
    private XYCoordinateBuilder xyCoordinateBuilder;
    private int xPixelSpacing;
    private int yPixelSpacing;

    public XYCoordinate(XYCoordinate xyCoordinate) {
        this(xyCoordinate.getX(), xyCoordinate.getY());
    }

    public XYCoordinate(int x, int y) {
        this.point = new Point(x, y);
        xyCoordinateBuilder = new XYCoordinateBuilder();
    }

    public XYCoordinate(int x, int y, Precision precision) {
        this(x, y);
        this.precision = precision;
    }

    public int getX() {
        return point.x;
    }

    public void setX(int x) {
        point.x = x;
    }

    public int getY() {
        return point.y;
    }

    public void setY(int y) {
        point.y = y;
    }

    public int getxPixelSpacing() {
        return xPixelSpacing;
    }

    public void setxPixelSpacing(int xPixelSpacing) {
        this.xPixelSpacing = xPixelSpacing;
    }

    public int getyPixelSpacing() {
        return yPixelSpacing;
    }

    public void setyPixelSpacing(int yPixelSpacing) {
        this.yPixelSpacing = yPixelSpacing;
    }

    public void setLocation(int x, int y) {
        point.setLocation(x, y);
    }

    public void setLocation(XYCoordinate xyCoordinate) {
        point.setLocation(xyCoordinate.getX(), xyCoordinate.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof XYCoordinate) {
            XYCoordinate xyCoordinate = (XYCoordinate) obj;
            return getX() == xyCoordinate.getX();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return point.hashCode();
    }

}
