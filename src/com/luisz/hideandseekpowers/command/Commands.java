package com.luisz.hideandseekpowers.command;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.building.BuildingMemory;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.IGame;
import com.luisz.hideandseekpowers.game.arena.Arena;
import com.luisz.hideandseekpowers.game.sign.SignGame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Você não pode usar esse comando!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.isOp()){
            p.sendMessage(ChatColor.RED + "Você não pode usar esse comando!");
            return false;
        }
        if(cmd.getName().equalsIgnoreCase("hasp")){
            if(args.length > 0){
                switch(args[0].toLowerCase()){
                    case "startarena":
                        if(args.length > 1){
                            Arena arena = Main.arenasConfig.getArena(args[1].toLowerCase());
                            if(arena != null){
                                if(Main.gameController.getByArena(arena.name) == null){
                                    Main.gameController.add(new Game(arena));
                                    p.sendMessage(ChatColor.GREEN + "Arena aberta!");
                                }else
                                    p.sendMessage(ChatColor.RED + "Arena já aberta!");
                            }else
                                p.sendMessage(ChatColor.RED + "Essa arena não existe!");
                        }else
                            p.sendMessage(ChatColor.RED + "Use: /hasp startarena <arena>");
                        break;
                    case "stoparena":
                        if(args.length > 1){
                            IGame game = Main.gameController.getByArena(args[1].toLowerCase());
                            if(game != null){
                                if(Main.gameController.remove(game))
                                    p.sendMessage(ChatColor.GREEN + "Arena fechada!");
                                else
                                    p.sendMessage(ChatColor.RED + "Erro ao fechar arena!");
                            }else
                                p.sendMessage(ChatColor.RED + "A arena ou não foi encontrada ou não está aberta!");
                        }else
                            p.sendMessage(ChatColor.RED + "Use: /hasp stoparena <arena>");
                        break;
                    case "createsign":
                        if(args.length > 2){
                            String signName = args[1].toLowerCase(), arenaName = args[2].toLowerCase();
                            if(Main.signsConfig.getSign(signName) == null){
                                if(Main.arenasConfig.getArena(arenaName) != null){
                                    if(BuildingMemory.buildingSign.get(p) != null){
                                        Main.signsConfig.addSign(new SignGame(signName, BuildingMemory.buildingSign.get(p), arenaName));
                                        p.sendMessage(ChatColor.GREEN + "Sign creada!");
                                    }else
                                        p.sendMessage(ChatColor.RED + "Você não está editando uma sign!");
                                }else
                                    p.sendMessage(ChatColor.RED + "Arena não encontrada!");
                            }else
                                p.sendMessage(ChatColor.RED + "Já existe uma sign com esse nome!");
                        }else
                            p.sendMessage(ChatColor.RED + "Use: /hasp createsign <signName> <arenaName>");
                        break;
                    case "deletesign":
                        if(args.length > 1){
                            if(Main.signsConfig.getSign(args[1].toLowerCase()) != null){
                                Main.signsConfig.removeSign(args[1].toLowerCase());
                                p.sendMessage(ChatColor.GREEN + "Sign removida!");
                            }else
                                p.sendMessage(ChatColor.RED + "Não existe uma sign com esse nome!");
                        }else
                            p.sendMessage(ChatColor.RED + "Use: /hasp deletesign <signName>");
                        break;
                    case "deletearena":
                        break;
                    case "createarena":
                        break;
                    case "buildarena":
                        if(args.length > 1){
                            switch (args[1].toLowerCase()){
                                case "name":
                                    break;
                                case "spawn":
                                    break;
                                case "lobby":
                                    break;
                                default:
                                    p.sendMessage(ChatColor.RED + "Esse comando não existe ou ainda não foi adicionado!");
                            }
                        }else
                            p.sendMessage(ChatColor.RED + "Use: /hasp buildarena <comando>");
                        break;
                    default:
                        p.sendMessage(ChatColor.RED + "Esse comando não existe ou ainda não foi adicionado!");
                }
            }else
                p.sendMessage(ChatColor.RED + "Use: /hasp <comando>");
        }
        return false;
    }
}
