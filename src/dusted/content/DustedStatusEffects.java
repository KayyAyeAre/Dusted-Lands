package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedStatusEffects {
    public static StatusEffect deteriorating, shirroteCorrosion, sprayed;

    public static void load() {
        sprayed = new StatusEffect("sprayed") {{
            damage = 0.36f;
        }};

        deteriorating = new StatusEffect("deteriorating") {{
            damage = 0.56f;
            transitionDamage = 28f;

            init(() -> {
                affinity(shirroteCorrosion, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                });
            });
        }};

        shirroteCorrosion = new StatusEffect("shirrote-corrosion") {{
            color = Color.valueOf("78ffdb");
            permanent = true;
            healthMultiplier = 0.6f;
            damageMultiplier = 1.3f;
            speedMultiplier = 1.1f;
        }};
    }
}
