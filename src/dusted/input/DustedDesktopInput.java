package dusted.input;

import arc.*;
import arc.input.*;
import dusted.entities.abilities.*;
import mindustry.*;

public class DustedDesktopInput implements CustomInput {
    @Override
    public void update() {
        if (Vars.state.isPlaying()) {
            if (Core.input.keyTap(KeyCode.b) && !Core.scene.hasKeyboard()) Vars.player.unit().abilities.each(a -> a instanceof BounceAbility, a -> ((BounceAbility) a).bounce(Vars.player.unit()));
            if (Core.input.keyTap(KeyCode.q) && !Core.scene.hasKeyboard()) Vars.player.unit().abilities.each(a -> a instanceof QuakeAbility, a -> ((QuakeAbility) a).quake());
            if (Core.input.keyTap(KeyCode.r) && !Core.scene.hasKeyboard()) Vars.player.unit().abilities.each(a -> a instanceof RevolvingOrbAbility, a -> ((RevolvingOrbAbility) a).summon());
        }
    }
}
