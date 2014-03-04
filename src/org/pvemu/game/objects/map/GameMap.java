package org.pvemu.game.objects.map;

import org.pvemu.game.ActionsHandler.Action;
import org.pvemu.game.objects.GameNpc;
import org.pvemu.game.objects.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.MapModel;
import org.pvemu.models.MapNpcs;
import org.pvemu.models.Trigger;
import org.pvemu.models.dao.DAOFactory;

public final class GameMap {
    final private MapModel model;
    final private ArrayList<MapCell> cells = new ArrayList<>(150); //300 cells. devrait allez pour la plupart des maps
    short id;
    final private ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<>();
    private String mapDataPacket = null;
    final private ConcurrentHashMap<Integer, GMable> gms = new ConcurrentHashMap<>();
    private int lastGMId = 0;

    public GameMap(MapModel model) {
        this.model = model;
        id = this.model.id;

        for (int f = 0; f < this.model.mapData.length(); f += 10) {
            String CellData = this.model.mapData.substring(f, f + 10);
            cells.add(new MapCell(this, (short) (f / 10), CellData));
        }
        
        this.model.mapData = null;

        for (Trigger T : DAOFactory.trigger().getByMapID(id)) {
            MapCell cell = getCellById(T.cellID);

            if (cell != null) {
                cell.addTrigger(T);
            }
        }
        
        for(MapNpcs MN : DAOFactory.mapNpcs().getByMapId(id)){
            lastGMId--;
            gms.put(lastGMId, new GameNpc(MN, lastGMId));
        }
    }

    /**
     * Ajoute un joueur à la map
     *
     * @param p
     * @param cellID
     */
    public void addPlayer(Player p, short cellID) {
        players.put(p.getID(), p);
        gms.put(p.getID(), p);
        getCellById(cellID)._players.put(p.getID(), p);
    }

    /**
     * Supprime un joueur de la map
     *
     * @param p
     */
    public void removePlayer(Player p) {
        players.remove(p.getID());
        gms.remove(p.getID());
        if (p.getCell() != null) {
            p.getCell()._players.remove(p.getID());
        }
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }
    
    /**
     * Retourne le liste des GMables (pour envoyer packet GM par exmple)
     * @return 
     */
    public Collection<GMable> getGMables(){
        return gms.values();
    }

    public MapModel getModel() {
        return model;
    }
    
    /**
     * Retourn le GMable d'id indiqué
     * @param id
     * @return 
     */
    public GMable getGMable(int id){
        return gms.get(id);
    }

    /**
     * Retourne la cellule par son ID, si elle existe
     *
     * @param id
     * @return
     */
    public MapCell getCellById(short id) {
        if (cells.size() < id) {
            Loggin.debug("CellID invalide : %d, max : %d", id, cells.size());
            return null;
        }

        return cells.get(id);
    }

    public byte getWidth() {
        return model.width;
    }

    public byte getHeigth() {
        return model.heigth;
    }

    /**
     * Packet pour charger la map
     *
     * @return
     */
    /*public String getMapDataPacket() {
        if (mapDataPacket == null) {
            StringBuilder p = new StringBuilder();
            p.append(id).append("|").append(model.date);
            if(Constants.DOFUS_VER_ID >= 1100){
                p.append("|").append(model.key);
            }
            mapDataPacket = p.toString();
        }
        return mapDataPacket;
    }*/

    /**
     * Vérifie si la destination est valide ou non
     *
     * @param mapID
     * @param cellID
     * @return
     */
    public static boolean isValidDest(short mapID, short cellID) {
        GameMap map = DAOFactory.map().getById(mapID).getGameMap();

        if (map == null) { //map inexistante
            return false;
        }

        if (map.cells.size() < cellID) { //cellule inexistante
            return false;
        }

        return map.cells.get(cellID).isWalkable();
    }

    public short getID() {
        return id;
    }
}
