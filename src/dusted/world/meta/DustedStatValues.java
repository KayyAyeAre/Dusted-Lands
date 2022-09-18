package dusted.world.meta;

import arc.func.*;
import arc.struct.*;
import dusted.type.*;
import dusted.ui.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.maps.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class DustedStatValues {
    public static StatValue powder(Powder powder, float amount, boolean perSecond) {
        return table -> table.add(new PowderDisplay(powder, amount, perSecond));
    }

    public static StatValue powders(Boolf<Powder> filter, float amount, boolean perSecond) {
        return table -> {
            Seq<Powder> list = Vars.content.<Powder>getBy(ContentType.effect_UNUSED).select(p -> !p.isHidden() && filter.get(p));

            for (int i = 0; i < list.size; i++) {
                table.add(new PowderDisplay(list.get(i), amount, perSecond)).padRight(5);
                if (i != list.size - 1) {
                    table.add("/");
                }
            }
        };
    }

    //yes this is a copy of StatValue.blocks()
    public static StatValue blocksFilter(Floatf<Floor> filter, boolean floating, float scale, boolean startZero) {
        return table -> table.table(c -> {
            Runnable[] rebuild = {null};
            Map[] lastMap = {null};

            rebuild[0] = () -> {
                c.clearChildren();
                c.left();

                if (state.isGame()) {
                    Seq<Floor> blocks = Vars.content.blocks()
                            .select(block -> block instanceof Floor floor && indexer.isBlockPresent(block) && filter.get(floor) != 0 && !(floor.isDeep() && !floating))
                            .map(Block::asFloor)
                            .with(s -> s.sort(f -> filter.get(f.asFloor())));

                    if (blocks.any()) {
                        int i = 0;
                        for (Floor block : blocks) {
                            StatValues.blockEfficiency(block, filter.get(block) * scale, startZero).display(c);
                            if (++i % 5 == 0) {
                                c.row();
                            }
                        }
                    } else {
                        c.add("@none.inmap");
                    }
                } else {
                    c.add("@stat.showinmap");
                }
            };

            rebuild[0].run();

            //rebuild when map changes.
            c.update(() -> {
                Map current = state.isGame() ? state.map : null;

                if (current != lastMap[0]) {
                    rebuild[0].run();
                    lastMap[0] = current;
                }
            });
        });
    }
}
