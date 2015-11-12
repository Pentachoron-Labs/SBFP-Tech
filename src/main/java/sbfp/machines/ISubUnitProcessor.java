package sbfp.machines;

import java.util.List;


public interface ISubUnitProcessor {
    
    public List<IProcessor> getSubUnits();
    
    public ContainerSB setContainer(ContainerSB c);
}
