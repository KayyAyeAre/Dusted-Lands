package dusted.world.blocks.environment;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import dusted.content.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class Fumarole extends Floor {
    public TextureRegion[][] tileRegions;
    public Effect effect = DustedFx.volcanicGas;
    public float effectChance = 0.005f;

    public Fumarole(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();

        tileRegions = new TextureRegion[variants][4];
        for (int i = 0; i < variants; i++) {
            for (int j = 0; j < 4; j++) {
                tileRegions[i][j] = Core.atlas.find(name + (i + 1) + "-" + j);
            }
        }
    }

    @Override
    public void loadIcon() {
        fullIcon = uiIcon = Core.atlas.find(name + "1-0");
    }

    @Override
    public void drawBase(Tile tile) {
        Mathf.rand.setSeed(tile.pos());
        Draw.rect(tileRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))][index(tile)], tile.worldx(), tile.worldy());

        Draw.alpha(1f);
        drawEdges(tile);
        drawOverlay(tile);
    }

    @Override
    public boolean updateRender(Tile tile) {
        return true;
    }

    @Override
    public void renderUpdate(UpdateRenderState state) {
        if (state.tile.block() == Blocks.air && Mathf.chanceDelta(effectChance)) {
            effect.at(state.tile.drawx(), state.tile.drawy());
        }
    }

    public int index(Tile tile) {
        int output = 0;
        if (tile.nearby(0).floor() == tile.floor()) output |= 1;
        if (tile.nearby(2).floor() == tile.floor()) output |= 2;
        return output;
    }
}
