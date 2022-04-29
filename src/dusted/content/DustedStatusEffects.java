package dusted.content;

import arc.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.type.*;

public class DustedStatusEffects {
    public static StatusEffect deteriorating, sprayed;

    public static void load() {
        sprayed = new StatusEffect("sprayed") {{
            damage = 0.36f;
        }};

        deteriorating = new StatusEffect("deteriorating") {{
            damage = 0.56f;
            Events.on(UnitDestroyEvent.class, e -> {
                if (((UnitEntity) e.unit).statuses.contains(stentry -> stentry.effect == this)) {
                    //gonna replace flare with another type, just placeholder
                    e.unit.type.spawn(e.unit).setType(UnitTypes.flare);
                }
            });
        }};
    }
}
