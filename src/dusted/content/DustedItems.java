package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedItems {
    public static Item plastel, telonate;

    public static void load() {
        plastel = new Item("plastel", Color.valueOf("e9e49f")) {{
            alwaysUnlocked = true;
        }};
        telonate = new Item("telonate") {{
            hardness = 4;
        }};
    }
}
