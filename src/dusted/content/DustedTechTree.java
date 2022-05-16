package dusted.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.content.TechTree.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static dusted.content.DustedBlocks.*;

//TODO add sector completion to requirements
public class DustedTechTree {
    private static TechNode context = null;

    public static void load() {
        entryNode(SectorPresets.groundZero, () -> {
            //node(DustedSectorPresets.dormantChasm);
        });

        entryNode(Items.copper, () -> {
            nodeProduce(DustedPowders.titaniumPowder, () -> {
                nodeProduce(DustedPowders.cavnenDust, () -> {});
                nodeProduce(DustedPowders.quartzDust, () -> {
                    nodeProduce(DustedPowders.cafraegen, () -> {});
                    nodeProduce(DustedPowders.pyreol, () -> {});
                });
            });
            nodeProduce(DustedItems.plastel, () -> {
                //nodeProduce(DustedItems.telonate, () -> {});
                //nodeProduce(DustedItems.shirrote, () -> {});
            });
        });

        entryNode(Blocks.powerNode, () -> {
            node(powerElectrode);
        });

        entryNode(Blocks.mechanicalDrill, () -> {
            node(pneumaticFunnel, () -> {
                node(chute, () -> {
                    node(powderJunction, () -> {
                        node(powderRouter);
                    });
                    node(bridgeChute);
                });

                node(rotaryFunnel, () -> {
                    node(blastFunnel);
                });
            });
        });

        entryNode(Blocks.graphitePress, () -> {
            node(graphiteCompactor, () -> {
                node(titaniumMill, () -> {});
                node(quartzExtractor, () -> {});
            });
        });

        entryNode(Blocks.scorch, () -> {
            node(spume, () -> {
                node(abrade, () -> {
                    node(degrade);
                });
                node(scald, () -> {
                    node(coruscate, () -> {
                        node(cauterize);
                        node(pyrexia);
                    });
                });
            });
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
