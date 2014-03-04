/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.chat;

import java.util.HashMap;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class ChatHandler {
    final static private ChatHandler instance = new ChatHandler();
    final private HashMap<String, ChatChannel> channels = new HashMap<>();
    
    private ChatHandler(){
        registerChannel(new MapChannel());
        registerChannel(new RecruitmentChannel());
    }
    
    public void parse(String[] args, Player player){
        ChatChannel channel = channels.get(args[0]);
        
        if(channel == null){
            Loggin.debug("Canal de discution '%s' non trouvé !", args[0]);
            return;
        }
            
        if(!player.corresponds(channel.conditions())){
            return;
        }
        
        channel.post(args[1], player);
    }
    
    /**
     * Add a new chanel to chat system
     * @param channel the chanel to add
     */
    public void registerChannel(ChatChannel channel){
        channels.put(channel.id(), channel);
    }
    
    static public ChatHandler instance(){
        return instance;
    }
}
