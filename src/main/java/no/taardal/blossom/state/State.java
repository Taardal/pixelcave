package no.taardal.blossom.state;

import no.taardal.blossom.view.Camera;

public interface State {

    void onEntry();
    void update();
    void draw(Camera camera);

}
