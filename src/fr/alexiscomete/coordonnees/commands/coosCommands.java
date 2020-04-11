package fr.alexiscomete.coordonnees.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class coosCommands implements CommandExecutor {

    public static ArrayList<Player> playersOnline = new ArrayList<>();
    public static ArrayList<Boolean> playerActivateWorld = new ArrayList<>();
    public static ArrayList<Boolean> playerActivateCoos = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("coordonees")){
            if (strings.length >= 1){
                if (strings.length >= 2){
                    if ((strings[0].equalsIgnoreCase("i") || strings[0].equalsIgnoreCase("ici")) && commandSender instanceof Player){
                        Player player = (Player)commandSender;
                        if (strings[1].equalsIgnoreCase("a") || strings[1].equalsIgnoreCase("afficher")){
                            switchAfficher(true, player);
                            return true;
                        }else if (strings[1].equalsIgnoreCase("envoyer") || strings[1].equalsIgnoreCase("e") || strings[1].equalsIgnoreCase("send") || strings[1].equalsIgnoreCase("s")){
                            player.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else if (strings[1].equalsIgnoreCase("c") || strings[1].equalsIgnoreCase("copie")){
                            player.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else {
                            commandSender.sendMessage("Erreur : sous-sous-commande invalide. Les sous-sous-commandes pour cette sous-commande sont : e,a,c");
                        }
                        return true;
                    }else if ((strings[0].equalsIgnoreCase("w") || strings[0].equalsIgnoreCase("world")) && commandSender instanceof Player){
                        Player player = (Player)commandSender;
                        if (strings[1].equalsIgnoreCase("envoyer") || strings[1].equalsIgnoreCase("e") || strings[1].equalsIgnoreCase("send") || strings[1].equalsIgnoreCase("s")){
                            player.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else if (strings[1].equalsIgnoreCase("c") || strings[1].equalsIgnoreCase("copie")){
                            player.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else {
                            commandSender.sendMessage("Erreur : sous-sous-commande invalide. Les sous-sous-commandes pour cette sous-commande sont : e,c");
                        }
                        return true;
                    }else if (strings[0].equalsIgnoreCase("c") || strings[0].equalsIgnoreCase("convertir")){
                        if (strings[1].equalsIgnoreCase("envoyer") || strings[1].equalsIgnoreCase("e") || strings[1].equalsIgnoreCase("send") || strings[1].equalsIgnoreCase("s")){
                            commandSender.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else if (strings[1].equalsIgnoreCase("c") || strings[1].equalsIgnoreCase("copie")){
                            commandSender.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else {
                            commandSender.sendMessage("Erreur : sous-sous-commande invalide. Les sous-sous-commandes pour cette sous-commande sont : e,c");
                        }
                        return true;
                    }else if ((strings[0].equalsIgnoreCase("time") || strings[0].equalsIgnoreCase("t")) && commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        if (strings[1].equalsIgnoreCase("a") || strings[1].equalsIgnoreCase("afficher")){
                            switchAfficher(false, player);
                            return true;
                        }else if (strings[1].equalsIgnoreCase("envoyer") || strings[1].equalsIgnoreCase("e") || strings[1].equalsIgnoreCase("send") || strings[1].equalsIgnoreCase("s")){
                            player.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else if (strings[1].equalsIgnoreCase("c") || strings[1].equalsIgnoreCase("copie")){
                            player.sendMessage("Commande en cours de développement :)");
                            return true;
                        }else {
                            commandSender.sendMessage("Erreur : sous-sous-commande invalide. Les sous-sous-commandes pour cette sous-commande sont : a,c,e");
                        }
                        return true;
                    }else {
                        commandSender.sendMessage("Erreur : sous-commande invalide. Les sous-commandes sont : ici/i/world/w/time/t/convertir/c");
                    }
                }else {
                    commandSender.sendMessage("Une sous-sous-commande est nécessaire : a/e/s/n/o/c");
                }
            }else {
                commandSender.sendMessage("Utilisation : ici/world/time/convertir, vous pouvez aussi mettre les initiales, des sous-commandes sont disponibles. ici = vos coos , world = ici -> nether/overworld (convertion) , time = heure en jeu , convertir = coos choisis -> nether/overworld.");
            }
            //[sous-commande ; ici(ou i)/world(ou w)/time(ou t)/convertir(ou c)] [afficher (ou a) + true/false , sauf convertir / envoyer(ou e/send/s) facultatif(+ player) , tout / copie(ou c) (shift + flèches puis ctrl + c) , tout / world de départ : (nether(ou n)/overworld(ou o)) + coos]
            return true;
        }

        return false;
    }

    public void switchAfficher(boolean WorldOuCoos, Player player){ // false pour world et true pour Coos.
        int indexOfPlayer = playersOnline.indexOf(player);
        if (WorldOuCoos){
            if (getIfPlayerActivateWorldOrCoos(player, true)){
                playerActivateCoos.set(indexOfPlayer, false);
            }else{
                playerActivateCoos.set(indexOfPlayer, true);
            }
        }else{
            if (getIfPlayerActivateWorldOrCoos(player, false)){
                playerActivateWorld.set(indexOfPlayer, false);
            }else{
                playerActivateWorld.set(indexOfPlayer, true);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int indexOfPlayer = coosCommands.playersOnline.indexOf(player);
                        boolean test = coosCommands.playerActivateWorld.get(indexOfPlayer);
                        if (test){
                            coosCommands.miseAJour(player);
                        }else{
                            timer.cancel();
                            timer.purge();
                        }
                    }
                },1000);
            }
        }
        miseAJour(player);
    }

    public static void miseAJour(Player player){
        int indexOfPlayer = playersOnline.indexOf(player);
        StringBuilder message = new StringBuilder();
        boolean testWorld = playerActivateWorld.get(indexOfPlayer);
        boolean testCoos = playerActivateCoos.get(indexOfPlayer);
        if (testCoos){
            message.append("X : ").append(player.getLocation().getX()).append(" Y : ").append(player.getLocation().getY()).append(" Z : ").append(player.getLocation().getZ()).append(" Yaw : ").append(player.getLocation().getYaw()).append(" Pitch : ").append(player.getLocation().getPitch()).append(" ");
        }
        if (testWorld){
            message.append("Time : ").append(player.getWorld().getTime());
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(message)));
    }

    public boolean getIfPlayerActivateWorldOrCoos(Player player, boolean WorldOrCoos){
        int indexOfPlayer = playersOnline.indexOf(player);
        if (WorldOrCoos){
            return playerActivateCoos.get(indexOfPlayer);
        }else{
            return playerActivateWorld.get(indexOfPlayer);
        }
    }

}
