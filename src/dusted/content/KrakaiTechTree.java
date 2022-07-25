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
                node(rotaryFunnel);
            });

            node(powerElectrode, () -> {
                node(pressureBurner);
            });

            node(cavnenTerraConstructor, () -> {
                node(seism);
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

            node(quartzExtractor, () -> {
                node(siliconForge, () -> {
                    node(metaglassFurnace);
                });
                node(pyresinCondenser);
            });

            node(zirconWall, () -> {
                node(zirconWallLarge);
            });

            node(abrade, () -> {
                node(bisect);
            });
            node(scald, () -> {
                node(coruscate, () -> {
                });
            });

            nodeProduce(DustedItems.zircon, () -> {
                nodeProduce(DustedItems.arsenic, () -> {
                    nodeProduce(Items.graphite, () -> {
                        nodeProduce(Items.titanium, () -> {
                            nodeProduce(DustedItems.pyresin, () -> {
                            });
                            nodeProduce(Items.thorium, () -> {
                                nodeProduce(DustedItems.telonate, () -> {
                                });
                            });
                        });
                    });
                });

                nodeProduce(Items.sand, () -> {
                });

                nodeProduce(DustedPowders.cavnenDust, () -> {
                    nodeProduce(DustedPowders.quartzDust, () -> {
                    });

                    nodeProduce(DustedPowders.pyreol, () -> {
                    });
                });
            });

            node(DustedSectorPresets.cariousOutset);
        });
    }
}
