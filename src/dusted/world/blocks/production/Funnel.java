package dusted.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import dusted.content.*;
import dusted.type.*;
import dusted.world.blocks.environment.*;
import dusted.world.blocks.powder.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class Funnel extends PowderBlock {
    public float funnelAmount = 0.2f;
    public TextureRegion powderRegion;
    public float effectInterval = 120f;
    public Effect extractEffect = DustedFx.funnelExtractSmall;

    public Funnel(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output, 60f * funnelAmount * size * size, StatUnit.perSecond);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Tile tile = world.tile(x, y);
        if (tile == null) return;

        float amount = 0f;
        Powder powderDrop = null;

        for (Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
            if (drops(other)) {
                powderDrop = ((PowderFloor) other.floor()).powderDrop;
                amount += ((PowderFloor) other.floor()).powderMultiplier;
            }
        }

        if (powderDrop != null) {
            float width = drawPlaceText(Core.bundle.formatFloat("bar.funnelspeed", amount * funnelAmount * 60f, 0), x, y, valid);
            float dx = x * tilesize + offset - width / 2f - 4f, dy = y * tilesize + offset + size * tilesize / 2f + 5, s = iconSmall / 4f;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(powderDrop.fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(powderDrop.fullIcon, dx, dy, s, s);
        }
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (isMultiblock()) {
            Powder last = null;
            for (Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
                if (!(other.floor() instanceof PowderFloor)) continue;
                if (((PowderFloor) other.floor()).powderDrop != last && last != null) return false;
                last = ((PowderFloor) other.floor()).powderDrop;
            }
            return last != null;
        } else {
            return drops(tile);
        }
    }

    @Override
    public void load() {
        super.load();
        powderRegion = Core.atlas.find(name + "-powder");
    }

    protected boolean drops(Tile tile) {
        return tile != null && tile.floor() instanceof PowderFloor;
    }

    public class FunnelBuild extends PowderBuild {
        public float amount;
        public Powder powderDrop;
        public float effectTime;

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.color(powderDrop.color, powders.currentAmount() / powderCapacity);
            Draw.rect(powderRegion, x, y);
            Draw.color();
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();

            amount = 0f;
            powderDrop = null;

            for (Tile other : tile.getLinkedTiles(tempTiles)) {
                if (drops(other)) {
                    powderDrop = ((PowderFloor) other.floor()).powderDrop;
                    amount += ((PowderFloor) other.floor()).powderMultiplier;
                }
            }
        }

        @Override
        public boolean shouldConsume() {
            return powderDrop != null && powders.get(powderDrop) < powderCapacity - 0.01f && enabled;
        }

        @Override
        public void updateTile() {
            if (canConsume() && powderDrop != null) {
                float add = Math.min(powderCapacity - powders.total(), amount * funnelAmount * edelta());
                powders.add(powderDrop, add);

                effectTime += delta();
                if (effectTime > effectInterval) {
                    effectTime = 0;
                    extractEffect.at(x, y, 0f, powderDrop);
                }
            }

            dumpPowder(powders.current());
        }
    }
}
