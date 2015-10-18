
package sbfp.chemistry;

import net.minecraft.util.IStringSerializable;

/**
 *
 * 
 */
public enum DyeTypes implements IStringSerializable{
    TIO2(0, "titaniumWhite"), VERMILLION(1, "vermillion"), OCHRE(2, "ochre"), ULTRAMARINE(3, "ultramarine"), MNO2(4, "manganeseBlack");
    //, GREEN(5, ""), PURPLE, ORANGE, GREY Do I even need these types?
    private final String name;
    private final int metadata;
    private DyeTypes(int meta, String name){
        this.name = name;
        this.metadata = meta;
    }
    
    @Override
    public String getName(){
        return name;
    }
    
    public int getMeta(){
        return metadata;
    }
    
    public String getModelResourceName(){
        return "sbfp:"+getName();
    }
    
    private static final DyeTypes[] TYPES_BY_META = new DyeTypes[DyeTypes.values().length];
    static{
        for (DyeTypes dye : DyeTypes.values()){
            TYPES_BY_META[dye.getMeta()] = dye;
        }
    }
    
    public static DyeTypes dyeTypeByMeta(int meta){
        if(meta<0||meta>TYPES_BY_META.length) meta = 0;
        return TYPES_BY_META[meta];
    }
}
