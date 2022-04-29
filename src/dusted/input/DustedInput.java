package dusted.input;

import arc.*;
import arc.input.*;
import dusted.entities.units.*;
import mindustry.*;

public class DustedInput implements ApplicationListener {
    @Override
    public void update() {
        if (Vars.state.isPlaying()) {
            if (Vars.player.unit() instanceof BouncingUnitEntity bounce && Core.input.keyTap(KeyCode.k) && !Core.scene.hasKeyboard()) {
                bounce.bounce();
            }
        }
    }
}
