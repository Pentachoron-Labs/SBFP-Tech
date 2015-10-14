
package sbfp.world;

import net.minecraft.util.IStringSerializable;

public enum EnumOreType implements IStringSerializable{

    ARSENOPYRITE(0, "arsenopyrite"), MN02(1, "mno2"), FLUORITE(2, "fluorite"), 
    LIMONITE(3, "limonite"), CINNABAR(4, "cinnabar"), RUTILE(5, "rutile"), MONAZITE(6, "monazite");
    private final String name;
    private final int metadata;
    private EnumOreType(int meta, String name){
        this.metadata=meta;
        this.name = name;
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
    
}
