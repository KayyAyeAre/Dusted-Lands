package dusted.content;

import arc.graphics.*;
import dusted.type.*;

public class DustedPowders {
    public static Powder titaniumPowder, quartzDust, cavnenDust, pyreol, cafraegen, cocaine;

    public static void load() {
        titaniumPowder = new Powder("titanium-powder", Color.valueOf("8da1e3")) {{
            temperature = 0.2f;
            flammability = 0.4f;
        }};

        quartzDust = new Powder("quartz-dust", Color.valueOf("ffe8ee")) {{
            temperature = 0.1f;
        }};

        cavnenDust = new Powder("cavnen-dust", Color.valueOf("6d726b")) {{
            flammability = 0.3f;
            density = 0.2f;
        }};

        pyreol = new Powder("pyreol") {{
            flammability =  0.6f;
            temperature = 0.7f;
        }};

        cafraegen = new Powder("cafraegen", Color.valueOf("9ef4ef")) {{
            temperature = 0.3f;
            density = 0.4f;
        }};

        //joke powder
        cocaine = new Powder("cocaine", Color.white);
    }
}
