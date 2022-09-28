package dusted.world.blocks.environment;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import arc.util.noise.*;
import dusted.content.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

//frostscape
public class RiftFloor extends Floor {
    public TextureRegion[] riftRegions, riftGlowRegions;

    public RiftFloor(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();

        riftRegions = new TextureRegion[variants];
        riftGlowRegions = new TextureRegion[variants];

        for (int i = 0; i < variants; i++) {
            riftRegions[i] = Core.atlas.find(name + "-rift" + (i + 1));
            riftGlowRegions[i] = Core.atlas.find(name + "-glow" + (i + 1));
        }
    }

    @Override
    public boolean updateRender(Tile tile) {
        return true;
    }

    @Override
    public void renderUpdate(UpdateRenderState tile) {
        float otime = Time.time / 100f;
        float offset = Simplex.noise2d(0, 9, 0.2f, 0.6f, tile.tile.x + otime * 0.6f, tile.tile.y + otime * -0.4f) +
                Simplex.noise2d(0, 9, 0.2f, 0.6f, tile.tile.x + (otime * 1.1f) * 0.3f, tile.tile.y + (otime * 1.1f) * 0.1f) * 180f;

        if ((tile.data += Time.delta) >= 180f + offset) {
            if (tile.tile.block() == Blocks.air) DustedFx.riftGlow.at(tile.tile.worldx(), tile.tile.worldy(), 0f, tile.tile);
            tile.data = offset;
        }
    }
}
