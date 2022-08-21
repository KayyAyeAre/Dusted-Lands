package dusted;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import dusted.content.*;
import dusted.game.*;
import dusted.graphics.*;
import dusted.input.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import mindustry.mod.*;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.*;

public class DustedLands extends Mod {
    public static DustedInputHandler inputHandler;
    public static Decay decay;

    public DustedLands() {
        Core.app.addListener(inputHandler = new DustedInputHandler());
        Core.app.addListener(decay = new Decay());
        VolcanoSpawner.load();

        Events.run(FileTreeInitEvent.class, () -> Core.app.post(DustedShaders::init));
        Events.run(Trigger.drawOver, () -> {
            if (Vars.renderer.animateShields && DustedShaders.decayShield != null) Draw.drawRange(Layer.shields + 2.5f, 1f, () -> Vars.renderer.effectBuffer.begin(Color.clear), () -> {
                Vars.renderer.effectBuffer.end();
                Vars.renderer.effectBuffer.blit(DustedShaders.decayShield);
            });
        });

        Vars.renderer.addEnvRenderer(DustedEnv.volcanic, () -> {

        });
    }

    @Override
    public void loadContent() {
        DustedStatusEffects.load();
        DustedItems.load();
        DustedPowders.load();
        DustedUnitTypes.load();
        DustedBlocks.load();
        DustedLoadouts.load();
        DustedWeathers.load();
        DustedPlanets.load();
        DustedSectorPresets.load();
        KrakaiTechTree.load();
    }
}
