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

import static mindustry.Vars.*;

public class KrakaiPlanetGenerator extends PlanetGenerator {
    float scl = 6f;

    Block[][] terrain = {
            {DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.scoria, DustedBlocks.scoria, Blocks.darksand, Blocks.basalt, DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.latite},
            {DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.volstone, Blocks.darksand, Blocks.basalt, DustedBlocks.volstone, DustedBlocks.latite, DustedBlocks.latite},
            {DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.stradrock, Blocks.hotrock, Blocks.darksand, Blocks.basalt, DustedBlocks.volstone, DustedBlocks.scoria, DustedBlocks.latite},
            {DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.stradrock, Blocks.hotrock, Blocks.hotrock, Blocks.darksand, Blocks.basalt, Blocks.basalt, DustedBlocks.volstone, DustedBlocks.scoria},
            {DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.volstone, DustedBlocks.volstone, Blocks.darksand, Blocks.darksand, Blocks.basalt, DustedBlocks.scoria, DustedBlocks.latite},
            {DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.latite, DustedBlocks.scoria, DustedBlocks.scoria, DustedBlocks.stradrock, DustedBlocks.stradrock, Blocks.darksand, Blocks.darksand, Blocks.basalt, DustedBlocks.latite, DustedBlocks.latite}
    };

    Block[] decayedTerrain = {DustedBlocks.cavnenSilk, DustedBlocks.cavnenSilk, DustedBlocks.cavnenSilk, DustedBlocks.cavnenDusting, DustedBlocks.cavnenSediment, DustedBlocks.riftRock, DustedBlocks.decayedRock, DustedBlocks.decayedRock, DustedBlocks.fallenStone, DustedBlocks.fallenMantle};

    @Override
    public Schematic getDefaultLoadout() {
        return DustedLoadouts.basicAbate;
    }

    @Override
    public boolean allowLanding(Sector sector) {
        return false;
    }

    @Override
    public void generateSector(Sector sector) {
        //no
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
        return (Math.abs(position.y) * 0.42f) + Simplex.noise3d(seed, 6, 0.5, 0.6f, position.x + 20f, position.y + 20f, position.z + 20f);
    }

    @Override
    public Color getColor(Vec3 position) {
        Block block = getBlock(position);
        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    Block getBlock(Vec3 position) {
        float height = Mathf.clamp(genHeight(position) * 0.9f);

        Block[] arr = terrain[Mathf.clamp(Mathf.round(((position.y + 1f) / 2f) * (terrain.length - 1)), 0, terrain.length - 1)];
        int index = Mathf.clamp((int) (height * terrain[0].length), 0, terrain[0].length - 1);
        Block result = decay(position) < 0.7f || arr[index] == DustedBlocks.volstone ? arr[index] : decayedTerrain[index];

        //lower slag in decayed biomes
        float slagHeight = rawHeight(position);
        if (((decay(position) < 0.7f && slagHeight < 0.27f) || slagHeight < 0.6f) && Simplex.noise3d(seed, 12, 0.3, 0.9f, position.x + 100f, position.y + 100f, position.z + 100f) + Math.abs(position.y / 3.5f) < 0.52f) result = Blocks.slag;

        return result;
    }

    @Override
    protected void genTile(Vec3 position, TileGen tile) {
        tile.floor = getBlock(position);
        tile.block = tile.floor == Blocks.slag ? Blocks.duneWall : tile.floor.asFloor().wall;

        if (tile.floor == Blocks.slag && Ridged.noise3d(seed, position.x, position.y, position.z, 4, 17) > 0.68) {
            tile.floor = Blocks.hotrock;
        }

        if (Ridged.noise3d(seed, position.x, position.y, position.z, 2, 14) > 0.24) {
            tile.block = Blocks.air;
        }
    }

    /*
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
    */

    @Override
    protected void generate() {
        //:<
        Block slagwall = Blocks.slag.asFloor().wall, hotwall = Blocks.hotrock.asFloor().wall, magmawall = Blocks.magmarock.asFloor().wall;
        Blocks.slag.asFloor().wall = Blocks.hotrock.asFloor().wall = Blocks.magmarock.asFloor().wall = Blocks.duneWall;

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

        //antimony spawns more commonly near the equator
        boolean antimony = Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z) + (1f - Math.abs(sector.tile.v.y)) * 0.55f > 0.81f;
        //platinum spawns more in low, decayed biomes (this isnt actually used yet)
        boolean platinum = Simplex.noise3d(seed, 3, 0.5, scl, sector.tile.v.x, sector.tile.v.y + 20, sector.tile.v.z) + decay(sector.tile.v) * 0.7f > 0.85f + rawHeight(sector.tile.v) * 0.33f;

        //ore
        pass((x, y) -> {
            if (!floor.asFloor().isDeep()) {
                if (noise(x / 3f + 150f, y + 500f, 4, 0.7f, 20f, 1f) > 0.7f) {
                    ore = DustedBlocks.oreZircon;
                }

                if (noise(x - 300f, y - x + 100f, 4, 0.8f, 20f, 1f) > 0.72f) {
                    ore = DustedBlocks.oreArsenic;
                }

                if (antimony && noise(x + 999f, y / 4f - 150f, 4, 0.9f, 15f, 1f) < 0.22f) {
                    ore = DustedBlocks.oreAntimony;
                }
            }
        });

        trimDark();
        median(2);

        int tlen = tiles.width * tiles.height;
        int total = 0, slag = 0;

        for (int i = 0; i < tlen; i++) {
            Tile tile = tiles.geti(i);
            if (tile.block() == Blocks.air) {
                total++;
                if (tile.floor().liquidDrop == Liquids.slag) slag++;
            }
        }

        //TODO
        boolean slagUnits = (float) slag / total >= 0.19f;

        pass((x, y) -> {
            if (floor == DustedBlocks.stradrock) {
                if (Math.abs(0.5f - noise(x - 30, y, 3, 0.8, 70)) > 0.02) {
                    floor = DustedBlocks.scorchedStradrock;
                }
            }

            if(floor == Blocks.hotrock) {
                if (Math.abs(0.5f - noise(x - 20, y, 7, 0.8, 85)) > 0.04) {
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

            if (floor == DustedBlocks.riftRock && Math.abs(0.5f - noise(x + 50, y + 20, 7, 0.8, 70)) > 0.03) floor = DustedBlocks.decayedRock;

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

        //TODO enemy waves

        //rollback
        Blocks.slag.asFloor().wall = slagwall;
        Blocks.hotrock.asFloor().wall = hotwall;
        Blocks.magmarock.asFloor().wall = magmawall;
    }
}
