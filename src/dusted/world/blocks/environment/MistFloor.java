package dusted.world.blocks.environment;

import arc.math.*;
import arc.util.*;
import arc.util.noise.*;
import dusted.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class MistFloor extends Floor {
    public int seed = 0;
    public double octaves = 8, persistence = 0.5, scale = 7;
    public float mistSpacing = 240f;

    public MistFloor(String name) {
        super(name);
    }

    @Override
    public boolean updateRender(Tile tile) {
        return Simplex.noise2d(seed, octaves, persistence, scale, tile.x, tile.y) < 0.2f;
    }

    @Override
    public void renderUpdate(UpdateRenderState tile) {
        float offset = Simplex.noise2d(seed, 10, 0.5, 0.4, tile.tile.x, tile.tile.y) * mistSpacing;

        if ((tile.data += Time.delta) >= mistSpacing + offset) {
            tile.data = offset;
            DustedFx.mist.at(tile.tile.worldx() + Mathf.range(4f), tile.tile.worldy() + Mathf.range(4f));
        }
    }
}
