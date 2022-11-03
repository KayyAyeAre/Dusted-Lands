package dusted.world.interfaces;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.world.blocks.power.*;

//why cant i extend two classes at once
public interface TransferPowerc extends BuildAccessor {
    float radius();

    int lastChange();
    void lastChange(int change);

    default void updateTransfer() {
        if (lastChange() != Vars.world.tileChanges) {
            lastChange(Vars.world.tileChanges);

            Vars.indexer.eachBlock(build(), radius(), b -> b.block.hasPower && !(b.block instanceof PowerNode), b -> {
                build().power.links.addUnique(b.pos());
                b.power.links.addUnique(build().pos());

                build().power.graph.addGraph(b.power.graph);
            });
        }
    }

    default void drawTransfer() {
        float progress = Interp.pow2Out.apply((Time.time / 180f) % 1f);

        Draw.blend(Blending.additive);
        Draw.z(Layer.blockOver);
        Lines.stroke(10f * (1f - progress));
        Draw.color(Pal.powerLight, 0.5f * build().warmup());

        Lines.circle(build().x, build().y, progress * radius());

        Draw.blend();
        Draw.z(Layer.block);
        Draw.reset();
    }
}
