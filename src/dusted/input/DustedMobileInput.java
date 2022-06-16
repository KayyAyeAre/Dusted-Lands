package dusted.input;

import arc.util.*;
import dusted.entities.abilities.*;
import mindustry.*;
import mindustry.gen.*;

public class DustedMobileInput implements CustomInput {
    @Override
    public void update() {
        if (Vars.state.isPlaying()) {
            Structs.each(a -> {
                if (Vars.player.shooting) {
                    if (a instanceof BounceAbility bounceAbility) bounceAbility.bounce(Vars.player.unit());
                    if (a instanceof  QuakeAbility quakeAbility) quakeAbility.quake();
                }

                if (a instanceof RevolvingOrbAbility orbs && Groups.bullet.intersect(Vars.player.unit().x - (orbs.radius * 2), Vars.player.unit().y - (orbs.radius * 2), orbs.radius * 4, orbs.radius * 4).contains(b -> b.team.isEnemy(Vars.player.unit().team))) {
                    orbs.summon();
                }
            }, Vars.player.unit().abilities);
        }
    }
}
