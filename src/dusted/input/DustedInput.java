package dusted.input;

import arc.*;
import arc.input.*;
import dusted.entities.abilities.*;
import dusted.entities.units.*;
import mindustry.*;

public class DustedInput implements ApplicationListener {
    @Override
    public void update() {
        //messy code
        if (Vars.state.isPlaying()) {
            if (Core.input.keyTap(KeyCode.b) && !Core.scene.hasKeyboard() && Vars.player.unit().abilities.contains(a -> a instanceof BounceAbility)) {
                ((BounceAbility) Vars.player.unit().abilities.find(a -> a instanceof BounceAbility)).bounce(Vars.player.unit());
            }

            if (Core.input.keyTap(KeyCode.q) && !Core.scene.hasKeyboard() && Vars.player.unit().abilities.contains(a -> a instanceof QuakeAbility)) {
                ((QuakeAbility) Vars.player.unit().abilities.find(a -> a instanceof QuakeAbility)).quake();
            }

            if (Core.input.keyTap(KeyCode.r) && !Core.scene.hasKeyboard() && Vars.player.unit().abilities.contains(a -> a instanceof RevolvingOrbAbility)) {
                ((RevolvingOrbAbility) Vars.player.unit().abilities.find(a -> a instanceof RevolvingOrbAbility)).summon();
            }
        }
    }
}
