package dusted.content;

import arc.graphics.*;
import dusted.type.*;

public class Powders {
    public static Powder titaniumPowder, quartzDust, cavnenDust;

    public static void load() {
        titaniumPowder = new Powder("titanium-powder", Color.valueOf("8da1e3"));
        quartzDust = new Powder("quartz-dust", Color.valueOf("ffe8ee"));
        cavnenDust = new Powder("cavnen-dust", Color.valueOf("6d726b"));
    }
}
