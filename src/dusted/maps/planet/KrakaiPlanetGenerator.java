package dusted.maps.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import dusted.content.*;
import mindustry.ai.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

import static mindustry.Vars.*;

public class KrakaiPlanetGenerator extends PlanetGenerator {
    float scl = 6f;

    Block[][] terrain = {
            {DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.scoria, DustedBlocks.scoria, Blocks.darksand, Blocks.basalt, DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.latite},
            {DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.volstone, Blocks.darksand, Blocks.basalt, DustedBlocks.volstone, DustedBlocks.latite, DustedBlocks.latite},
            {DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.volstone, Blocks.darksand, Blocks.basalt, DustedBlocks.volstone, DustedBlocks.volstone, DustedBlocks.volstone},
            {DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.volstone, DustedBlocks.volstone, Blocks.darksand, Blocks.basalt, DustedBlocks.volstone, DustedBlocks.volstone, DustedBlocks.volstone},
            {DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.volstone, DustedBlocks.volstone, Blocks.darksand, Blocks.darksand, Blocks.basalt, DustedBlocks.latite, DustedBlocks.latite},
            {DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.stradrock, Blocks.darksand, Blocks.darksand, Blocks.basalt, DustedBlocks.latite, DustedBlocks.latite}
    };

    @Override
    public Schematic getDefaultLoadout() {
        return DustedLoadouts.basicAbate;
    }

    @Override
    public boolean allowLanding(Sector sector) {
        return false;
    }

    @Override
    public float getHeight(Vec3 position) {
        return Mathf.pow(rawHeight(position), 2f);
    }

    public float rawHeight(Vec3 position) {
        position = Tmp.v33.set(position).scl(scl);
        return Simplex.noise3d(seed, 5, 0.4, 0.4, position.x, position.y, position.z);
    }

    public float genHeight(Vec3 position) {
        position = Tmp.v33.set(position).scl(scl);
        return Simplex.noise3d(seed, 7, 0.6, 1.2, position.x, position.y, position.z);
    }

    public float decay(Vec3 position) {
        return (Math.abs(position.y) * 0.3f) + Simplex.noise3d(seed, 6, 0.5, 0.3, position.x + 20f, position.y + 20f, position.z + 20f);
    }

    @Override
    public Color getColor(Vec3 position) {
        Block block = getBlock(position);
        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    Block getBlock(Vec3 position) {
        float height = Mathf.clamp(genHeight(position) * 1.1f);

        Block result = terrain[Mathf.clamp(Mathf.round(((position.y + 1f) / 2f) * (terrain.length - 1)), 0, terrain.length - 1)][Mathf.clamp((int) (height * terrain[0].length), 0, terrain[0].length - 1)];

        if (height < 0.6f && Simplex.noise3d(seed, 12, 0.3, 0.9, position.x + 100f, position.y + 100f, position.z + 100f) < 0.3f + position.dst(0f, 0f, 0f) / scl) result = Blocks.slag;

        //decayed blocks
        if (decay(position) > 0.9f) {
            if (!(result == DustedBlocks.volstone || result == Blocks.slag)) {
                result = Simplex.noise3d(seed, 9, 0.4, 0.5, position.x + 20f, position.y + 20f, position.z + 20f) < 0.6f ? DustedBlocks.cavnenSediment : DustedBlocks.cavnenDusting;
            }
        }

        return result;
    }

    @Override
    protected void genTile(Vec3 position, TileGen tile) {
        tile.floor = getBlock(position);
        tile.block = tile.floor == Blocks.slag ? Blocks.duneWall : tile.floor.asFloor().wall;

        if (Ridged.noise3d(seed, position.x, position.y, position.z, 3, 12) > 0.22) {
            tile.block = Blocks.air;
        }
    }

    @Override
    public void erase(int cx, int cy, int rad) {
        for (int x = -rad; x <= rad; x++) {
            for (int y = -rad; y <= rad; y++) {
                int wx = cx + x, wy = cy + y;
                if (Structs.inBounds(wx, wy, width, height) && Mathf.within(x, y, rad)) {
                    Tile other = tiles.getn(wx, wy);
                    other.setBlock(Blocks.air);
                    if (other.floor() == Blocks.slag) other.setFloor(Blocks.basalt.asFloor());
                }
            }
        }
    }

    @Override
    protected void generate() {
        //:<
        Blocks.slag.asFloor().wall = Blocks.duneWall;

        class Room {
            final int x, y, radius;
            final ObjectSet<Room> connected = new ObjectSet<>();

            Room(int x, int y, int radius) {
                this.x = x;
                this.y = y;
                this.radius = radius;
                connected.add(this);
            }

            void connect(Room to) {
                if (connected.contains(to)) return;

                connected.add(to);
                float nscl = rand.random(20f, 60f);
                int stroke = rand.random(4, 12);
                brush(pathfind(x, y, to.x, to.y, tile -> (tile.solid() ? 5f : 0f) + noise(tile.x, tile.y, 1, 1, 1f / nscl) * 60, Astar.manhattan), stroke);
            }
        }

        float constraint = 1.4f;
        float radius = width / 2f / Mathf.sqrt3;
        int rooms = rand.random(2, 5);
        Seq<Room> roomseq = new Seq<>();

        for (int i = 0; i < rooms; i++) {
            Tmp.v1.trns(rand.random(360f), rand.random(radius / constraint));
            float rx = (width / 2f + Tmp.v1.x);
            float ry = (height / 2f + Tmp.v1.y);
            float maxrad = radius - Tmp.v1.len();
            float rrad = Math.min(rand.random(9f, maxrad / 2f), 30f);
            roomseq.add(new Room((int) rx, (int) ry, (int) rrad));
        }

        Room spawn = new Room(width / 2, height / 2, 16);
        Seq<Room> enemies = new Seq<>();
        int enemySpawns = rand.random(1, Math.max((int) (sector.threat * 4), 1));
        int offset = rand.nextInt(360);
        float length = width / 2.55f - rand.random(13, 23);
        int angleStep = 5;
        int waterCheckRad = 5;
        for (int i = 0; i < 360; i += angleStep) {
            int angle = offset + i;
            int cx = (int) (width / 2 + Angles.trnsx(angle, length));
            int cy = (int) (height / 2 + Angles.trnsy(angle, length));

            int waterTiles = 0;

            for (int rx = -waterCheckRad; rx <= waterCheckRad; rx++) {
                for (int ry = -waterCheckRad; ry <= waterCheckRad; ry++) {
                    Tile tile = tiles.get(cx + rx, cy + ry);
                    if (tile == null || tile.floor().liquidDrop != null) {
                        waterTiles++;
                    }
                }
            }

            if (waterTiles <= 4 || (i + angleStep >= 360)) {
                roomseq.add(spawn = new Room(cx, cy, rand.random(8, 15)));

                for (int j = 0; j < enemySpawns; j++) {
                    float enemyOffset = rand.range(60f);
                    Tmp.v1.set(cx - width / 2f, cy - height / 2f).rotate(180f + enemyOffset).add(width / 2f, height / 2f);
                    Room espawn = new Room((int) Tmp.v1.x, (int) Tmp.v1.y, rand.random(12, 21));
                    roomseq.add(espawn);
                    enemies.add(espawn);
                }

                break;
            }
        }

        roomseq.each(r -> erase(r.x, r.y, r.radius));

        int connections = rand.random(Math.max(rooms - 1, 1), rooms + 3);
        for (int i = 0; i < connections; i++) {
            roomseq.random(rand).connect(roomseq.random(rand));
        }

        roomseq.each(spawn::connect);

        cells(4);
        distort(10f, 12f);

        inverseFloodFill(tiles.getn(spawn.x, spawn.y));

        //this sucks
        Seq<Block> ores = Seq.with(DustedBlocks.oreZircon, DustedBlocks.oreArsenic);
        float poles = Math.abs(sector.tile.v.y);
        float nmag = 0.5f;
        float scl = 1f;
        float addscl = 1.3f;

        if (Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.7f * addscl) {
            ores.add(DustedBlocks.oreAntimony);
        }
        FloatSeq frequencies = new FloatSeq();
        for (int i = 0; i < ores.size; i++) {
            frequencies.add(rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f);
        }

        pass((x, y) -> {
            if (!floor.asFloor().hasSurface()) return;

            int offsetX = x - 4, offsetY = y + 23;
            for (int i = ores.size - 1; i >= 0; i--) {
                Block entry = ores.get(i);
                float freq = frequencies.get(i);
                if (Math.abs(0.5f - noise(offsetX, offsetY + i * 999, 2, 0.7, (40 + i * 2))) > 0.22f + i * 0.01 &&
                        Math.abs(0.5f - noise(offsetX, offsetY - i * 999, 1, 1, (30 + i * 4))) > 0.37f + freq) {
                    ore = entry;
                    break;
                }
            }
        });

        trimDark();
        median(2);

        pass((x, y) -> {
            if (floor == DustedBlocks.stradrock) {
                if (Math.abs(0.5f - noise(x - 30, y, 3, 0.8, 70)) > 0.02) {
                    floor = DustedBlocks.scorchedStradrock;
                }
            }

            if(floor == Blocks.hotrock) {
                if (Math.abs(0.5f - noise(x - 20, y, 5, 0.8, 85)) > 0.04) {
                    floor = Blocks.basalt;
                } else {
                    ore = Blocks.air;
                    boolean all = true;
                    for (Point2 p : Geometry.d4) {
                        Tile other = tiles.get(x + p.x, y + p.y);
                        if (other == null || (other.floor() != Blocks.hotrock && other.floor() != Blocks.magmarock)) {
                            all = false;
                        }
                    }
                    if (all) {
                        floor = Blocks.magmarock;
                    }
                }
            }

            //decoration
            boolean decorate = true;
            for (int i = 0; i < 4; i++) {
                Tile near = world.tile(x + Geometry.d4[i].x, y + Geometry.d4[i].y);
                if (near != null && near.block() != Blocks.air) {
                    decorate = false;
                    break;
                }
            }

            if (decorate && rand.chance(0.01) && floor.asFloor().hasSurface() && block == Blocks.air) {
                block = floor == DustedBlocks.volstone && rand.chance(0.4) ? DustedBlocks.volSprout :
                        (floor == DustedBlocks.cavnenSediment || floor == DustedBlocks.cavnenDusting) && rand.chance(0.4) ? DustedBlocks.weepingShrub :
                floor.asFloor().decoration;
            }
        });

        float difficulty = sector.threat;
        Schematics.placeLaunchLoadout(spawn.x, spawn.y);

        for (Room espawn : enemies) {
            tiles.getn(espawn.x, espawn.y).setOverlay(Blocks.spawn);
        }

        state.rules.winWave = sector.info.winWave = 10 + 5 * (int) Math.max(difficulty * 10, 1);

        float waveTimeDec = 0.4f;

        state.rules.waveSpacing = Mathf.lerp(60 * 65 * 2, 60f * 60f * 1f, Math.max(difficulty - waveTimeDec, 0f) / 0.8f);
        state.rules.waves = sector.info.waves = true;
        state.rules.enemyCoreBuildRadius = 600f;

        //TODO change enemies
        state.rules.spawns = Waves.generate(difficulty, new Rand(), state.rules.attackMode);

        //rollback
        Blocks.slag.asFloor().wall = Blocks.yellowStoneWall;
    }
}
