// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.api;

import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import java.util.function.Supplier;
import java.util.function.Function;

public class ModuleOption<T>
{
    public final Function<T, Boolean> SET;
    public final Supplier<T> GET;
    private final String[] DEFAULT_COMPLETIONS;
    private final T DEFAULT_VALUE;
    public String displayName;
    public boolean makeButton;
    public OptionExtended extended;
    private T value;
    private String name;
    
    public ModuleOption(final T defaultValue, final String name, final String[] defaultCompletions, final Function<T, Boolean> set, final Supplier<T> get, final String displayName, final OptionExtended extended, final boolean makeWindow) {
        this(defaultValue, name, defaultCompletions, (Function<Object, Boolean>)set, (Supplier<Object>)get, displayName, makeWindow);
        this.extended = extended;
    }
    
    public ModuleOption(final T defaultValue, final String name, final String[] defaultCompletions, final Function<T, Boolean> set, final Supplier<T> get, final String displayName, final OptionExtended extended) {
        this(defaultValue, name, defaultCompletions, (Function<Object, Boolean>)set, (Supplier<Object>)get, displayName);
        this.extended = extended;
    }
    
    public ModuleOption(final T defaultValue, final String name, final String[] defaultCompletions, final Function<T, Boolean> set, final Supplier<T> get, final String displayName, final boolean makeButton) {
        this(defaultValue, name, defaultCompletions, (Function<Object, Boolean>)set, (Supplier<Object>)get, displayName);
        this.makeButton = makeButton;
    }
    
    public ModuleOption(final T defaultValue, final String name, final String[] defaultCompletions, final Function<T, Boolean> set, final Supplier<T> get, final String displayName) {
        this.makeButton = true;
        this.extended = null;
        this.DEFAULT_COMPLETIONS = defaultCompletions;
        this.DEFAULT_VALUE = defaultValue;
        this.SET = set;
        this.GET = get;
        this.value = defaultValue;
        this.name = name;
        this.displayName = displayName;
    }
    
    public String getName() {
        return (this.name == null) ? this.displayName.toLowerCase() : this.name;
    }
    
    public boolean setValue(final T value) {
        return this.SET.apply(value);
    }
    
    public T getValue() {
        final T toReturn = this.GET.get();
        return (toReturn == null) ? this.getDefaultValue() : toReturn;
    }
    
    public T getDefaultValue() {
        return this.DEFAULT_VALUE;
    }
    
    public String[] defaultCompletions() {
        return this.DEFAULT_COMPLETIONS;
    }
}
