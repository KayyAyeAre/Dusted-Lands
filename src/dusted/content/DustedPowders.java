package dusted.content;

import arc.graphics.*;
import dusted.type.*;

public class DustedPowders {
    public static Powder titaniumPowder, quartzDust, cavnenDust, pyreol, cafraegen, cocaine;

    public static void load() {
        titaniumPowder = new Powder("titanium-powder", Color.valueOf("8da1e3")) {{
            temperature = 0.1f;
        }};

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

        cafraegen = new Powder("cafraegen", Color.valueOf("9ef4ef")) {{
            temperature = 0.6f;
            flammability = 0.4f;
            density = 0.4f;
        }};

        cocaine = new Powder("cocaine", Color.white);
    }
}
