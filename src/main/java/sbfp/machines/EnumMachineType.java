
package sbfp.machines;

import net.minecraft.util.IStringSerializable;

public enum EnumMachineType implements IStringSerializable{

    CRUSHER(0, "crusher"), SOLARCHARGER(1, "solarcharger");
    
    private final String name;
    private final int metadata;
    private final String modelName;
    private EnumMachineType(int meta, String name){
        this.metadata=meta;
        this.name = name;
        this.modelName = "sbfp:block"+name.substring(0,1).toUpperCase()+name.substring(1);
    }
    @Override
    public String getName() {
        return name; //To change body of generated methods, choose Tools | Templates.
    }
    public int getMeta(){
        return metadata;
    }
    @Override
    public String toString(){
        return getName();
    }
    public String getModelResourceName(){
        return modelName;
    }
    private static final EnumMachineType[] TYPES_BY_META = new EnumMachineType[EnumMachineType.values().length];
    static{
        for(EnumMachineType machine : EnumMachineType.values()){
            TYPES_BY_META[machine.getMeta()] = machine;
        }
    }
    public static EnumMachineType typeFromMeta(int meta){
        if(meta>=TYPES_BY_META.length||meta<0) meta = 0;
        return TYPES_BY_META[meta];
    }
}
