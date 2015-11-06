package sbfp.machines.foundry;

import net.minecraft.util.IStringSerializable;

public enum FoundryStates implements IStringSerializable {

    DISCONNECTED("disconnected", 0), CONNECTED("connected", 1);
    private final String name;
    private final int meta;
    private final String modelName;

    private FoundryStates(String name, int metadata) {
        this.name = name;
        this.meta = metadata;
        this.modelName = "sbfp:blockFoundry" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getMeta() {
        return this.meta;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String getModelResourceName() {
        return this.modelName;
    }

    private static final FoundryStates[] STATES_BY_META = new FoundryStates[FoundryStates.values().length];

    static {
        for (FoundryStates foundry : FoundryStates.values()) {
            STATES_BY_META[foundry.getMeta()] = foundry;
        }
    }

    public static FoundryStates stateFromMeta(int meta) {
        if (meta >= STATES_BY_META.length || meta < 0) {
            meta = 0;
        }
        return STATES_BY_META[meta];
    }

}
