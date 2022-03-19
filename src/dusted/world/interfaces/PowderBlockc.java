package dusted.world.interfaces;

import arc.math.*;
import arc.struct.*;
import dusted.type.*;
import dusted.world.blocks.powder.*;
import dusted.world.blocks.powder.PowderBlock.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.world.*;

public interface PowderBlockc {
    PowderModule powderModule();
    Building build();
    Bits filters();

    default float powderCapacity() {
        return ((PowderBlock) build().block).powderCapacity;
    }

    default boolean acceptPowder(Building source, Powder powder) {
        return filters().get(powder.id);
    }

    default void handlePowder(Powder powder, float amount) {
        powderModule().add(powder, amount);
    }

    default void dumpPowder(Powder powder) {
        dumpPowder(powder, 2.0F);
    }

    default void dumpPowder(Powder powder, float scaling) {
        int dump = build().cdump;
        if (powderModule().get(powder) <= 1.0E-4F) return;
        if (!Vars.net.client() && Vars.state.isCampaign() && build().team == Vars.state.rules.defaultTeam) powder.unlock();
        for (int i = 0; i < build().proximity.size; i++) {
            build().incrementDump(build().proximity.size);
            Building building = build().proximity.get((i + dump) % build().proximity.size);
            if (building != null && building.team == build().team && building instanceof PowderBlockc other) {
                float ofract = other.powderModule().get(powder) / other.powderCapacity();
                float fract = powderModule().get(powder) / powderCapacity();
                if (ofract < fract)
                    transferPowder(other, (fract - ofract) * powderCapacity() / scaling, powder);
            }
        }
    }

    default void transferPowder(PowderBlockc next, float amount, Powder powder) {
        float flow = Math.min(next.powderCapacity() - next.powderModule().get(powder), amount);
        if (next.acceptPowder(build(), powder)) {
            next.handlePowder(powder, flow);
            powderModule().remove(powder, flow);
        }
    }

    default void movePowder(Building building, Powder powder) {
        if (building.team == build().team && building instanceof PowderBlockc next && powderModule().get(powder) > 0f) {
            float ofract = next.powderModule().get(powder) / next.powderCapacity();
            float fract = powderModule().get(powder) / powderCapacity();
            float flow = Math.min(Mathf.clamp((fract - ofract)) * powderCapacity(), powderModule().get(powder));
            flow = Math.min(flow, powderCapacity() - next.powderModule().get(powder));

            if (flow > 0f && ofract <= fract && next.acceptPowder(build(), powder)) {
                next.handlePowder(powder, flow);
                powderModule().remove(powder, flow);
            }
        }
    }

    default void movePowderForward(Powder powder) {
        Tile next = build().tile.nearby(build().rotation);

        if (next.build != null) {
            movePowder(next.build, powder);
        }
    }
}
