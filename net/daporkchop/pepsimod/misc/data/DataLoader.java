// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.misc.data;

import net.daporkchop.pepsimod.util.render.Texture;
import java.io.UnsupportedEncodingException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import com.google.gson.JsonParser;
import java.io.OutputStream;
import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.Image;
import javax.swing.ImageIcon;
import net.daporkchop.pepsimod.util.PepsiUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import net.daporkchop.lib.common.function.io.IOSupplier;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import java.util.function.BiConsumer;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.Set;
import java.util.function.Function;
import com.google.gson.JsonElement;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.net.URL;
import net.daporkchop.pepsimod.PepsimodMixinLoader;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;
import net.daporkchop.lib.common.function.io.IOFunction;
import com.google.gson.JsonObject;
import java.io.File;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class DataLoader extends PepsiConstants
{
    protected final String resourcesUrl;
    protected final File cache;
    protected final JsonObject root;
    protected IOFunction<String, InputStream> readerFunction;
    public final Groups groups;
    public final Map<String, String> localeKeys;
    public final MainMenu mainMenu;
    
    public DataLoader(final String resourcesUrl, final File cache) {
        this.groups = new Groups();
        this.localeKeys = new HashMap<String, String>();
        this.mainMenu = new MainMenu();
        this.resourcesUrl = Objects.requireNonNull(resourcesUrl, "resourcesUrl");
        this.cache = cache;
        if (!cache.exists() && !cache.mkdirs()) {
            throw new IllegalStateException(String.format("Unable to create directory: %s", cache.getAbsolutePath()));
        }
        this.root = this.getResourcesRoot();
        final String baseurl = this.root.get("baseurl").getAsString();
        final URL url;
        final Object o;
        final File file;
        final InputStream in;
        final Object o2;
        this.readerFunction = (IOFunction<String, InputStream>)(PepsimodMixinLoader.isObfuscatedEnvironment ? (s -> {
            new URL(String.format("%s%s", o, s));
            return url.openStream();
        }) : (s -> {
            // new(java.io.BufferedInputStream.class)
            // new(java.io.FileInputStream.class)
            new File(String.format("../resources/%s", s));
            new FileInputStream(file);
            new BufferedInputStream(in);
            return o2;
        }));
    }
    
    public void load() {
        final JsonObject data = this.root.get("data").getAsJsonObject();
        if (data.has("groups")) {
            final JsonObject json = this.readJson(data.get("groups").getAsString());
            this.groups.close();
            int color;
            JsonObject colorJson;
            final Groups groups;
            final Group group;
            StreamSupport.stream((Spliterator<Object>)json.getAsJsonArray("groups").spliterator(), false).map((Function<? super Object, ?>)JsonElement::getAsString).map((Function<? super Object, ?>)this::readJson).forEach(object -> {
                color = 0;
                if (object.has("color")) {
                    colorJson = object.getAsJsonObject("color");
                    color = (colorJson.get("r").getAsInt() << 16 | colorJson.get("g").getAsInt() << 8 | colorJson.get("b").getAsInt());
                }
                groups = this.groups;
                new Group(object.get("id").getAsString(), object.has("name") ? object.get("name").getAsString() : null, (Set<UUID>)StreamSupport.stream((Spliterator<Object>)object.getAsJsonArray("members").spliterator(), false).map((Function<? super Object, ?>)JsonElement::getAsString).map((Function<? super Object, ?>)UUID::fromString).collect((Collector<? super Object, ?, Set<? super Object>>)Collectors.toSet()), color, object.has("cape") ? this.readTexture(object.get("cape").getAsString()) : null, object.has("icon") ? this.readTexture(object.get("icon").getAsString()) : null);
                groups.addGroup(group);
                return;
            });
        }
        if (data.has("lang")) {
            final JsonObject json = this.readJson(data.get("lang").getAsString());
            final Map<String, String> internalMap = ReflectionStuff.getLanguageMapMap();
            final Map<String, String> ourMap = new HashMap<String, String>();
            for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject("translations").entrySet()) {
                ourMap.put(entry.getKey(), entry.getValue().getAsString());
            }
            this.localeKeys.forEach(internalMap::remove);
            internalMap.putAll(ourMap);
            this.localeKeys.clear();
            this.localeKeys.putAll(ourMap);
        }
        if (data.has("mainmenu")) {
            final JsonObject json = this.readJson(data.get("mainmenu").getAsString());
            this.mainMenu.setup(StreamSupport.stream((Spliterator<Object>)json.getAsJsonArray("splashes").spliterator(), false).map((Function<? super Object, ?>)JsonElement::getAsString).toArray(String[]::new), this.readTexture(json.get("banner").getAsString()));
        }
    }
    
    public Group getGroup(final EntityPlayer entity) {
        return this.getGroup(entity.getGameProfile());
    }
    
    public Group getGroup(final NetworkPlayerInfo info) {
        return this.getGroup(info.getGameProfile());
    }
    
    public Group getGroup(final GameProfile profile) {
        return this.getGroup(profile.getId());
    }
    
    public Group getGroup(final UUID uuid) {
        return this.groups.playerToGroup.get(uuid);
    }
    
    protected JsonObject getResourcesRoot() {
        if (PepsimodMixinLoader.isObfuscatedEnvironment) {
            return this.readJson(this.read("/resources.json", () -> new URL(this.resourcesUrl).openStream(), new File(this.cache, "resources.json")));
        }
        final FileInputStream in;
        final Object o;
        return this.readJson(this.read("/resources.json", () -> {
            // new(java.io.BufferedInputStream.class)
            new FileInputStream(new File("../resources/resources.json"));
            new BufferedInputStream(in);
            return o;
        }, new File(this.cache, "resources.json")));
    }
    
    protected byte[] read(final String path) {
        return this.read(path, () -> this.readerFunction.apply(path), new File(this.cache, path.replace('/', File.separatorChar)));
    }
    
    protected byte[] read(final String path, final IOSupplier<InputStream> inSupplier, final File cached) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Label_0303: {
                try (final InputStream in = inSupplier.get()) {
                    int i;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                }
                catch (IOException e2) {
                    if (cached.exists()) {
                        baos.reset();
                        try {
                            try (final InputStream in2 = new BufferedInputStream(new FileInputStream(cached))) {
                                int j;
                                while ((j = in2.read()) != -1) {
                                    baos.write(j);
                                }
                            }
                            break Label_0303;
                        }
                        catch (IOException e1) {
                            throw new RuntimeException(e1);
                        }
                    }
                    throw new RuntimeException(e2);
                }
            }
            final byte[] b = baos.toByteArray();
            try {
                if (!cached.exists()) {
                    final File parent = cached.getParentFile();
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IllegalStateException(String.format("Unable to create directory: %s", parent.getAbsolutePath()));
                    }
                    if (!cached.createNewFile()) {
                        throw new IllegalStateException(String.format("Unable to create file: %s", cached.getAbsolutePath()));
                    }
                }
                try (final OutputStream out = new FileOutputStream(cached)) {
                    out.write(b);
                }
            }
            catch (IOException e3) {
                throw new RuntimeException(e3);
            }
            return b;
        }
        catch (RuntimeException e4) {
            e4.printStackTrace();
            JOptionPane.showMessageDialog(null, String.format("Unable to load resource: \"%s\"", path), "pepsimod load error", 0, new ImageIcon(PepsiUtils.PEPSI_LOGO));
            throw e4;
        }
    }
    
    protected JsonObject readJson(final byte[] in) {
        try {
            return new JsonParser().parse((Reader)new InputStreamReader(new ByteArrayInputStream(in), "UTF-8")).getAsJsonObject();
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("go buy a computer that isn't shit", e);
        }
    }
    
    protected JsonObject readJson(final String path) {
        return this.readJson(this.read(path));
    }
    
    protected Texture readTexture(final String path) {
        try {
            return new Texture(this.read(path));
        }
        catch (IOException e) {
            throw new RuntimeException(String.format("Unable to parse texture: %s", path), e);
        }
    }
}
