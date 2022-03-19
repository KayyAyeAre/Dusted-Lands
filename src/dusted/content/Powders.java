package dusted.content;

import arc.graphics.*;
import dusted.type.*;

public class Powders {
    public static Powder titaniumPowder;

    public static void load() {
        titaniumPowder = new Powder("titanium-powder", Color.valueOf("8da1e3"));
    }
}
