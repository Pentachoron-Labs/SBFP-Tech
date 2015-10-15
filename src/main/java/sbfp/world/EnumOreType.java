
package sbfp.world;

import net.minecraft.util.IStringSerializable;

public enum EnumOreType implements IStringSerializable{

    MONAZITE(0, "monazite"), FLUORITE(1, "fluorite"), MN02(2, "mno2"),
    RUTILE(3, "rutile"), CINNABAR(4, "cinnabar"), LIMONITE(5, "limonite"), PYROLUSITE(6, "pyrolusite"), 
    ARSENOPYRITE(7, "arsenopyrite");
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
