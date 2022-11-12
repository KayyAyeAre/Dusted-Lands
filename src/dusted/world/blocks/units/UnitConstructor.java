package dusted.world.blocks.units;

import arc.*;
import arc.graphics.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import dusted.world.blocks.units.UnitConstructorModule.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.units.UnitAssemblerModule.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

//not sure what to call this, its like the unit assembler but with different requirements
public class UnitConstructor extends UnitAssembler {
    public int[] capacities = {};
    public Seq<Seq<UnitConstructorPlan>> plans = new Seq<>();

    public UnitConstructor(String name) {
        super(name);
        hasItems = true;
        acceptsPayload = false;
    }

    @Override
    public void init() {
        capacities = new int[Vars.content.items().size];
        for(Seq<UnitConstructorPlan> planseq : plans) {
            for (UnitConstructorPlan plan : planseq) {
                for(ItemStack stack : plan.irequirements) {
                    capacities[stack.item.id] = Math.max(capacities[stack.item.id], stack.amount * 2);
                    itemCapacity = Math.max(itemCapacity, stack.amount * 2);
                }
            }
        }
        consume(new ConsumeItemDynamic((UnitConstructorBuild e) -> e.plan().irequirements));
        super.init();
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.remove(Stat.output);
        stats.add(Stat.output, table -> {
            table.row();

            for (var planseq : plans) {
                int tier = 0;
                for (var plan : planseq) {
                    int ttier = tier;
                    table.table(Styles.grayPanel, t -> {
                        if (plan.unit.isBanned()) {
                            t.image(Icon.cancel).color(Pal.remove).size(40).pad(10);
                            return;
                        }

                        if (plan.unit.unlockedNow()) {
                            t.image(plan.unit.uiIcon).scaling(Scaling.fit).size(40).pad(10f).left();
                            t.table(info -> {
                                info.defaults().left();
                                info.add(plan.unit.localizedName);
                                info.row();
                                info.add(Strings.autoFixed(plan.time / 60f, 1) + " " + Core.bundle.get("unit.seconds")).color(Color.lightGray);
                                if (ttier > 0) {
                                    info.row();
                                    info.add(Stat.moduleTier.localized() + ": " + ttier).color(Color.lightGray);
                                }
                            }).left();

                            t.table(req -> {
                                req.right();
                                for (int i = 0; i < plan.irequirements.length; i++) {
                                    if (i % 6 == 0) {
                                        req.row();
                                    }

                                    ItemStack stack = plan.irequirements[i];
                                    req.add(new ItemImage(stack)).pad(5);
                                }
                            }).right().grow().pad(10f);
                        } else {
                            t.image(Icon.lock).color(Pal.darkerGray).size(40).pad(10);
                        }
                    }).growX().pad(5);
                    table.row();
                    tier++;
                }
            }
        });
    }

    public static class UnitConstructorPlan extends AssemblerUnitPlan {
        public ItemStack[] irequirements;

        public UnitConstructorPlan(UnitType unit, float time, ItemStack[] requirements) {
            super(unit, time, Seq.with());
            irequirements = requirements;
        }
    }

    public class UnitConstructorBuild extends UnitAssemblerBuild {
        public int ptier;
        public int lastPtier = -2;
        public boolean gap, exceed;

        @Override
        public void updateTile() {
            super.updateTile();

            if(lastPtier != ptier) {
                progress = 0f;
                lastPtier = lastPtier == -2 ? -1 : ptier;
            }
        }

        @Override
        public boolean shouldConsume() {
            return enabled && !wasOccupied && Units.canCreate(team, plan().unit);
        }

        @Override
        public UnitConstructorPlan plan() {
            Seq<UnitConstructorPlan> pplans = plans.get(Math.min(ptier, plans.size - 1));
            return pplans.get(Math.min(currentTier, pplans.size - 1));
        }

        @Override
        public int getMaximumAccepted(Item item){
            return capacities[item.id];
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.get(item) < getMaximumAccepted(item) && Structs.contains(plan().irequirements, stack -> stack.item == item);
        }

        @Override
        public void handleItem(Building source, Item item) {
            super.handleItem(source, item);
            Tmp.v1.set(source instanceof UnitConstructorModuleBuild umod && modules.contains(umod) ? umod : this);
            Fx.itemTransfer.at(Tmp.v1.x, Tmp.v1.y, 0f, item.color, getUnitSpawn().cpy());
        }

        @Override
        public void display(Table table) {
            super.display(table);
            if (team != Vars.player.team()) return;

            Table t = (Table) table.getChildren().peek();

            if (exceed) {
                t.row();
                t.image(Icon.cancel).padRight(2).color(Color.scarlet);
                t.add("@moduleoverflow").width(190f).wrap();
            } else if (gap) {
                t.row();
                t.image(Icon.cancel).padRight(2).color(Color.scarlet);
                t.add("@missingmodule").width(190f).wrap();
            }
        }

        @Override
        public void checkTier() {
            modules.sort(UnitAssemblerModuleBuild::tier);
            int max = 0, emax = 0, set = 0;
            boolean hasGap = false;
            for(int i = 0; i < modules.size; i++) {
                var mod = modules.get(i);
                if (mod instanceof UnitConstructorModuleBuild umod) {
                    set = Math.max(set, umod.ptier());
                }

                emax = Math.max(emax, mod.tier());
                if (mod.tier() == max || mod.tier() == max + 1) {
                    max = mod.tier();
                } else {
                    hasGap = true;
                    break;
                }
            }

            gap = hasGap;
            ptier = set;
            currentTier = max;

            Seq<UnitConstructorPlan> pplans = plans.get(Math.min(ptier, plans.size - 1));
            exceed = emax >= pplans.size;
        }
    }
}
