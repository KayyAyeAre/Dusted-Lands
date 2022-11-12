package dusted.input;

import arc.*;
import arc.input.*;
import arc.util.*;
import dusted.entities.abilities.*;
import mindustry.*;

public class DustedDesktopInput implements CustomInput {
    @Override
    public void update() {
        if (Vars.state.isPlaying()) {
            Structs.each(a -> {
                if (Core.scene.hasKeyboard()) return;
                if (Core.input.keyTap(KeyCode.b) && a instanceof BounceAbility bounceAbility) bounceAbility.bounce(Vars.player.unit());
                if (Core.input.keyTap(KeyCode.r) && a instanceof RevolvingOrbAbility orbsAbility) orbsAbility.summon();
            }, Vars.player.unit().abilities);
        }
    }
}
