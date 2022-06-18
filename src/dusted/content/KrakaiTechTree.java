package dusted.content;

import mindustry.content.*;

import static dusted.content.DustedBlocks.*;
import static dusted.content.DustedUnitTypes.*;
import static mindustry.content.TechTree.*;

public class KrakaiTechTree {

    public static void load() {
        DustedPlanets.krakai.techTree = nodeRoot("krakai", coreAbate, () -> {
            node(transferLink, () -> {
                node(chute, () -> {
                    node(powderJunction, () -> {
                        node(powderRouter, () -> {
                            node(bridgeChute);
                            node(denseChute, () -> {
                                node(armoredChute);
                            });
                        });
                    });
                });
            });

            node(pneumaticFunnel, () -> {
                node(rotaryFunnel, () -> {
                    node(blastFunnel);
                });
            });

            node(powerElectrode, () -> {
                node(pressureBurner, () -> {
                    node(stasisProjector);
                });
            });

            node(cavnenTerraConstructor, () -> {
                node(quail);
                node(pique, () -> {
                    node(rancor, () -> {
                        node(animus);
                    });
                });

                node(cavnenAerialConstructor, () -> {
                    node(carom, () -> {
                        node(recur, () -> {
                            node(saltate);
                        });
                    });
                });

                node(binaryRestructurer, () -> {
                    node(ternaryRestructurer);
                });
            });

            node(graphiteCompactor, () -> {
                node(quartzExtractor, () -> {
                    node(siliconForge);
                    node(pyresinCondenser);
                    node(cafraegenRadiator);
                });
                node(titaniumMill);
            });

            node(plastelWall, () -> {
                node(plastelWallLarge);
            });

            node(rive, () -> {
                node(abrade, () -> {
                    node(bisect);
                    node(spume);
                });
                node(scald, () -> {
                    node(coruscate, () -> {
                        node(cauterize);
                    });
                });
            });

            nodeProduce(DustedItems.plastel, () -> {
                nodeProduce(DustedItems.arsenic, () -> {
                    nodeProduce(Items.graphite, () -> {
                        nodeProduce(Items.titanium, () -> {
                            nodeProduce(DustedItems.pyresin, () -> {});
                            nodeProduce(Items.thorium, () -> {
                                nodeProduce(DustedItems.telonate, () -> {});
                            });
                        });
                    });
                });

                nodeProduce(Items.sand, () -> {});

                nodeProduce(DustedPowders.cavnenDust, () -> {
                    nodeProduce(DustedPowders.quartzDust, () -> {
                        nodeProduce(DustedPowders.cafraegen, () -> {});
                    });

                    nodeProduce(DustedPowders.pyreol, () -> {});
                    nodeProduce(DustedPowders.titaniumPowder, () -> {});
                });
            });

            node(DustedSectorPresets.cariousOutset);
        });
    }
}
