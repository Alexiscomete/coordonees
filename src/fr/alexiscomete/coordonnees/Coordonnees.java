package fr.alexiscomete.coordonnees;

import fr.alexiscomete.coordonnees.commands.coosCommands;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Coordonnees extends JavaPlugin {



    @Override
    public void onEnable(){
        System.out.println("Vous êtes dans la console donc pas de coos pour vous !");
        Objects.requireNonNull(getCommand("coordonees")).setExecutor(new coosCommands());
        getServer().getPluginManager().registerEvents(new PluginListeners(), this);
        

    }
    @Override
    public void onDisable(){
        System.out.println("Extinction du plugin : coordonnees");
    }

}
