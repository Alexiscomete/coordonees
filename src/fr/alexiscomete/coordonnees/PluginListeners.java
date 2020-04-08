package fr.alexiscomete.coordonnees;

import fr.alexiscomete.coordonnees.commands.coosCommands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PluginListeners implements Listener {
    
    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event){
        Player player = event.getPlayer();
        coosCommands.playersOnline.add(player);
        coosCommands.playerActivateWorld.add(false);
        coosCommands.playerActivateCoos.add(false);
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event){
        Player player = event.getPlayer();
        int indexOfPlayer = coosCommands.playersOnline.indexOf(player);
        coosCommands.playersOnline.remove(indexOfPlayer);
        coosCommands.playerActivateWorld.remove(indexOfPlayer);
        coosCommands.playerActivateCoos.remove(indexOfPlayer);
    }

    @EventHandler
    public void onMove(@NotNull PlayerMoveEvent event){
        Player player = event.getPlayer();
        int indexOfPlayer = coosCommands.playersOnline.indexOf(player);
        boolean test = coosCommands.playerActivateCoos.get(indexOfPlayer);
        if (test){
            coosCommands.miseAJour(player);
        }
    }
}
