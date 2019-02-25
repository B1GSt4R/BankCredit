package blockstone.B1GSt4R.BankCredit.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blockstone.B1GSt4R.BankCredit.Main.system;

public class creditTransferCMD implements CommandExecutor {
	
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public creditTransferCMD(system system) {
		this.plugin = system;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 2) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("BankCredit.credit.transfer") || p.isOp()) {
					
				}else {
					
				}
			}else {
				
			}
		}
		if(args.length == 2) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("BankCredit.credit.transfer") || p.isOp()) {
					
				}else {
					
				}
			}else {
				
			}
		}
		return true;
	}
}
