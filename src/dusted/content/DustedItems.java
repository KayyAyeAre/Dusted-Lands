package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedItems {
    public static Item zircon, arsenic, antimony, platinum, rockwool, pyresin, telonate;

    public static void load() {
        zircon = new Item("zircon", Color.valueOf("fff2aa")) {{
            alwaysUnlocked = true;
            cost = 0.5f;
        }};

        arsenic = new Item("arsenic", Color.valueOf("cd8594")) {{
            alwaysUnlocked = true;
        }};

        antimony = new Item("antimony", Color.valueOf("f1b093")) {{
            hardness = 2;
        }};

        platinum = new Item("platinum", Color.valueOf("c0c7ff")) {{
            hardness = 3;
        }};

        rockwool = new Item("rockwool", Color.valueOf("85889b"));

        pyresin = new Item("pyresin", Color.valueOf("e997b1")) {{
            flammability = 0.2f;
            explosiveness = 0.1f;
            cost = 1.1f;
        }};

        //TODO resprite
        telonate = new Item("telonate", Color.valueOf("9b77bc")) {{
            charge = 0.3f;
            radioactivity = 0.2f;
            cost = 1.25f;
        }};
    }
}
