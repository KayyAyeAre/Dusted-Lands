package dusted.world.consumers;

import arc.*;
import arc.math.*;
import dusted.*;
import dusted.graphics.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.consumers.*;

//has to be actively decaying, cant be shielded
public class ConsumeDecay extends Consume {
    public float decay(Building build) {
        return DustedLands.decay.isShielded(build) ? 0f : build.block.sumAttribute(DustedAttribute.decay, build.tileX(), build.tileY()) + Vars.state.rules.attributes.get(DustedAttribute.decay);
    }

    @Override
    public float efficiency(Building build) {
        return decay(build);
    }

    @Override
    public void apply(Block block) {
        Core.app.post(() -> {
            block.addBar("decay", entity -> new Bar(
                    () -> Core.bundle.format("bar.decay", decay(entity) * 100f),
                    () -> DustedPal.decay,
                    () -> Mathf.clamp(decay(entity))
            ));
        });
    }
}
