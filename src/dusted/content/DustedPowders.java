package dusted.content;

import arc.graphics.*;
import dusted.type.*;

public class DustedPowders {
    public static Powder quartzDust, cavnenDust, pyreol, catalygen, cocaine;

    public static void load() {
        quartzDust = new Powder("quartz-dust", Color.valueOf("ffe8ee")) {{
            temperature = 0.2f;
        }};

        cavnenDust = new Powder("cavnen-dust", Color.valueOf("6d726b")) {{
            density = 0.2f;
            effect = DustedStatusEffects.deteriorating;
        }};

        pyreol = new Powder("pyreol", Color.valueOf("ffa1ab")) {{
            flammability = 1f;
            temperature = 0.7f;
        }};

        catalygen = new Powder("catalygen", Color.valueOf("9ef4ef")) {{
            temperature = 0.6f;
            flammability = 0.4f;
            density = 0.4f;
        }};

        cocaine = new Powder("cocaine", Color.white) {{
            effect = DustedStatusEffects.high;
        }};
    }
}
