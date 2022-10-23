package dusted.ai.types;

import arc.util.*;
import dusted.entities.abilities.*;
import mindustry.*;
import mindustry.ai.types.*;
import mindustry.gen.*;

//TODO this ai could just be moved into the ability itself
public class OrbsAI extends GroundAI {
    @Override
    public void updateUnit() {
        super.updateUnit();
        Structs.each(a -> {
            if (a instanceof RevolvingOrbAbility orbs && Groups.bullet.intersect(unit.x - (orbs.radius * 2), unit.y - (orbs.radius * 2), orbs.radius * 4, orbs.radius * 4).contains(b -> b.team.isEnemy(Vars.player.unit().team))) {
                orbs.summon();
            }
        }, unit.abilities);
    }
}
