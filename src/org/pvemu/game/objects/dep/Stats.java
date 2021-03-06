package org.pvemu.game.objects.dep;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.Shell.GraphicRenditionEnum;

public class Stats{

    public enum Element {
        //basiques

        PA(new int[]{111, 120}, new int[]{101, 168}),
        PM(new int[]{78, 128}, new int[]{169, 127}),
        //primaires
        FORCE(new int[]{118}, new int[]{157}),
        INTEL(new int[]{126}, new int[]{155}),
        AGILITE(new int[]{119}, new int[]{154}),
        CHANCE(new int[]{123}, new int[]{152}),
        SAGESSE(new int[]{124}, new int[]{156}),
        VITA(new int[]{125, 110}, new int[]{153}),
        //secondaires
        DOMMAGE(new int[]{112}, new int[]{145, 220}),
        PERDOM(new int[]{138, 142}, new int[]{}),
        MAITRISE(new int[]{165}, new int[]{}),
        TRAP_DOM(new int[]{225}, new int[]{}),
        TRAP_PERDOM(new int[]{226}, new int[]{}),
        CC(new int[]{115}, new int[]{171}),
        EC(new int[]{122}, new int[]{}),
        PO(new int[]{117}, new int[]{116}),
        INIT(new int[]{174}, new int[]{175}),
        SOIN(new int[]{178}, new int[]{179}),
        //resistances

        //autres
        PODS(new int[]{158}, new int[]{159}),
        PROSPEC(new int[]{176}, new int[]{177}),
        INVOC(new int[]{182}, new int[]{}),;
        
        final private int[] add_id;
        final private int[] rem_id;

        Element(int[] add_id, int[] rem_id) {
            this.add_id = add_id;
            this.rem_id = rem_id;
        }

        /**
         * Si l'élément a une valeur négative
         *
         * @param id
         * @return
         */
        public boolean isRemove(int id) {
            if (rem_id.length == 0) {
                return false;
            }
            for (int i : rem_id) {
                if (i == id) {
                    return true;
                }
            }
            return false;
        }

        public int getId(boolean isRemove) {
            if (isRemove && rem_id.length != 0) {
                return rem_id[0];
            } else if (add_id.length != 0) {
                return add_id[0];
            }
            return -1;
        }
    }
    
    private static final HashMap<Integer, Element> intToElement = new HashMap<>();
    final private EnumMap<Element, Short> stats;
    
    public Stats(){
        stats = new EnumMap<>(Element.class);
    }
    
    public Stats(Stats other){
        stats = new EnumMap<>(other.stats);
    }

    /**
     * Ajoute qu Element aux stats courentes
     *
     * @param e
     * @param qu
     * @return
     */
    public Stats add(Element e, short qu) {
        if (stats.containsKey(e)) {
            qu += stats.get(e);
        }

        stats.put(e, qu);

        return this;
    }

    /**
     * Ajoute l'élément d'id elemId aux stats courentes
     *
     * @param elemId
     * @param qu
     * @return
     */
    public Stats add(int elemId, short qu) {
        Element e = intToElement.get(elemId);

        if (e != null) {
            if (e.isRemove(elemId)) {
                qu = (short)-qu;
            }
            add(e, qu);
        }else{
            Loggin.debug("Element inconnue : %d", elemId);
        }

        return this;
    }
    
    /**
     * Combine deux classes de stats
     * @param other
     * @return 
     */
    public Stats addAll(Stats other){
        for(Entry<Element, Short> entry : other.stats.entrySet()){
            add(entry.getKey(), entry.getValue());
        }
        
        return this;
    }

    /**
     * Retourne la quatité total de l'élément e
     *
     * @param e
     * @return
     */
    public Short get(Element e) {
        if (e != null && stats.containsKey(e)) {
            return stats.get(e);
        }
        return 0;
    }

    /**
     * Retourne la quantité total de l'élément associé à elemId
     *
     * @param elemId
     * @return
     */
    public Short get(int elemId) {
        Element e = intToElement.get(elemId);
        return get(e);
    }
    
    /**
     * Retourne toute les stats, pour for-each
     * @return 
     */
    public Set<Entry<Element, Short>> getAll(){
        return stats.entrySet();
    }

    public static void loadElements() {
        Shell.print("Chargement des élements : ", GraphicRenditionEnum.YELLOW);
        int num = 0;
        for (Element e : Element.values()) {
            for (int id : e.add_id) {
                intToElement.put(id, e);
                num++;
            }
            for (int id : e.rem_id) {
                intToElement.put(id, e);
                num++;
            }
        }
        Shell.println(num + " éléments chargées !", GraphicRenditionEnum.GREEN);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.stats);
        return hash;
    }   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stats other = (Stats) obj;
        return Objects.equals(this.stats, other.stats);
    }
    
}
