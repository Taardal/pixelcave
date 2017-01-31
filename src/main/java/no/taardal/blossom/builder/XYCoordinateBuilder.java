package no.taardal.blossom.builder;

import no.taardal.blossom.coordinate.XYCoordinate;

public class XYCoordinateBuilder implements Builder<XYCoordinate> {

    private int x;
    private int y;
    private int xPrecision;
    private int yPrecision;

    @Override
    public XYCoordinate build() {
        XYCoordinate xyCoordinate = new XYCoordinate(x, y);
        return xyCoordinate;
    }

    public XYCoordinateBuilder withX(int x) {
        this.x = x;
        return this;
    }

    public XYCoordinateBuilder withY(int y) {
        this.y = y;
        return this;
    }

    public XYCoordinateBuilder withXPrecision(int xPrecision) {
        this.xPrecision = xPrecision;
        return this;
    }

    public XYCoordinateBuilder withYPrecision(int yPrecision) {
        this.yPrecision = yPrecision;
        return this;
    }

}
