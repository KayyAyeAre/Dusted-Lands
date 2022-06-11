package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedItems {
    public static Item plastel, arsenic, pyresin, volMembrane, telonate;

    public static void load() {
        plastel = new Item("plastel", Color.valueOf("e9e49f")) {{
            alwaysUnlocked = true;
        }};

        arsenic = new Item("arsenic", Color.valueOf("cd8594")) {{
            alwaysUnlocked = true;
        }};

        pyresin = new Item("pyresin", Color.valueOf("e997b1"));

        //volMembrane = new Item("vol-membrane");

        telonate = new Item("telonate", Color.valueOf("9b77bc"));
    }
}
