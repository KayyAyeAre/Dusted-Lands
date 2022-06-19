package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedItems {
    public static Item plastel, arsenic, pyresin, volMembrane, ascneta, telonate;

    public static void load() {
        plastel = new Item("plastel", Color.valueOf("e9e49f")) {{
            alwaysUnlocked = true;
        }};

        arsenic = new Item("arsenic", Color.valueOf("cd8594")) {{
            alwaysUnlocked = true;
        }};

        pyresin = new Item("pyresin", Color.valueOf("e997b1")) {{
            flammability = 0.1f;
        }};

        //volMembrane = new Item("vol-membrane");

        telonate = new Item("telonate", Color.valueOf("9b77bc")) {{
            charge = 0.3f;
            radioactivity = 0.2f;
        }};
    }
}
