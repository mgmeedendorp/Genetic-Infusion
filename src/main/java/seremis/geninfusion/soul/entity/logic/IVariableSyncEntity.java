package seremis.geninfusion.soul.entity.logic;

public interface IVariableSyncEntity {

    void syncNonPrimitives(String[] variables);

    void initVariables();
}
