
package sbfp.world;

import net.minecraft.util.IStringSerializable;

public enum EnumOreType implements IStringSerializable{

    MONAZITE(0, "monazite"), FLUORITE(1, "fluorite"), MOLYBDENITE(2, "mno2"), //This is actually MoS2, but I'm an idiot... fix all the names later
    RUTILE(3, "rutile"), CINNABAR(4, "cinnabar"), LIMONITE(5, "limonite"), PYROLUSITE(6, "pyrolusite"), 
    ARSENOPYRITE(7, "arsenopyrite");
    private final String name;
    private final int metadata;
    private final String modelName;
    private EnumOreType(int meta, String name){
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
    private static final EnumOreType[] TYPES_BY_META = new EnumOreType[EnumOreType.values().length];
    static{
        for(EnumOreType ore : EnumOreType.values()){
            TYPES_BY_META[ore.getMeta()] = ore;
        }
    }
    public static EnumOreType typeFromMeta(int meta){
        if(meta>=TYPES_BY_META.length||meta<0) meta = 0;
        return TYPES_BY_META[meta];
    }
}
