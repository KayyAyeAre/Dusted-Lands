package dusted.world.meta;

import mindustry.world.meta.*;

import static mindustry.content.Blocks.*;

public class DustedAttribute {
    //used in rockwool production
    public static final Attribute rock = Attribute.add("rock");

    static {
        stoneWall.attributes.set(rock, 1f);
        shaleWall.attributes.set(rock, 0.8f);
        daciteWall.attributes.set(rock, 1f);
        duneWall.attributes.set(rock, 1f);
        rhyoliteWall.attributes.set(rock, 1f);
        yellowStoneWall.attributes.set(rock, 1f);
        ferricStoneWall.attributes.set(rock, 0.6f);
        beryllicStoneWall.attributes.set(rock, 0.8f);
        crystallineStoneWall.attributes.set(rock, 0.8f);
        redStoneWall.attributes.set(rock, 1f);
    }
}
