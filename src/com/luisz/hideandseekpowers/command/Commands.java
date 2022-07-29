package com.luisz.hideandseekpowers.command;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.building.BuildingMemory;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.IGame;
import com.luisz.hideandseekpowers.game.arena.Arena;
import com.luisz.hideandseekpowers.game.sign.SignGame;
import com.luisz.hideandseekpowers.lib576.LConvert;
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
                        if(args.length > 1){
                            if(Main.arenasConfig.getArena(args[1].toLowerCase()) != null){
                                Main.arenasConfig.removeArena(args[1].toLowerCase());
                                p.sendMessage(ChatColor.GREEN + "Arena removida!");
                            }else
                                p.sendMessage(ChatColor.RED + "Não existe uma arena com esse nome!");
                        }else
                            p.sendMessage(ChatColor.RED + "Use: /hasp deletearena <arenaName>");
                        break;
                    case "createarena":
                        if(args.length > 2){
                            int min = LConvert.convertToInteger(args[1]), max = LConvert.convertToInteger(args[2]);
                            if(min > 1 && max > 1){
                                if(BuildingMemory.buildingArena.get(p) != null){
                                    if(BuildingMemory.buildingArena.get(p).spawn != null &&
                                        BuildingMemory.buildingArena.get(p).lobby != null){
                                        BuildingMemory.buildingArena.get(p).minPlayers = min;
                                        BuildingMemory.buildingArena.get(p).maxPlayers = max;
                                        Main.arenasConfig.addArena(BuildingMemory.buildingArena.get(p));
                                        BuildingMemory.buildingArena.remove(p);
                                        p.sendMessage(ChatColor.GREEN + "Arena criada!");
                                    }else
                                        p.sendMessage(ChatColor.RED + "Arena incompleta!");
                                }else
                                    p.sendMessage(ChatColor.RED + "Você não está editando uma arena!");
                            }else
                                p.sendMessage(ChatColor.RED + "Informe números válidos!");
                        }else
                            p.sendMessage(ChatColor.RED + "Use: /hasp createarena <minPlayers> <maxPlayers>");
                        break;
                    case "buildarena":
                        if(args.length > 1){
                            switch (args[1].toLowerCase()){
                                case "start":
                                    if(args.length > 2){
                                        String arenaName = args[2].toLowerCase();
                                        if(Main.arenasConfig.getArena(arenaName) == null){
                                            Arena arena = new Arena();
                                            arena.name = arenaName;
                                            BuildingMemory.buildingArena.put(p, arena);
                                            p.sendMessage(ChatColor.GREEN + "Editando uma nova arena!");
                                        }else
                                            p.sendMessage(ChatColor.RED + "Essa arena já existe!");
                                    }else
                                        p.sendMessage(ChatColor.RED + "Use: /hasp buildarena start <arenaName>");
                                    break;
                                case "name":
                                    if(args.length > 2){
                                        String arenaName = args[2].toLowerCase();
                                        if(Main.arenasConfig.getArena(arenaName) == null){
                                            if(BuildingMemory.buildingArena.get(p) != null){
                                                String oldName = BuildingMemory.buildingArena.get(p).name;
                                                BuildingMemory.buildingArena.get(p).name = arenaName;
                                                p.sendMessage(ChatColor.GREEN + "Nome trocado de " + oldName + " para " + BuildingMemory.buildingArena.get(p).name + "!");
                                            }else
                                                p.sendMessage(ChatColor.RED + "Você não está editando uma arena!");
                                        }else
                                            p.sendMessage(ChatColor.RED + "Já existe uma arena com esse nome!");
                                    }else
                                        p.sendMessage(ChatColor.RED + "Use: /hasp buildarena name <arenaName>");
                                    break;
                                case "spawn":
                                    if(BuildingMemory.buildingArena.get(p) != null){
                                        BuildingMemory.buildingArena.get(p).spawn = p.getLocation();
                                        p.sendMessage(ChatColor.GREEN + "Spawn atualizado!");
                                    }else
                                        p.sendMessage(ChatColor.RED + "Você não está editando uma arena!");
                                    break;
                                case "lobby":
                                    if(BuildingMemory.buildingArena.get(p) != null){
                                        BuildingMemory.buildingArena.get(p).lobby = p.getLocation();
                                        p.sendMessage(ChatColor.GREEN + "Lobby atualizado!");
                                    }else
                                        p.sendMessage(ChatColor.RED + "Você não está editando uma arena!");
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
