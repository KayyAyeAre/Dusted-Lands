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

        DustedPlanets.krakai.techTree = nodeRoot("krakai", coreAbate, true, () -> {
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

                node(siliconForge, Seq.with(new OnSector(magmaticPassage)), () -> {});
                node(quartzExtractor, Seq.with(new OnSector(magmaticPassage), new Research(siliconForge)), () -> {
                    node(metaglassFurnace, Seq.with(tmpNever), () -> {
                        node(crisaltSynthesizer, () -> {
                            node(deteriorationChamber);
                        });
                        node(rockwoolExtruder, () -> {
                            node(gunpowderMill);
                        });
                    });
                });
            });

            node(magmaticGenerator, () -> {
                node(decaySuppressor, () -> {
                    node(regenerationTower, Seq.with(tmpNever), () -> {});
                });

                node(liquidLink, Seq.with(tmpNever), () -> {
                    node(refractoryPump);
                });

                node(riftDischarger, Seq.with(tmpNever), () -> {});
                node(crystalConcentrator, Seq.with(tmpNever), () -> {});
                node(sulfurSiphon, Seq.with(tmpNever), () -> {
                    node(hydrogenSiphon);
                });
            });

            node(abrade, () -> {
                node(zirconWall, () -> {
                    node(zirconWallLarge);

                    node(antimonyWall, () -> {
                        node(antimonyWallLarge);

                        node(crisaltWall, Seq.with(tmpNever), () -> {
                            node(crisaltWallLarge);

                            node(perisleWall, () -> {
                                node(perisleWallLarge);
                            });
                        });
                    });
                });

                node(scald, Seq.with(new OnSector(taintedValley)), () -> {
                    node(coruscate, Seq.with(tmpNever), () -> {
                        node(strike);
                        node(clutter);
                        node(blight, () -> {
                            node(crush);
                        });
                    });
                });
                node(sunder, Seq.with(new OnSector(magmaticPassage)), () -> {});
            });

            node(coreDissent, Seq.with(tmpNever), () -> {
                node(coreDecadence);
            });

            node(witheredAssembler, Seq.with(tmpNever), () -> {
                node(DustedUnitTypes.annul);

                node(aerialAssemblerModule, () -> {
                    node(DustedUnitTypes.carom);
                });

                node(voltaicAssembler, () -> {
                    node(DustedUnitTypes.pique);
                    node(DustedUnitTypes.sob, Seq.with(new Research(aerialAssemblerModule)), () -> {});
                });

                node(blazingAssembler, () -> {
                    node(DustedUnitTypes.protei);
                });

                node(binaryAssemblerModule, Seq.with(new Research(aerialAssemblerModule)), () -> {
                    node(DustedUnitTypes.excise);
                    node(DustedUnitTypes.recur);
                    node(DustedUnitTypes.rancor);
                    node(DustedUnitTypes.wail);
                    node(DustedUnitTypes.hynobii);

                    node(largeWitheredAssembler, () -> {
                        node(DustedUnitTypes.deduct, () -> {
                            node(DustedUnitTypes.diminish);
                        });
                        node(DustedUnitTypes.saltate, () -> {
                            node(DustedUnitTypes.staccato);
                        });

                        node(largeVoltaicAssembler, () -> {
                            node(DustedUnitTypes.animus, () -> {
                                //node(DustedUnitTypes.enmity);
                            });
                            node(DustedUnitTypes.snivel, () -> {
                                //node(DustedUnitTypes.lament);
                            });
                        });

                        node(largeBlazingAssembler, () -> {
                            node(DustedUnitTypes.sirenid, () -> {
                                //node(DustedUnitTypes.pleurodel);
                            });
                        });

                        node(ternaryAssemblerModule);
                    });
                });
            });

            nodeProduce(DustedItems.zircon, () -> {
                nodeProduce(DustedItems.arsenic, () -> {
                    nodeProduce(DustedItems.antimony, () -> {
                        nodeProduce(DustedItems.platinum, () -> {
                            nodeProduce(DustedItems.perisle, () -> {});
                            nodeProduce(DustedItems.telonate, () -> {});
                        });

                        nodeProduce(Items.sand, () -> {
                            nodeProduce(Items.silicon, () -> {

                            });

                            nodeProduce(Items.metaglass, () -> {
                                nodeProduce(Liquids.slag, () -> {});
                                nodeProduce(DustedItems.crisalt, () -> {});
                                nodeProduce(DustedItems.rockwool, () -> {});
                            });
                        });
                    });

                    nodeProduce(DustedPowders.orchar, () -> {
                        nodeProduce(DustedPowders.quartzDust, () -> {});
                        nodeProduce(DustedPowders.sulfur, () -> {});
                        nodeProduce(DustedPowders.niter, () -> {
                            nodeProduce(DustedPowders.gunpowder, () -> {});
                        });
                    });
                });
            });

            node(outbreak, () -> {
                node(taintedValley, Seq.with(new SectorComplete(outbreak)), () -> {
                    node(magmaticPassage, Seq.with(new SectorComplete(taintedValley)), () -> {});
                });
            });
        });
    }
}
