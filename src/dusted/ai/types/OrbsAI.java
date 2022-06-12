package dusted.ai.types;

import dusted.entities.abilities.*;
import mindustry.*;
import mindustry.ai.types.*;
import mindustry.gen.*;

public class OrbsAI extends GroundAI {
    @Override
    public void updateUnit() {
        super.updateUnit();

        unit.abilities.each(a -> a instanceof RevolvingOrbAbility, a -> {
            RevolvingOrbAbility orbs = (RevolvingOrbAbility) a;
            if (Groups.bullet.intersect(unit.x - (orbs.radius * 2), unit.y - (orbs.radius * 2), orbs.radius * 4, orbs.radius * 4).contains(b -> b.team.isEnemy(Vars.player.unit().team))) {
                orbs.summon();
            }
        });
    }
}
