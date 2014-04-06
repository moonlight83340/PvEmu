/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player.classes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Sadida extends ClassData{

    public Sadida() {
        addSpell(1, 183);
        addSpell(1, 200);
        addSpell(1, 193);
        addSpell(3, 198);
        addSpell(6, 195);
        addSpell(9, 182);
        addSpell(13, 192);
        addSpell(17, 197);
        addSpell(21, 189);
        addSpell(26, 181);
        addSpell(31, 199);
        addSpell(36, 191);
        addSpell(42, 186);
        addSpell(48, 196);
        addSpell(54, 190);
        addSpell(60, 194);
        addSpell(70, 185);
        addSpell(80, 184);
        addSpell(90, 188);
        addSpell(100, 187);
        addSpell(200, 1910);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_SADIDA;
    }

    @Override
    public short getStartMap() {
        return 10279;
    }

    @Override
    public short getStartCell() {
        return 270;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7395;
    }

    @Override
    public short getAstrubStatueCell() {
        return 357;
    }
    
}
