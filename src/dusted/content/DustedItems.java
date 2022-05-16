package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedItems {
    //TODO more than half of these are unobtainable
    public static Item plastel, shirrote, telonate;

    public static void load() {
        plastel = new Item("plastel", Color.valueOf("e9e49f")) {{
            alwaysUnlocked = true;
        }};

        //shirrote = new Item("shirrote");

        //telonate = new Item("telonate");
    }
}
