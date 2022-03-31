package dusted.content;

import mindustry.type.*;

public class DustedStatusEffects {
    public static StatusEffect dusted, sprayed;

    public static void load() {
        //TODO
        dusted = new StatusEffect("dusted");

        sprayed = new StatusEffect("sprayed") {{
            damage = 0.36f;
        }};
    }
}
