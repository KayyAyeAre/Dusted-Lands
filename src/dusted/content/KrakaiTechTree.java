package dusted.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.game.Objectives.*;

import static dusted.content.DustedBlocks.*;
import static mindustry.content.TechTree.*;

public class KrakaiTechTree {
    public static void load() {
        Objective tmpNever = new Research(Items.fissileMatter);

        DustedPlanets.krakai.techTree = nodeRoot("krakai", coreAbate, () -> {
            node(transferLink, () -> {
                node(chute, () -> {

                    node(powderJunction, () -> {
                        node(powderRouter);
                        node(bridgeChute, () -> {
                            node(armoredChute, Seq.with(tmpNever), () -> {});
                        });
                    });
                });

                node(transferTower, Seq.with(tmpNever), () -> {});
            });

            node(pressureDrill);

            node(magmaticGenerator, () -> {
                node(decaySuppressor);
            });

            node(abrade, () -> {
                node(zirconWall, () -> {
                    node(zirconWallLarge);
                });

                node(sunder, Seq.with(tmpNever), () -> {});
                node(bisect, Seq.with(tmpNever), () -> {});
                node(scald, Seq.with(tmpNever), () -> {
                    node(coruscate, () -> {
                        node(strike);
                        node(blight);
                    });
                });
            });

            node(quartzExtractor, Seq.with(tmpNever), () -> {
                node(metaglassFurnace, () -> {
                    node(rockwoolExtruder);
                });
                node(siliconForge);
            });

            node(coreDissent, Seq.with(tmpNever), () -> {
                node(coreDecadence);
            });

            nodeProduce(DustedItems.zircon, () -> {
                nodeProduce(DustedItems.arsenic, () -> {
                    nodeProduce(Items.sand, () -> {
                        nodeProduce(Items.silicon, () -> {
                            nodeProduce(DustedItems.antimony, () -> {
                                nodeProduce(DustedItems.platinum, () -> {
                                    nodeProduce(DustedItems.telonate, () -> {});
                                });
                            });
                        });

                        nodeProduce(Items.metaglass, () -> {
                            nodeProduce(Liquids.slag, () -> {});
                            nodeProduce(DustedItems.rockwool, () -> {});
                        });

                        nodeProduce(DustedPowders.quartzDust, () -> {
                            nodeProduce(DustedPowders.orchar, () -> {});
                            nodeProduce(DustedPowders.sulfur, () -> {});
                        });
                    });
                });
            });

            node(DustedSectorPresets.cariousOutset);
        });
    }
}
