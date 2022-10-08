package dusted.content;

import arc.graphics.*;
import arc.math.*;
import dusted.graphics.*;
import dusted.type.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.type.*;

public class DustedStatusEffects {
    public static StatusEffect deteriorating, blazing, high;

    public static void load() {
        //sprite by sh1p :D
        deteriorating = new StatusEffect("deteriorating") {{
            color = DustedPal.decay;
            damage = 0.26f;
            transitionDamage = 16f;
            effect = DustedFx.deteriorating;

            init(() -> {
                affinity(StatusEffects.burning, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);

                    Units.nearby(unit.team, unit.x, unit.y, 16f, u -> {
                        u.damagePierce(transitionDamage);
                    });
                });
                affinity(blazing, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);

                    Units.nearby(unit.team, unit.x, unit.y, 16f, u -> {
                        u.damagePierce(transitionDamage);
                    });
                });
            });
        }};

        //TODO make orchar bullets use burning and quartz bullets use blazing
        blazing = new StatusEffect("blazing") {{
            damage = 0.3f;
            effect = DustedFx.blazing;
            transitionDamage = 12f;

            init(() -> {
                opposite(StatusEffects.wet, StatusEffects.freezing);

                affinity(StatusEffects.tarred, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    Fx.burning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(blazing, Math.min(time + result.time, 600f));
                });

                affinity(StatusEffects.burning, (unit, result, time) -> {
                    result.set(blazing, Math.min(time + result.time, 600f));
                });

                affinity(StatusEffects.blasted, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    Fx.explosion.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    Units.nearby(unit.team, unit.x, unit.y, 16f, u -> {
                        u.damagePierce(transitionDamage);
                    });
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
