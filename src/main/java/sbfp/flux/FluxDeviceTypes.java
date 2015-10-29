

package sbfp.flux;

import net.minecraft.util.IStringSerializable;

/**
 *
 * 
 */
public enum FluxDeviceTypes implements IStringSerializable {
    //,new String[]{"redFluxAmp","redFluxAbsorber","redFluxStabilizer","chargedRedstone"}
    AMPLIFIER(0, "redFluxAmp"), ABSORBER(1, "redFluxAbsorber"), STABILIZER(2, "redFluxStabilizer"), CHARGEDREDSTONE(3, "chargedRedstone");
    private final String name;
    private final int metadata;
    private FluxDeviceTypes(int meta, String name){
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
    
    private static final FluxDeviceTypes[] TYPES_BY_META = new FluxDeviceTypes[FluxDeviceTypes.values().length];
    static{
        for (FluxDeviceTypes dye : FluxDeviceTypes.values()){
            TYPES_BY_META[dye.getMeta()] = dye;
        }
    }
    
    public static FluxDeviceTypes dyeTypeByMeta(int meta){
        if(meta<0||meta>TYPES_BY_META.length) meta = 0;
        return TYPES_BY_META[meta];
    }
}
