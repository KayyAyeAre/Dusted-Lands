package dusted.world.blocks.production;

import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;

//literally just wallcrafter with genericcrafter drawers
public class DrawerWallCrafter extends WallCrafter {
    public DrawBlock drawer = new DrawDefault();

    public DrawerWallCrafter(String name) {
        super(name);
    }

    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawDefaultPlanRegion(plan, list);
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
    }

    public class DrawerWallCrafterBuild extends WallCrafterBuild {
        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }
    }
}
