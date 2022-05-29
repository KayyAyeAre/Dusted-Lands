package dusted.content;

import arc.graphics.*;
import mindustry.type.*;

public class DustedItems {
    //TODO more than half of these are unobtainable
    public static Item plastel, biotite, pyresin, telonate;

    public static void load() {
        plastel = new Item("plastel", Color.valueOf("e9e49f")) {{
            alwaysUnlocked = true;
        }};

        biotite = new Item("biotite");

        pyresin = new Item("pyresin");

        telonate = new Item("telonate");
    }
}
