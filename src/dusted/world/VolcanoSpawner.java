package dusted.world;

import arc.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import dusted.content.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;

//TODO
public class VolcanoSpawner {
    private static Seq<Tile> volcanoes = new Seq<>();
    public static int eruptionWave;
    public static BulletType volcanoBullet;

    public static void load() {
        volcanoBullet = new BasicBulletType(4f, 70f, "circle-bullet") {
            {
                width = height = 22f;
                lifetime = 80f;
                status = StatusEffects.melting;
                statusDuration = 8 * 60f;
                frontColor = Pal.lighterOrange;
                backColor = trailColor = Pal.lightOrange;
                trailWidth = 8f;
                trailLength = 22;
                pierce = pierceBuilding = true;
                shrinkX = shrinkY = 0.6f;
            }

            @Override
            public void despawned(Bullet b) {
                super.despawned(b);
                Puddles.deposit(Vars.world.tileWorld(b.x, b.y), Liquids.slag, 128f);
            }
        };

        Events.run(WorldLoadEvent.class, () -> {
            volcanoes.clear();
            for (Tile tile : Vars.world.tiles) {
                if (tile.overlay() == DustedBlocks.volcanoZone) volcanoes.add(tile);
            }
            eruptionWave = Vars.state.rules.tags.getInt("volcano-wave", 20);
        });

        Events.run(WaveEvent.class, () -> {
            if (volcanoes.isEmpty()) return;
            int diff = eruptionWave - Vars.state.wave;

            if (diff == 1 || diff == 2 || diff == 5 || diff == 10) {
                Vars.ui.hudfrag.showToast(Icon.warning, Core.bundle.format("volcanowarn" + (diff == 1 ? ".one" : ""), Vars.state.isCampaign() ? Vars.state.getSector().name() : Vars.state.map.name(), diff));
            }

            if (Vars.state.wave == eruptionWave) runEruption();
        });
    }

    public static void runEruption() {
        volcanoes.each(t -> {
            bullets(t);
            for (int r = 0; r < 16; r++) {
                Time.run(r * 100f + Mathf.random(-60f, 70f), () -> bullets(t));
            }
        });
    }

    public static void bullets(Tile tile) {
        if (!Vars.state.isPlaying()) return;
        DustedFx.eruption.at(tile.worldx(), tile.worldy());
        Sounds.explosionbig.at(tile.worldx(), tile.worldy(), Mathf.random(0.9f, 1.1f));
        for (int i = 0; i < 30; i++) {
            Call.createBullet(volcanoBullet, Team.derelict, tile.worldx(), tile.worldy(), Mathf.random(360f), volcanoBullet.damage, Mathf.random(0.6f, 1.4f), 1);
        }
    }
}
