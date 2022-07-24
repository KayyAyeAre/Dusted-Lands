package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedItems {
    public static Item zircon, arsenic, platinum, pyresin, volMembrane, telonate;

    public static void load() {
        zircon = new Item("zircon", Color.valueOf("fff2aa")) {{
            alwaysUnlocked = true;
            cost = 0.5f;
        }};

        arsenic = new Item("arsenic", Color.valueOf("cd8594")) {{
            alwaysUnlocked = true;
        }};

        platinum = new Item("platinum", Color.valueOf("c0c7ff")) {{
            hardness = 3;
        }};

        pyresin = new Item("pyresin", Color.valueOf("e997b1")) {{
            flammability = 0.1f;
            cost = 1.1f;
        }};

        telonate = new Item("telonate", Color.valueOf("9b77bc")) {{
            charge = 0.3f;
            radioactivity = 0.2f;
            cost = 1.25f;
        }};
    }
}
