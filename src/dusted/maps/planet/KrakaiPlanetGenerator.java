package dusted.maps.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.maps.generators.*;
import mindustry.world.*;

public class KrakaiPlanetGenerator extends PlanetGenerator {
    RidgedPerlin rid = new RidgedPerlin(645213, 1);
    float scl = 6f;
    float waterOffset = 0.06f;

    Block[][] arr = {
            {Blocks.dirt, Blocks.slag, Blocks.basalt, Blocks.darksand, Blocks.charr, Blocks.dirt, Blocks.darksand, Blocks.dirt},
            {Blocks.charr, Blocks.dirt, Blocks.slag, Blocks.basalt, Blocks.slag, Blocks.charr, Blocks.dirt, Blocks.slag},
            {Blocks.dirt, Blocks.charr, Blocks.dirt, Blocks.slag, Blocks.slag, Blocks.charr, Blocks.basalt, Blocks.basalt},
            {Blocks.charr, Blocks.basalt, Blocks.dirt, Blocks.basalt, Blocks.dirt, Blocks.slag, Blocks.basalt, Blocks.basalt}
    };

    @Override
    public float getHeight(Vec3 position) {
        return Math.max(rawHeight(position), 0.3f);
    }

    @Override
    public Color getColor(Vec3 position) {
        Block block = getBlock(position);
        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    float rawHeight(Vec3 position) {
        position = Tmp.v33.set(position).scl(scl);
        return (Mathf.pow((float) noise.octaveNoise3D(3, 0.4, 0.4, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

    Block getBlock(Vec3 position) {
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(scl);
        float rad = scl;
        float temp = Mathf.clamp(Math.abs(position.y * 2f) / (rad));
        float tnoise = (float) noise.octaveNoise3D(3, 0.6, 0.4, position.x, position.y + 999f, position.z);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 1.2f;
        height = Mathf.clamp(height);

        return arr[Mathf.clamp((int) (temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int) (height * arr[0].length), 0, arr[0].length - 1)];
    }

    @Override
    public void genTile(Vec3 position, TileGen tile) {
        tile.floor = getBlock(position);
        tile.block = tile.floor.asFloor().wall;

        if (rid.getValue(position.x, position.y, position.z, 20) > 0.3) {
            tile.block = Blocks.air;
        }
    }

    @Override
    protected void generate() {
        //TODO
        Schematics.placeLaunchLoadout(width / 2, height / 2);
    }
}
