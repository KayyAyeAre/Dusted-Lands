package dusted;

import arc.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.math.*;
import dusted.content.*;
import dusted.game.*;
import dusted.graphics.*;
import dusted.input.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import mindustry.mod.*;
import mindustry.type.*;

public class DustedLands extends Mod {
    public static DustedInputHandler inputHandler;
    public static Decay decay;

    public DustedLands() {
        Core.app.addListener(inputHandler = new DustedInputHandler());
        Core.app.addListener(decay = new Decay());
        VolcanoSpawner.load();

        Events.run(FileTreeInitEvent.class, () -> Core.app.post(DustedShaders::init));
        Events.run(Trigger.drawOver, () -> {
            if (Vars.renderer.animateShields && DustedShaders.decayShield != null)
                Draw.drawRange(Layer.shields + 2.5f, 1f, () -> Vars.renderer.effectBuffer.begin(Color.clear), () -> {
                    Vars.renderer.effectBuffer.end();
                    Vars.renderer.effectBuffer.blit(DustedShaders.decayShield);
                });
        });

        //TODO should this be moved to a different class?
        float windSpeed = 0.4f, windAngle = 45f;
        float windx = Mathf.cosDeg(windAngle) * windSpeed, windy = Mathf.sinDeg(windAngle) * windSpeed;

        Vars.renderer.addEnvRenderer(DustedEnv.volcanic, () -> {
            Texture tex = Core.assets.get("sprites/distortAlpha.png", Texture.class);
            if (tex.getMagFilter() != TextureFilter.linear) {
                tex.setFilter(TextureFilter.linear);
                tex.setWrap(TextureWrap.repeat);
            }

            Draw.z(Layer.weather - 1);
            Weather.drawNoiseLayers(tex, Pal.darkFlame, 1000f, 0.2f, 0.5f, 1.2f, 0.6f, 0.4f, 4, -1.2f, 0.7f, 0.8f, 0.9f);
            Draw.reset();

            Draw.draw(Layer.weather, () -> {
                Weather.drawParticles(
                        Core.atlas.find("particle"), Pal.lightOrange,
                        1.2f, 3f,
                        6000f, 1.2f, 1f,
                        windx, windy,
                        1f, 1f,
                        40f, 60f,
                        1.2f, 7f,
                        false
                );
            });
        });
    }

    @Override
    public void init() {
        Vars.mods.locateMod("dusted-lands").meta.author += """
    
    
    [gray]Credits:[]
    [accent]Sh1penfire[], for the deteriorating status effect sprite
    [accent]rubberduck[] on [accent]opengameart.org[] for blight and clutter's shoot sounds
    [accent]KuraiWolf[] on [accent]opengameart.org[] for sunder's shoot sound
    [accent]Thimras[] on [accent]opengameart.org[] for crush's shoot sound
    [accent]kurt[] on [accent]opengameart.org[] for the crisalt synthesizer ambient noise
    [accent]Gobusto[] on [accent]opengameart.org[] for the deterioration chamber ambient noise""";
    }

    @Override
    public void loadContent() {
        DustedStatusEffects.load();
        DustedItems.load();
        DustedPowders.load();
        DustedUnitTypes.load();
        DustedBlocks.load();
        DustedLoadouts.load();
        DustedPlanets.load();
        DustedSectorPresets.load();
        KrakaiTechTree.load();
    }
}
