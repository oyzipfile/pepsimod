// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.daporkchop.pepsimod.util.misc.announcer.impl.TaskBasic;
import net.daporkchop.pepsimod.util.misc.announcer.MessagePrefixes;
import net.minecraft.block.Block;
import net.daporkchop.pepsimod.util.misc.announcer.impl.TaskBlock;
import net.minecraft.block.state.IBlockState;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraftforge.common.MinecraftForge;
import java.util.Iterator;
import net.daporkchop.pepsimod.util.misc.announcer.TaskType;
import net.daporkchop.pepsimod.util.misc.announcer.impl.TaskMove;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.daporkchop.pepsimod.util.config.impl.AnnouncerTranslator;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.daporkchop.pepsimod.util.misc.announcer.QueuedTask;
import java.util.Queue;
import net.daporkchop.pepsimod.module.api.TimeModule;

public class AnnouncerMod extends TimeModule
{
    public static AnnouncerMod INSTANCE;
    public Queue<QueuedTask> toSend;
    
    public AnnouncerMod() {
        super("Announcer");
        this.toSend = new ConcurrentLinkedQueue<QueuedTask>();
        AnnouncerMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (AnnouncerMod.mc.world != null && AnnouncerMod.mc.world.isRemote) {
            this.updateMS();
            if (this.hasTimePassedM(AnnouncerTranslator.INSTANCE.delay)) {
                this.updateLastMS();
                while (this.toSend.size() > 0) {
                    final QueuedTask task = this.toSend.poll();
                    if (task != null) {
                        final String msg = task.getMessage();
                        if (msg == null) {
                            continue;
                        }
                        if (AnnouncerTranslator.INSTANCE.clientSide) {
                            AnnouncerMod.mc.player.sendMessage((ITextComponent)new TextComponentString("§a" + msg));
                            break;
                        }
                        AnnouncerMod.mc.player.sendChatMessage(msg);
                        break;
                    }
                }
            }
            if (AnnouncerTranslator.INSTANCE.walk && !FreecamMod.INSTANCE.state.enabled) {
                final Iterator<QueuedTask> iterator = this.toSend.iterator();
                TaskMove task2 = null;
                while (iterator.hasNext()) {
                    final QueuedTask curr = iterator.next();
                    if (curr instanceof TaskMove) {
                        task2 = (TaskMove)curr;
                    }
                }
                if (task2 == null) {
                    this.toSend.add(new TaskMove(TaskType.WALK));
                }
                else {
                    task2.update(AnnouncerMod.mc.player.getPositionVector());
                }
            }
        }
    }
    
    @Override
    public void init() {
        AnnouncerMod.INSTANCE = this;
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.updateLastMS();
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)AnnouncerTranslator.INSTANCE.clientSide, "client", OptionCompletions.BOOLEAN, value -> {
                AnnouncerTranslator.INSTANCE.clientSide = value;
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.clientSide, "Client Sided"), new ModuleOption((T)AnnouncerTranslator.INSTANCE.join, "join", OptionCompletions.BOOLEAN, value -> {
                AnnouncerTranslator.INSTANCE.join = value;
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.join, "Join"), new ModuleOption((T)AnnouncerTranslator.INSTANCE.leave, "leave", OptionCompletions.BOOLEAN, value -> {
                AnnouncerTranslator.INSTANCE.leave = value;
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.leave, "Leave"), new ModuleOption((T)AnnouncerTranslator.INSTANCE.eat, "eat", OptionCompletions.BOOLEAN, value -> {
                AnnouncerTranslator.INSTANCE.eat = value;
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.eat, "Food"), new ModuleOption((T)AnnouncerTranslator.INSTANCE.walk, "walk", OptionCompletions.BOOLEAN, value -> {
                AnnouncerTranslator.INSTANCE.walk = value;
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.walk, "Walk"), new ModuleOption((T)AnnouncerTranslator.INSTANCE.mine, "mine", OptionCompletions.BOOLEAN, value -> {
                AnnouncerTranslator.INSTANCE.mine = value;
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.mine, "Mined"), new ModuleOption((T)AnnouncerTranslator.INSTANCE.place, "place", OptionCompletions.BOOLEAN, value -> {
                AnnouncerTranslator.INSTANCE.place = value;
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.place, "Place"), new ModuleOption((T)AnnouncerTranslator.INSTANCE.delay, "delay", OptionCompletions.INTEGER, value -> {
                AnnouncerTranslator.INSTANCE.delay = Math.max(value, 0);
                return Boolean.valueOf(true);
            }, () -> AnnouncerTranslator.INSTANCE.delay, "Delay", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 10000, 500)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
    
    public void onBreakBlock(final IBlockState state) {
        if (this.state.enabled && AnnouncerTranslator.INSTANCE.mine) {
            for (final QueuedTask task : this.toSend) {
                if (task.type == TaskType.BREAK) {
                    final TaskBlock taskBlock = (TaskBlock)task;
                    if (taskBlock.block == state.getBlock()) {
                        final TaskBlock taskBlock2 = taskBlock;
                        ++taskBlock2.count;
                        return;
                    }
                    continue;
                }
            }
            this.toSend.add(new TaskBlock(TaskType.BREAK, state.getBlock()));
            this.tick();
        }
    }
    
    public void onPlaceBlock(final Block block) {
        if (this.state.enabled && AnnouncerTranslator.INSTANCE.place) {
            for (final QueuedTask task : this.toSend) {
                if (task.type == TaskType.PLACE) {
                    final TaskBlock taskBlock = (TaskBlock)task;
                    if (taskBlock.block == block) {
                        final TaskBlock taskBlock2 = taskBlock;
                        ++taskBlock2.count;
                        return;
                    }
                    continue;
                }
            }
            this.toSend.add(new TaskBlock(TaskType.PLACE, block));
            this.tick();
        }
    }
    
    public void onPlayerJoin(final String name) {
        if (this.state.enabled && AnnouncerTranslator.INSTANCE.join) {
            final QueuedTask task = new TaskBasic(TaskType.JOIN, MessagePrefixes.getMessage(TaskType.JOIN, name));
            if (this.hasTimePassedM(2000L)) {
                this.updateLastMS();
                final String msg = task.getMessage();
                if (msg != null) {
                    if (AnnouncerTranslator.INSTANCE.clientSide) {
                        AnnouncerMod.mc.player.sendMessage((ITextComponent)new TextComponentString("§a" + msg));
                    }
                    else {
                        AnnouncerMod.mc.player.sendChatMessage(msg);
                    }
                }
            }
        }
    }
    
    public void onPlayerLeave(final String name) {
        if (this.state.enabled && AnnouncerTranslator.INSTANCE.leave) {
            final QueuedTask task = new TaskBasic(TaskType.LEAVE, MessagePrefixes.getMessage(TaskType.LEAVE, name));
            if (this.hasTimePassedM(2000L)) {
                this.updateLastMS();
                final String msg = task.getMessage();
                if (msg != null) {
                    if (AnnouncerTranslator.INSTANCE.clientSide) {
                        AnnouncerMod.mc.player.sendMessage((ITextComponent)new TextComponentString("§a" + msg));
                    }
                    else {
                        AnnouncerMod.mc.player.sendChatMessage(msg);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.toSend.clear();
    }
}
