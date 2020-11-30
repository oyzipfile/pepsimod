// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.scoreboard;

import org.spongepowered.asm.mixin.Overwrite;
import com.google.common.collect.Maps;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.scoreboard.ScorePlayerTeam;
import java.util.Map;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Scoreboard.class })
public abstract class MixinScoreboard
{
    @Shadow
    private final Map<String, ScorePlayerTeam> teamMemberships;
    
    public MixinScoreboard() {
        this.teamMemberships = (Map<String, ScorePlayerTeam>)Maps.newHashMap();
    }
    
    @Overwrite
    public void removePlayerFromTeam(final String username, final ScorePlayerTeam playerTeam) {
        try {
            if (this.getPlayersTeam(username) != playerTeam) {
                throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + playerTeam.getName() + "'.");
            }
            this.teamMemberships.remove(username);
            playerTeam.getMembershipCollection().remove(username);
        }
        catch (NullPointerException ex) {}
    }
    
    @Shadow
    public ScorePlayerTeam getPlayersTeam(final String username) {
        return null;
    }
}
