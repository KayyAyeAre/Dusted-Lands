package dusted.content;

import arc.graphics.*;
import dusted.type.*;
import mindustry.content.*;
import mindustry.type.*;

public class DustedStatusEffects {
    public static StatusEffect deteriorating, high;

    public static void load() {
        //sprite by sh1p :D
        deteriorating = new StatusEffect("deteriorating") {{
            color = Color.valueOf("6d726b");
            damage = 0.26f;
            transitionDamage = 16f;

            init(() -> {
                affinity(StatusEffects.burning, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                });
            });
        }};

        //why do i do these sorts of things
        high = new StatusEffect("high") {{
            color = Color.white;
            damage = 0.1f;
            speedMultiplier = 1.2f;
            reloadMultiplier = 1.4f;
            healthMultiplier = 0.6f;

            effect = DustedFx.high;
        }};
    }
}
