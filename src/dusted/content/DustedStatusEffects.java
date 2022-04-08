package dusted.content;

import mindustry.type.*;

public class DustedStatusEffects {
    public static StatusEffect deteriorating, sprayed;

    public static void load() {
        sprayed = new StatusEffect("sprayed") {{
            damage = 0.36f;
        }};

        deteriorating = new StatusEffect("deteriorating") {{
            damage = 0.56f;
        }};
    }
}
