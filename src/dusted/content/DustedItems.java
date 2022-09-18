package dusted.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;

import static mindustry.content.Items.*;

public class DustedItems {
    public static Item zircon, arsenic, antimony, platinum, rockwool, telonate;

    //TODO am i supposed to do this?
    public static final Seq<Item> krakaiItems = new Seq<>();

    public static void load() {
        zircon = new Item("zircon", Color.valueOf("fff2aa")) {{
            alwaysUnlocked = true;
            cost = 0.5f;
        }};

        arsenic = new Item("arsenic", Color.valueOf("cd8594")) {{
            alwaysUnlocked = true;
        }};

        antimony = new Item("antimony", Color.valueOf("f1b093")) {{
            hardness = 4;
        }};

        platinum = new Item("platinum", Color.valueOf("c0c7ff")) {{
            hardness = 5;
        }};

        rockwool = new Item("rockwool", Color.valueOf("85889b"));

        //TODO resprite
        telonate = new Item("telonate", Color.valueOf("9b77bc")) {{
            charge = 0.3f;
            radioactivity = 0.2f;
            cost = 1.25f;
        }};

        krakaiItems.addAll(
                zircon, arsenic, antimony, sand, silicon, metaglass, platinum, rockwool, telonate
        );
    }
}
