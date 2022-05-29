package dusted.ai.types;

import dusted.entities.abilities.*;
import mindustry.ai.types.*;
import mindustry.gen.*;

public class OrbsAI extends GroundAI {
    @Override
    public void updateUnit() {
        super.updateUnit();

        if (unit.abilities.contains(a -> a instanceof RevolvingOrbAbility)) {
            RevolvingOrbAbility ability = (RevolvingOrbAbility) unit.abilities.find(a -> a instanceof RevolvingOrbAbility);
            if (Groups.bullet.intersect(unit.x - (ability.radius * 2), unit.y - (ability.radius * 2), ability.radius * 4, ability.radius * 4).contains(b -> b.team.isEnemy(unit.team))) {
                ability.summon();
            }
        }
    }
}
