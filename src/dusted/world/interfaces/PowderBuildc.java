package dusted.world.interfaces;

import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import dusted.content.*;
import dusted.type.*;
import dusted.world.blocks.powder.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.*;

//interface for blocks with a powder module to transfer powder
public interface PowderBuildc extends BuildAccessor {
    PowderModule powderModule();
    default float powderPressure() {
        return 1f;
    }

    default boolean outputsPowder() {
        return false;
    }

    default Building getPowderDestination(Building from, Powder powder) {
        return build();
    }

    default boolean acceptPowder(Building source, Powder powder) {
        return ((PowderBlockc) build().block).powderFilters()[powder.id];
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
            if (building instanceof PowderBuildc pow) {
                building = pow.getPowderDestination(build(), powder);
                if (building instanceof PowderBuildc other && building.team == build().team) {
                    float ofract = other.powderModule().get(powder) / ((PowderBlockc) building.block).powderCapacity();
                    float fract = powderModule().get(powder) / ((PowderBlockc) building.block).powderCapacity();
                    if (ofract < fract) transferPowder(other, (fract - ofract) * ((PowderBlockc) build().block).powderCapacity() / scaling, powder);
                }
            }
        }
    }

    default void transferPowder(PowderBuildc next, float amount, Powder powder) {
        float flow = Math.min(((PowderBlockc) next.build().block).powderCapacity() - next.powderModule().get(powder), amount);
        if (next.acceptPowder(build(), powder)) {
            next.handlePowder(powder, flow);
            powderModule().remove(powder, flow);
        }
    }

    default float movePowder(Building building, Powder powder) {
        if (building instanceof PowderBuildc pow) {
            building = pow.getPowderDestination(build(), powder);
            if (building.team == build().team && building instanceof PowderBuildc next && powderModule().get(powder) > 0f) {
                float ofract = next.powderModule().get(powder) / ((PowderBlockc) build().block).powderCapacity();
                float fract = powderModule().get(powder) / ((PowderBlockc) build().block).powderCapacity() * powderPressure();
                float flow = Math.min(Mathf.clamp((fract - ofract)) * ((PowderBlockc) build().block).powderCapacity(), powderModule().get(powder));
                flow = Math.min(flow, ((PowderBlockc) build().block).powderCapacity() - next.powderModule().get(powder));

                if (flow > 0f && ofract <= fract && next.acceptPowder(build(), powder)) {
                    next.handlePowder(powder, flow);
                    powderModule().remove(powder, flow);
                    return flow;
                }
            }
        }

        return 0;
    }

    default float movePowderForward(boolean leaks, Powder powder) {
        Tile next = build().tile.nearby(build().rotation);

        if (next.build != null) {
            return movePowder(next.build, powder);
        } else if (leaks && !next.block().solid) {
            float leakAmount = powderModule().get(powder) / 1.5f;
            if (Mathf.chanceDelta(0.2f * leakAmount)) DustedFx.powderLeak.at((build().tile.worldx() + next.worldx()) / 2, (build().tile.worldy() + next.worldy()) / 2, build().rotdeg(), powder);
            Units.nearby(Tmp.r1.set(Geometry.d4x(build().rotation) * 8 + build().x, Geometry.d4y(build().rotation) * 8 + build().y, 8, 8), u -> {
                if (u.isGrounded()) u.apply(powder.effect, leakAmount * 60);
            });
            powderModule().remove(powder, leakAmount);
        }

        return 0;
    }
}
