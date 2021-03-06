package org.pvemu.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.Database;
import org.pvemu.jelly.database.FindableDAO;
import org.pvemu.models.MapNpcs;

public class MapNpcsDAO extends FindableDAO<MapNpcs> {
    private PreparedStatement getByMapIdStatement = null;
    
    public MapNpcsDAO(){
        getByMapIdStatement = Database.prepare("SELECT * FROM map_npcs WHERE mapid = ?");
    }

    @Override
    protected String tableName() {
        return "map_npcs";
    }

    @Override
    protected MapNpcs createByResultSet(ResultSet RS) {
        try {
            MapNpcs MN = new MapNpcs();
            
            MN.cellid = RS.getShort("cellid");
            MN.mapid = RS.getShort("mapid");
            MN.npcid = RS.getInt("npcid");
            MN.orientation = RS.getByte("orientation");
            
            return MN;
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger le npc !", ex);
            return null;
        }
    }
    
    public ArrayList<MapNpcs> getByMapId(short mapID){
        ArrayList<MapNpcs> list = new ArrayList<>();
        
        try {
            getByMapIdStatement.setShort(1, mapID);
            
            ResultSet RS = getByMapIdStatement.executeQuery();
            
            while(RS.next()){
                MapNpcs MN = createByResultSet(RS);
                
                if(MN != null){
                    list.add(MN);
                }
            }
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger les pnj de la map " + mapID, ex);
        }
        
        return list;
    }
    
}
