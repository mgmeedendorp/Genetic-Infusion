package seremis.geninfusion.api.util.structure;

import seremis.geninfusion.api.util.Coordinate3D;

import java.util.List;

public class StructureBlockChangeable extends StructureBlock {

    List<IStructureBlock> changeableBlocks;

    public StructureBlockChangeable(List<IStructureBlock> changeableBlocks, Coordinate3D position, boolean mustExist) {
        super(changeableBlocks.get(0).getBlock(), 0, position, mustExist);
        this.changeableBlocks = changeableBlocks;
    }

    public StructureBlockChangeable(List<IStructureBlock> changeableBlocks, Coordinate3D position) {
        this(changeableBlocks, position, true);
    }

    @Override
    public IStructureBlock copy() {
        return new StructureBlockChangeable(changeableBlocks, position.clone(), needsExistance);
    }

    @Override
    public List<IStructureBlock> getReplaceableBlocks() {
        return changeableBlocks;
    }
}
