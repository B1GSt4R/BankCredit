package blockstone.B1GSt4R.BankCredit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blockstone.B1GSt4R.BankCredit.Main.system;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("static-access")
public class creditTransferCMD implements CommandExecutor {
	
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public creditTransferCMD(system system) {
		this.plugin = system;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 4) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("BankCredit.credit.transfer") || p.isOp()) {
					p.sendMessage(plugin.prefix+"§c/transfer <oldPlayer> <newPlayer> <CreditID> <addieren|überschreiben>");
				}else {
					p.sendMessage(plugin.prefix+"§cNo Perms!");
				}
			}else {
				plugin.CONSOLE.sendMessage(plugin.prefix+"§c/transfer <oldPlayer> <newPlayer> <CreditID> <addieren|überschreiben>");
			}
		}
		if(args.length == 4) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("BankCredit.credit.transfer") || p.isOp()) {
					if(!args[0].equals(args[1])) {
						OfflinePlayer oldPlayer = Bukkit.getOfflinePlayer(args[0]);
						OfflinePlayer newPlayer = Bukkit.getOfflinePlayer(args[1]);
						String CreditID = args[2].toString();
						boolean addieren = args[3].toString().equalsIgnoreCase("addieren");
						boolean override = args[3].toString().equalsIgnoreCase("überschreiben");
						
						if(plugin.api.isCreditExists(CreditID)) {
							if(plugin.api.isExistsPlayerCredit(oldPlayer, CreditID)) {
								if(!plugin.api.isExistsPlayerCredit(newPlayer, CreditID)) {
									String newPID = plugin.api.builderPlayerUUID_CreditID(newPlayer, CreditID);
									String oldPID = plugin.api.builderPlayerUUID_CreditID(oldPlayer, CreditID);
									plugin.api.createPlayerCredit(newPlayer, CreditID, plugin.api.getNextPayDate(oldPID), plugin.api.getNextPayTime(oldPID), plugin.api.getDaysLeft(oldPID), plugin.api.getRemainingCreditValue(oldPID), plugin.api.getDeferralCounter(oldPID), plugin.api.getNotPayedDays(oldPID), plugin.api.getExtraPays(oldPID), plugin.api.getPunishPays(oldPID));
									plugin.api.removePlayerCredit(oldPlayer, CreditID);
									p.sendMessage(plugin.prefix+"Der Spieler §6"+newPlayer.getName()+" §7hat nun den Kredit §6"+CreditID+" §7von §6"+oldPlayer.getName()+" §7bekommen!");
								}else {
									if(addieren && !override) {
										String newPID = plugin.api.builderPlayerUUID_CreditID(newPlayer, CreditID);
										String oldPID = plugin.api.builderPlayerUUID_CreditID(oldPlayer, CreditID);
										
//										String payDate = plugin.api.getNextPayDate(oldPID);
//										String payTime = plugin.api.getNextPayTime(oldPID);
										int daysLeft = plugin.api.getDaysLeft(oldPID);
										double remaining = plugin.api.getRemainingCreditValue(oldPID);
										int deferrel = plugin.api.getDeferralCounter(oldPID);
										int notPayedDays = plugin.api.getNotPayedDays(oldPID);
										double extraPays = plugin.api.getExtraPays(oldPID);
										double Punishpays = plugin.api.getPunishPays(oldPID);
										
										plugin.api.addDeferralCounter(newPID, deferrel);
										plugin.api.addDaysLeft(newPID, daysLeft);
										plugin.api.addExtraPays(newPID, extraPays);
										plugin.api.addNotPayedDays(newPID, notPayedDays);
										plugin.api.addPunishPay(newPID, Punishpays);
										plugin.api.addRemainingCreditValue(newPID, remaining);
										
										plugin.api.removePlayerCredit(oldPlayer, CreditID);
										p.sendMessage(plugin.prefix+"Der Spieler §6"+newPlayer.getName()+" §7hat nun den Kredit §6"+CreditID+" §7von §6"+oldPlayer.getName()+" §7addiert bekommen!");
									}else if(!addieren && override){
										String newPID = plugin.api.builderPlayerUUID_CreditID(newPlayer, CreditID);
										String oldPID = plugin.api.builderPlayerUUID_CreditID(oldPlayer, CreditID);
										
//										String payDate = plugin.api.getNextPayDate(oldPID);
//										String payTime = plugin.api.getNextPayTime(oldPID);
										int daysLeft = plugin.api.getDaysLeft(oldPID);
										double remaining = plugin.api.getRemainingCreditValue(oldPID);
										int deferrel = plugin.api.getDeferralCounter(oldPID);
										int notPayedDays = plugin.api.getNotPayedDays(oldPID);
										double extraPays = plugin.api.getExtraPays(oldPID);
										double Punishpays = plugin.api.getPunishPays(oldPID);
										
										plugin.api.setDeferralCounter(newPID, deferrel);
										plugin.api.setDaysLeft(newPID, daysLeft);
										plugin.api.setExtraPays(newPID, extraPays);
										plugin.api.setNotPayedDays(newPID, notPayedDays);
										plugin.api.setPunishPay(newPID, Punishpays);
										plugin.api.setRemainingCreditValue(newPID, remaining);
										
										plugin.api.removePlayerCredit(oldPlayer, CreditID);
										p.sendMessage(plugin.prefix+"Der Spieler §6"+newPlayer.getName()+" §7hat nun den Kredit §6"+CreditID+" §7von §6"+oldPlayer.getName()+" §7überschrieben bekommen!");
									}else {
										p.sendMessage(plugin.prefix+"§c/transfer <oldPlayer> <newPlayer> <CreditID> <addieren|überschreiben>");
									}
								}
							}else {
								p.sendMessage(plugin.prefix+"Der Spieler §6"+oldPlayer.getName()+" §7hat diesen Kredit nicht! §8[§6"+CreditID+"§8]");
							}
						}else {
							p.sendMessage(plugin.prefix+"Dieser Kredit existiert nicht! §8[§6"+CreditID+"§8]");
						}
					}else {
						p.sendMessage(plugin.prefix+"Bitte wähle nicht zweimal den selben Spieler aus!");
					}
				}else {
					p.sendMessage(plugin.prefix+"§cNo Perms!");
				}
			}else {
				
			}
		}
		return true;
	}
}
