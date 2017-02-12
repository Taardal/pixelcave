package no.taardal.blossom.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.keyboard.KeyboardListener;

public class InputModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Keyboard.class).to(KeyboardListener.class).asEagerSingleton();
    }

}
