
package sbfp.machines;

import net.minecraft.util.IStringSerializable;

public enum EnumMachineType implements IStringSerializable{

    CRUSHER(0, "crusher"), SOLARCHARGER(1, "solarcharger");
    
    private final String name;
    private final int metadata;
    private EnumMachineType(int meta, String id){
        this.metadata = meta;
        this.name = id;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public int getMeta(){
        return metadata;
    }
    
    @Override
    public String toString(){
        return getName();
    }
}
