// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.gui.mcleaks;

import java.io.IOException;
import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.PepsiConstants;
import net.daporkchop.pepsimod.util.AccountManager;
import net.minecraft.util.Session;
import com.google.gson.JsonParser;
import net.daporkchop.pepsimod.util.HTTPUtils;
import net.daporkchop.pepsimod.util.MCLeaks;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenMCLeaks extends GuiScreen
{
    public Minecraft mc;
    public GuiScreen prevScreen;
    private GuiTextField tokenField;
    
    public GuiScreenMCLeaks(final GuiScreen screen, final Minecraft minecraft) {
        this.mc = minecraft;
        this.prevScreen = screen;
    }
    
    public void updateScreen() {
        this.tokenField.updateCursorCounter();
    }
    
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Log in"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 2, this.height / 4 + 120 + 18, 98, 20, "Back"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + 18, 98, 20, "Original"));
        (this.tokenField = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100, 106, 200, 20)).setMaxStringLength(128);
        this.tokenField.setText("");
        this.buttonList.get(0).enabled = !this.tokenField.getText().isEmpty();
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayGuiScreen(this.prevScreen);
            }
            else if (button.id == 0) {
                final MCLeaks.RedeemResponse response = MCLeaks.redeemToken(this.tokenField.getText());
                if (response.success) {
                    final String idJson = HTTPUtils.performGetRequest(HTTPUtils.constantURL("https://api.mojang.com/users/profiles/minecraft/" + response.getName()));
                    final JsonObject json = new JsonParser().parse(idJson).getAsJsonObject();
                    final String UUID = json.get("id").getAsString();
                    final Session session = new Session(response.getName(), UUID, response.getSession(), "mojang");
                    try {
                        new AccountManager().setSession(session);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.tokenField.setText("");
                }
                PepsiConstants.pepsimod.isMcLeaksAccount = true;
            }
            else if (button.id == 2 && PepsiConstants.pepsimod.originalSession != null) {
                try {
                    new AccountManager().setSession(PepsiConstants.pepsimod.originalSession);
                    PepsiConstants.pepsimod.isMcLeaksAccount = false;
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        this.tokenField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 15) {
            this.tokenField.setFocused(!this.tokenField.isFocused());
        }
        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.tokenField.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.buttonList.get(0).enabled = !this.tokenField.getText().isEmpty();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "§9§lMCLeaks login", this.width / 2, 17, 16777215);
        this.drawCenteredString(this.fontRenderer, "Username: " + this.mc.getSession().getUsername(), this.width / 2, 27, 10526880);
        this.drawCenteredString(this.fontRenderer, "UUID: " + this.mc.getSession().getPlayerID(), this.width / 2, 37, 10526880);
        this.tokenField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
