package no.taardal.blossom.level;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.ribbon.Ribbon;

public interface Level {

    void update(Keyboard keyboard);

    void draw(Camera camera);

    void setRibbon(Ribbon ribbon);
}
