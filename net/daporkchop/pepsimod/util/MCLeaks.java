// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import javax.annotation.Nullable;
import io.netty.util.concurrent.Future;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.Packet;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import java.math.BigInteger;
import net.minecraft.util.CryptManager;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import com.google.gson.JsonObject;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.IOException;
import com.google.gson.JsonParser;
import java.net.URL;

public class MCLeaks
{
    public static final URL redeemUrl;
    public static final URL joinUrl;
    
    public static RedeemResponse redeemToken(final String token) {
        try {
            final String response = HTTPUtils.performPostRequest(MCLeaks.redeemUrl, "{ \"token\": \"" + token + "\" }", "application/json");
            final JsonObject json = new JsonParser().parse(response).getAsJsonObject();
            final JsonObject result = json.getAsJsonObject("result");
            return new RedeemResponse(result.get("mcname").getAsString(), result.get("session").getAsString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e2) {
            JOptionPane.showMessageDialog(null, "Invalid or expired token!", "MCLeaks Error", 0);
        }
        return new RedeemResponse();
    }
    
    public static void joinServerStuff(final SPacketEncryptionRequest pck, final NetworkManager mgr) {
        try {
            final SecretKey secretkey = CryptManager.createNewSharedKey();
            final String s = pck.getServerId();
            final PublicKey publickey = pck.getPublicKey();
            final String serverhash = new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey)).toString(16);
            final String request = "{ \"session\": \"" + Minecraft.getMinecraft().getSession().getToken() + "\", \"mcname\": \"" + Minecraft.getMinecraft().getSession().getUsername() + "\", \"serverhash\": \"" + serverhash + "\", \"server\": " + '\"' + ((Minecraft.getMinecraft().getCurrentServerData().serverIP.split(":").length == 1) ? (Minecraft.getMinecraft().getCurrentServerData().serverIP + ":25565") : Minecraft.getMinecraft().getCurrentServerData().serverIP) + "\" }";
            FMLLog.log.info(request);
            final String result = HTTPUtils.performPostRequest(MCLeaks.joinUrl, request, "application/json");
            FMLLog.log.info(result);
            final JsonObject json = new JsonParser().parse(result).getAsJsonObject();
            if (!json.get("success").getAsBoolean()) {
                mgr.closeChannel((ITextComponent)new TextComponentString("§c§lError validating §9MCLeaks §ckey!"));
            }
            mgr.sendPacket((Packet)new CPacketEncryptionResponse(secretkey, publickey, pck.getVerifyToken()), p_operationComplete_1_ -> mgr.enableEncryption(secretkey), new GenericFutureListener[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    static {
        redeemUrl = HTTPUtils.constantURL("http://auth.mcleaks.net/v1/redeem");
        joinUrl = HTTPUtils.constantURL("http://auth.mcleaks.net/v1/joinserver");
    }
    
    public static class RedeemResponse
    {
        public boolean success;
        @Nullable
        private String name;
        @Nullable
        private String session;
        
        public RedeemResponse() {
            this.success = false;
        }
        
        public RedeemResponse(final String n, final String s) {
            this.success = true;
            this.name = n;
            this.session = s;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getSession() {
            return this.session;
        }
    }
}
