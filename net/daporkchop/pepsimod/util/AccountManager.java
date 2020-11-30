// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import com.mojang.util.UUIDTypeAdapter;
import java.lang.reflect.Field;
import net.minecraft.util.Session;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.client.Minecraft;
import java.util.UUID;
import com.mojang.authlib.UserAuthentication;

public class AccountManager extends PepsiConstants
{
    private final UserAuthentication auth;
    
    public AccountManager() {
        final UUID uuid = UUID.randomUUID();
        final AuthenticationService authService = (AuthenticationService)new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), uuid.toString());
        this.auth = authService.createUserAuthentication(Agent.MINECRAFT);
        authService.createMinecraftSessionService();
    }
    
    public void setSession(final Session s) throws Exception {
        final Class<? extends Minecraft> mc = Minecraft.getMinecraft().getClass();
        try {
            Field session = null;
            for (final Field f : mc.getDeclaredFields()) {
                if (f.getType().isInstance(s)) {
                    session = f;
                }
            }
            if (session == null) {
                throw new IllegalStateException("No field of type " + Session.class.getCanonicalName() + " declared.");
            }
            session.setAccessible(true);
            if (AccountManager.pepsimod.originalSession == null) {
                AccountManager.pepsimod.originalSession = Minecraft.getMinecraft().getSession();
            }
            session.set(Minecraft.getMinecraft(), s);
            session.setAccessible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void setUser(final String username, final String password) {
        if (Minecraft.getMinecraft().getSession().getUsername() != username || "0".equals(Minecraft.getMinecraft().getSession().getToken())) {
            this.auth.logOut();
            this.auth.setUsername(username);
            this.auth.setPassword(password);
            try {
                this.auth.logIn();
                final Session session = new Session(this.auth.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(this.auth.getSelectedProfile().getId()), this.auth.getAuthenticatedToken(), this.auth.getUserType().getName());
                this.setSession(session);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
