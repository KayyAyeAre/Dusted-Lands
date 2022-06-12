package dusted.input;

import arc.*;
import dusted.entities.abilities.*;
import mindustry.*;
import mindustry.gen.*;

public class DustedMobileInput implements CustomInput {
    @Override
    public void update() {
        if (Vars.player.shooting) {
            Vars.player.unit().abilities.each(a -> a instanceof BounceAbility, a -> ((BounceAbility) a).bounce(Vars.player.unit()));
            Vars.player.unit().abilities.each(a -> a instanceof QuakeAbility, a -> ((QuakeAbility) a).quake());
        }

        Vars.player.unit().abilities.each(a -> a instanceof RevolvingOrbAbility, a -> {
            RevolvingOrbAbility orbs = (RevolvingOrbAbility) a;
            if (Groups.bullet.intersect(Vars.player.unit().x - (orbs.radius * 2), Vars.player.unit().y - (orbs.radius * 2), orbs.radius * 4, orbs.radius * 4).contains(b -> b.team.isEnemy(Vars.player.unit().team))) {
                orbs.summon();
            }
        });
    }
}
