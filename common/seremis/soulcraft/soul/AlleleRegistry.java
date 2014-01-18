package seremis.soulcraft.soul;

import java.util.HashMap;

public class AlleleRegistry {

    public static AlleleRegistry registry = new AlleleRegistry();
    
    private HashMap<String, IAlleleAction> alleles = new HashMap<String, IAlleleAction>();
    
    public void addAllele(String alleleName, IAlleleAction actions) {
        alleles.put(alleleName, actions);
    }
    
    public IAlleleAction getAction(String alleleName) {
        return alleles.get(alleleName);
    }
}
