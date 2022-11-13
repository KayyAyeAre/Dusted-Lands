package dusted.content;

import arc.graphics.*;
import mindustry.game.*;

public class DustedTeams {
    //TODO
    public static Team reliquiae;

    public static void load() {
        reliquiae = Team.get(46);
        reliquiae.name = "reliquiae";
        reliquiae.color.set(Color.valueOf("87a17d"));

        reliquiae.palette[0] = Color.valueOf("b1bf95");
        reliquiae.palette[1] = reliquiae.color;
        reliquiae.palette[2] = Color.valueOf("3d7243");

        for(int i = 0; i < 3; i++){
            reliquiae.palettei[i] = reliquiae.palette[i].rgba();
        }

        reliquiae.hasPalette = true;
    }
}
