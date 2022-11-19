package dusted.content;

import arc.graphics.*;
import dusted.type.*;

public class DustedPowders {
    public static Powder quartzDust, orchar, sulfur, niter, gunpowder, arcinder, cocaine;

    public static void load() {
        //TODO maybe rename this to silica
        quartzDust = new Powder("quartz-dust", Color.valueOf("ffe8ee"), Color.valueOf("fff4f7")) {{
            explosiveness = 0.05f;
        }};

        orchar = new Powder("orchar", Color.valueOf("7d3624"), Color.valueOf("a15d3f")) {{
            flammability = 0.7f;
            temperature = 0.7f;
        }};

        sulfur = new Powder("sulfur", Color.valueOf("f7e89c"), Color.valueOf("f9ffcf")) {{
            flammability = 0.2f;
            explosiveness = 0.6f;
        }};

        niter = new Powder("niter", Color.valueOf("d5d1c2"), Color.valueOf("fefff6")) {{
            flammability = 0.3f;
        }};

        gunpowder = new Powder("gunpowder", Color.valueOf("6f6460"), Color.valueOf("938d88")) {{
            flammability = 0.4f;
            explosiveness = 1f;
        }};

        arcinder = new Powder("arcinder", Color.valueOf("e65b5b"), Color.valueOf("fff085").a(0.7f)) {{
            flammability = 1.4f;
            temperature = 0.8f;
            explosiveness = 0.3f;
        }};

        cocaine = new Powder("cocaine", Color.valueOf("e6e6e6"), Color.white.cpy()) {{
            effect = DustedStatusEffects.high;
        }};
    }
}
