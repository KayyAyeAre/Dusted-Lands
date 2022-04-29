package dusted.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.content.TechTree.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static dusted.content.DustedBlocks.*;

public class DustedTechTree {
    private static TechNode context = null;

    public static void load() {
        entryNode(Items.copper, () -> {
            nodeProduce(Powders.titaniumPowder, () -> {
                nodeProduce(Powders.cavnenDust, () -> {});
                nodeProduce(Powders.quartzDust, () -> {});
            });
            nodeProduce(DustedItems.plastel, () -> {
                nodeProduce(DustedItems.telonate, () -> {});
            });
        });

        entryNode(Blocks.thermalGenerator, () -> {
            node(crudeThermalGenerator);
        });

        entryNode(Blocks.mechanicalDrill, () -> {
            node(pneumaticVacuum, () -> {
                node(chute, Seq.with(new Research(crudeThermalGenerator)), () -> {
                    node(powderJunction, () -> {
                        node(powderRouter);
                    });
                    node(bridgeChute);
                });

                node(thermalVacuum, () -> {
                    node(blastVacuum);
                });
            });
        });

        entryNode(Blocks.graphitePress, () -> {
            node(graphiteCompactor, () -> {
                node(titaniumMill, Seq.with(new Research(crudeThermalGenerator)), () -> {});
                node(quartzExtractor, Seq.with(new Research(crudeThermalGenerator)), () -> {});
            });
        });

        entryNode(Blocks.scorch, () -> {
            //TODO add sector completion to requirements
            node(spume);
        });
    }

    private static void node(UnlockableContent content) {
        node(content, () -> {});
    }

    public static void entryNode(UnlockableContent parent, Runnable children) {
        context = TechTree.get(parent);
        children.run();
    }

    private static void node(UnlockableContent content, Runnable children) {
        node(content, content.researchRequirements(), null, children);
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children) {
        node(content, content.researchRequirements(), objectives, children);
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children) {
        TechNode node = new TechNode(context, content, requirements);
        if (objectives != null) {
            node.objectives.addAll(objectives);
        }

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void nodeProduce(UnlockableContent content, Runnable children) {
        node(content, Seq.with(new Produce(content)), children);
    }
}
