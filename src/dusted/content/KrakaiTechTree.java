package dusted.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.game.Objectives.*;

import static dusted.content.DustedBlocks.*;
import static dusted.content.DustedSectorPresets.*;
import static mindustry.content.TechTree.*;

public class KrakaiTechTree {
    public static void load() {
        Objective tmpNever = new Research(Items.fissileMatter);

        DustedPlanets.krakai.techTree = nodeRoot("krakai", coreAbate, () -> {
            node(transferLink, () -> {
                node(pneumaticFunnel, Seq.with(new OnSector(taintedValley)), () -> {
                    node(chute, Seq.with(new OnSector(taintedValley)), () -> {
                        node(powderJunction, () -> {
                            node(powderRouter);
                            node(bridgeChute, () -> {
                                node(armoredChute, Seq.with(tmpNever), () -> {
                                });
                            });
                        });
                    });
                });

                node(transferTower, Seq.with(tmpNever), () -> {});
            });

            node(pressureDrill, () -> {
                node(ignitionDrill, Seq.with(new OnSector(taintedValley)), () -> {});
            });

            node(magmaticGenerator, () -> {
                node(decaySuppressor);
            });

            node(abrade, () -> {
                node(zirconWall, () -> {
                    node(zirconWallLarge);

                    node(antimonyWall, () -> {
                        node(antimonyWallLarge);
                    });
                });

                node(scald, Seq.with(new OnSector(taintedValley)), () -> {
                    node(coruscate, Seq.with(tmpNever), () -> {
                        node(strike);
                        node(blight);
                    });
                });
                node(sunder, Seq.with(tmpNever), () -> {});
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
                    nodeProduce(DustedItems.antimony, () -> {
                        nodeProduce(DustedItems.platinum, () -> {
                            nodeProduce(DustedItems.telonate, () -> {});
                        });

                        nodeProduce(Items.sand, () -> {
                            nodeProduce(Items.silicon, () -> {

                            });

                            nodeProduce(Items.metaglass, () -> {
                                nodeProduce(Liquids.slag, () -> {});
                                nodeProduce(DustedItems.rockwool, () -> {});
                            });
                        });
                    });

                    nodeProduce(DustedPowders.orchar, () -> {
                        nodeProduce(DustedPowders.quartzDust, () -> {});
                        nodeProduce(DustedPowders.sulfur, () -> {});
                    });
                });
            });

            node(outbreak, () -> {
                node(taintedValley, Seq.with(new SectorComplete(outbreak)), () -> {});
            });
        });
    }
}
