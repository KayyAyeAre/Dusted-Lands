package dusted.content;

import arc.graphics.*;
import dusted.type.*;

public class DustedPowders {
    public static Powder quartzDust, pyreol, sulfur, cocaine;

    public static void load() {
        quartzDust = new Powder("quartz-dust", Color.valueOf("ffe8ee")) {{
            temperature = 0.5f;
        }};

        pyreol = new Powder("pyreol", Color.valueOf("ffa1ab")) {{
            flammability = 1f;
            temperature = 0.7f;
        }};

        sulfur = new Powder("sulfur", Color.valueOf("f7e89c")) {{
            flammability = 0.4f;
            explosiveness = 0.6f;
        }};

        cocaine = new Powder("cocaine", Color.valueOf("e6e6e6")) {{
            effect = DustedStatusEffects.high;
        }};
    }
}
