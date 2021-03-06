package org.pvemu.game.objects.dep;

import org.pvemu.game.objects.dep.Stats.Element;

public abstract class Creature {

    protected Stats baseStats = new Stats();
    protected short level;
    protected short gfxID;
    protected String[] colors = new String[3];
    protected String name;

    /**
     * Get only the base stats
     * @return 
     */
    public Stats getBaseStats() {
        return baseStats;
    }

    /**
     * Get all the stats
     * @return 
     */
    abstract public Stats getTotalStats();

    /**
     * Retourne le nom de la créature
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne l'initiative
     *
     * @return
     */
    public Short getInitiative() {
        short fact = 4;
        short pvmax = 100;
        short pv = 100;
        /*if (_classe == Constants.CLASS_SACRIEUR) {
         fact = 8;
         }*/
        double coef = pvmax / fact;

        coef += getTotalStats().get(Element.INIT);
        coef += getTotalStats().get(Element.AGILITE);
        coef += getTotalStats().get(Element.CHANCE);
        coef += getTotalStats().get(Element.INTEL);
        coef += getTotalStats().get(Element.FORCE);

        short init = 1;
        if (pvmax != 0) {
            init = (short) (coef * ((double) pv / (double) pvmax));
        }
        if (init < 0) {
            init = 0;
        }
        return init;
    }

    public Short getLevel() {
        return level;
    }

    public Short getGfxID() {
        return gfxID;
    }

    public String[] getColors() {
        return colors;
    }
}
