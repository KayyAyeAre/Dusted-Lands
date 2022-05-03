package dusted.input;

import arc.*;
import arc.input.*;
import dusted.entities.units.*;
import mindustry.*;

public class DustedInput implements ApplicationListener {
    @Override
    public void update() {
        if (Vars.state.isPlaying()) {
            if (Core.input.keyTap(KeyCode.b) && !Core.scene.hasKeyboard() && Vars.player.unit() instanceof BouncingUnitEntity bounce) {
                bounce.bounce();
            }

            if (Core.input.keyTap(KeyCode.q) && !Core.scene.hasKeyboard() && Vars.player.unit() instanceof QuakeUnitEntity quake) {
                quake.quake();
            }
        }
    }
}
