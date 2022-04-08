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
import mindustry.type.Weather.*;
import mindustry.world.*;

import static mindustry.Vars.*;

public class KrakaiPlanetGenerator extends PlanetGenerator {
    int seed = 64;
    BaseGenerator basegen = new BaseGenerator();
    float scl = 6f;
    float waterOffset = 0.06f;

    Block[][] arr = {
        {DustedBlocks.cavnenSediment, DustedBlocks.cavnenDusting, DustedBlocks.cavnenSediment, DustedBlocks.cavnenSediment, Blocks.darksand, DustedBlocks.cavnenSediment, Blocks.darksand, Blocks.basalt, DustedBlocks.cavnenSediment, Blocks.darksand, DustedBlocks.cavnenSediment, DustedBlocks.cavnenSediment, DustedBlocks.cavnenDusting},
        {DustedBlocks.cavnenDusting, DustedBlocks.cavnenDusting, Blocks.darksand, Blocks.slag, Blocks.darksand, DustedBlocks.cavnenSediment, Blocks.slag, DustedBlocks.cavnenSediment, Blocks.basalt, Blocks.darksand, DustedBlocks.cavnenDusting, Blocks.basalt, DustedBlocks.cavnenDusting},
        {Blocks.slag, Blocks.slag, DustedBlocks.volcanGravel, Blocks.slag, Blocks.basalt, DustedBlocks.cavnenSediment, DustedBlocks.volcanGravel, DustedBlocks.cavnenDusting, Blocks.slag, Blocks.darksand, DustedBlocks.cavnenSediment, Blocks.basalt, DustedBlocks.cavnenDusting},
        {DustedBlocks.volcanGravel, Blocks.slag, Blocks.darksand, Blocks.slag, Blocks.slag, DustedBlocks.volcanGravel, Blocks.slag, DustedBlocks.cavnenDusting, DustedBlocks.volcanGravel, Blocks.darksand, Blocks.slag, DustedBlocks.volcanGravel, DustedBlocks.cavnenDusting},
        {DustedBlocks.volcanGravel, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.darksand, DustedBlocks.volcanGravel, Blocks.darksand, Blocks.slag, DustedBlocks.volcanGravel, Blocks.darksand, Blocks.slag, DustedBlocks.volcanGravel, DustedBlocks.cavnenDusting},
        {DustedBlocks.volcanGravel, Blocks.slag, Blocks.darksand, Blocks.slag, Blocks.slag, DustedBlocks.volcanGravel, Blocks.slag, Blocks.slag, DustedBlocks.volcanGravel, Blocks.darksand, Blocks.slag, DustedBlocks.volcanGravel, Blocks.darksand},
        {Blocks.slag, DustedBlocks.volcanGravel, Blocks.darksand, Blocks.darksand, Blocks.slag, DustedBlocks.volcanGravel, DustedBlocks.volcanGravel, Blocks.slag, DustedBlocks.volcanGravel, Blocks.slag, Blocks.slag, Blocks.darksand, Blocks.darksand},
        {DustedBlocks.cavnenDusting, Blocks.darksand, Blocks.slag, DustedBlocks.volcanGravel, Blocks.slag, DustedBlocks.volcanGravel, Blocks.darksand, DustedBlocks.volcanGravel, Blocks.slag, Blocks.darksand, Blocks.slag, DustedBlocks.cavnenDusting, DustedBlocks.cavnenDusting},
        {DustedBlocks.volcanGravel, DustedBlocks.cavnenDusting, Blocks.darksand, DustedBlocks.cavnenSediment, Blocks.slag, DustedBlocks.cavnenSediment, Blocks.slag, DustedBlocks.cavnenDusting, DustedBlocks.volcanGravel, Blocks.darksand, Blocks.slag, DustedBlocks.volcanGravel, DustedBlocks.cavnenDusting},
        {Blocks.basalt, Blocks.darksand, DustedBlocks.cavnenDusting, Blocks.slag, Blocks.basalt, DustedBlocks.cavnenDusting, Blocks.darksand, DustedBlocks.cavnenDusting, Blocks.slag, DustedBlocks.volcanGravel, DustedBlocks.cavnenSediment, Blocks.basalt, DustedBlocks.cavnenDusting},
        {DustedBlocks.cavnenSediment, Blocks.basalt, Blocks.darksand, Blocks.basalt, Blocks.darksand, DustedBlocks.cavnenSediment, DustedBlocks.volcanGravel, DustedBlocks.cavnenSediment, Blocks.basalt, Blocks.darksand, DustedBlocks.cavnenSediment, DustedBlocks.cavnenDusting, DustedBlocks.cavnenSediment},
        {Blocks.darksand, DustedBlocks.cavnenSediment, DustedBlocks.cavnenDusting, DustedBlocks.cavnenSediment, Blocks.darksand, DustedBlocks.cavnenSediment, Blocks.darksand, Blocks.basalt, Blocks.basalt, Blocks.darksand, DustedBlocks.cavnenDusting, DustedBlocks.cavnenSediment, DustedBlocks.cavnenDusting}
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
        return (Mathf.pow(Simplex.noise3d(seed, 3, 0.4, 0.4, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

    Block getBlock(Vec3 position) {
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(scl);
        float rad = scl;
        float temp = Mathf.clamp(Math.abs(position.y * 2f) / (rad));
        float tnoise = Simplex.noise3d(seed, 7, 0.56, 1f / 3f, position.x, position.y + 999f, position.z);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 1.2f;
        height = Mathf.clamp(height);

        return arr[Mathf.clamp((int) (temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int) (height * arr[0].length), 0, arr[0].length - 1)];
    }

    @Override
    public void genTile(Vec3 position, TileGen tile) {
        tile.floor = getBlock(position);
        tile.block = tile.floor.asFloor().wall;
        if (tile.block == Blocks.air && tile.floor == Blocks.slag) tile.block = DustedBlocks.volcanWall;

        if (Ridged.noise3d(seed, position.x, position.y, position.z, 22) > 0.32) {
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

        Seq<Block> ores = Seq.with(Blocks.oreCopper, DustedBlocks.orePlastel);
        float poles = Math.abs(sector.tile.v.y);
        float nmag = 0.5f;
        float scl = 1f;
        float addscl = 1.3f;

        if (Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.4f * addscl) {
            ores.add(Blocks.oreTitanium);
        }
        if (Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.7f * addscl) {
            ores.add(Blocks.oreThorium);
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
            //sediment
            if (floor == DustedBlocks.cavnenDusting) {
                if (Math.abs(0.5f - noise(x - 60, y, 2, 0.76, 85)) > 0.25f &&
                    Math.abs(0.5f - noise(x, y + sector.id * 10, 1, 1, 60)) > 0.4f && !(roomseq.contains(r -> Mathf.within(x, y, r.x, r.y, 15)))) {
                    floor = DustedBlocks.cavnenSediment;
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
                block = floor == DustedBlocks.volcanGravel && Mathf.chance(0.6) ? DustedBlocks.volcanFlower : floor.asFloor().decoration;
            }
        });

        float difficulty = sector.threat;
        Schematics.placeLaunchLoadout(spawn.x, spawn.y);

        for (Room espawn : enemies) {
            tiles.getn(espawn.x, espawn.y).setOverlay(Blocks.spawn);
        }

        if (sector.hasEnemyBase()) {
            basegen.generate(tiles, enemies.map(r -> tiles.getn(r.x, r.y)), tiles.get(spawn.x, spawn.y), state.rules.waveTeam, sector, difficulty);
            state.rules.attackMode = sector.info.attack = true;
        } else {
            state.rules.winWave = sector.info.winWave = 10 + 5 * (int) Math.max(difficulty * 10, 1);
        }

        float waveTimeDec = 0.4f;

        state.rules.waveSpacing = Mathf.lerp(60 * 65 * 2, 60f * 60f * 1f, Math.max(difficulty - waveTimeDec, 0f) / 0.8f);
        state.rules.waves = sector.info.waves = true;
        state.rules.enemyCoreBuildRadius = 600f;

        state.rules.weather.add(new WeatherEntry(DustedWeathers.dustStorm));
        state.rules.spawns = Waves.generate(difficulty, new Rand(), state.rules.attackMode);
    }

    @Override
    public void postGenerate(Tiles tiles) {
        if (sector.hasEnemyBase()) {
            basegen.postGenerate();
        }
    }
}
