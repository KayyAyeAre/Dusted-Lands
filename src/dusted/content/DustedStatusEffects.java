package dusted.content;

import mindustry.type.*;

public class DustedStatusEffects {
    public static StatusEffect sprayed;

    public static void load() {
        sprayed = new StatusEffect("sprayed") {{
            damage = 0.36f;
        }};
    }
}
