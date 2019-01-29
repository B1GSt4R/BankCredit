package blockstone.B1GSt4R.BankCredit.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blockstone.B1GSt4R.BankCredit.Main.system;

public class bankCreditCMD implements CommandExecutor {
	
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public bankCreditCMD(system system) {
		this.plugin = system;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			String newVersion = plugin.ReadURL(plugin.versionURL);
			if(sender instanceof Player) {
				Player p = (Player)sender;
				boolean perm = p.isOp() ||
						p.hasPermission(plugin.adminPerms[0]) ||
						p.hasPermission(plugin.adminPerms[1]) ||
						p.hasPermission(plugin.adminPerms[2]) ||
						p.hasPermission(plugin.adminPerms[13]);
				if(perm && !newVersion.equals(plugin.getDescription().getVersion()) && !plugin.license.equals("ABCDE-12345-FGH67-890IJ-KLMNOPQRST")) {
					p.sendMessage(plugin.strichWarning);
					p.sendMessage(plugin.prefix+"There is a new Version!");
					p.sendMessage(plugin.prefix+"Current Version: §6"+plugin.getDescription().getVersion());
					p.sendMessage(plugin.prefix+"New Version: §6"+newVersion);
					p.sendMessage(" ");
					p.sendMessage(plugin.prefix+"Download Link below:");
					p.sendMessage(plugin.prefix+"https://www.B1GSt4R.de/my-account/downloads/");
					p.sendMessage(plugin.strichWarning);
					
					plugin.CONSOLE.sendMessage(plugin.strichWarning);
					plugin.CONSOLE.sendMessage(plugin.prefix+"There is a new Version!");
					plugin.CONSOLE.sendMessage(plugin.prefix+"Current Version: §6"+plugin.getDescription().getVersion());
					plugin.CONSOLE.sendMessage(plugin.prefix+"New Version: §6"+newVersion);
					plugin.CONSOLE.sendMessage(" ");
					plugin.CONSOLE.sendMessage(plugin.prefix+"Download Link below:");
					plugin.CONSOLE.sendMessage(plugin.prefix+"https://www.B1GSt4R.de/my-account/downloads/");
					plugin.CONSOLE.sendMessage(plugin.strichWarning);
				}
			}else {
				if(!newVersion.equals(plugin.getDescription().getVersion()) && !plugin.license.equals("ABCDE-12345-FGH67-890IJ-KLMNOPQRST")) {
					plugin.CONSOLE.sendMessage(plugin.strichWarning);
					plugin.CONSOLE.sendMessage(plugin.prefix+"There is a new Version!");
					plugin.CONSOLE.sendMessage(plugin.prefix+"Current Version: §6"+plugin.getDescription().getVersion());
					plugin.CONSOLE.sendMessage(plugin.prefix+"New Version: §6"+newVersion);
					plugin.CONSOLE.sendMessage(" ");
					plugin.CONSOLE.sendMessage(plugin.prefix+"Download Link below:");
					plugin.CONSOLE.sendMessage(plugin.prefix+"https://www.B1GSt4R.de/my-account/downloads/");
					plugin.CONSOLE.sendMessage(plugin.strichWarning);
				}
			}
		}
		if(args.length == 1) {
			if(!plugin.checkLicense(args[0])) {
				//Wrong License
				if(sender instanceof Player) {
					Player p = (Player)sender;
					p.sendMessage(plugin.prefix+"§cEntered wrong license!");
				}
				plugin.CONSOLE.sendMessage(plugin.prefix+"§cEntered wrong license!");
			}else if(plugin.license.equals(args[0])) {
				//Already in use
				if(sender instanceof Player) {
					Player p = (Player)sender;
					p.sendMessage(plugin.prefix+"§ePlugin already authorised!");
				}
				plugin.CONSOLE.sendMessage(plugin.prefix+"§ePlugin already authorised!");
//			}else if(plugin.checkLicense(plugin.license)) {// && !plugin.license.equals("ABCDE-12345-FGH67-890IJ-KLMNOPQRST")) {
				//Already in use
//				if(sender instanceof Player) {
//					Player p = (Player)sender;
//					p.sendMessage(plugin.prefix+"§ePlugin already authorised!");
//				}
//				plugin.CONSOLE.sendMessage(plugin.prefix+"§ePlugin already authorised!");
			}else {
				//Sign in
				plugin.cfg.set("License", args[0].toString());
				plugin.saveCfg();
				plugin.pm.disablePlugin(plugin);
				plugin.pm.enablePlugin(plugin);
				if(sender instanceof Player) {
					Player p = (Player)sender;
					p.sendMessage(plugin.prefix+"§aPlugin Successfully enabled!");
				}
				plugin.CONSOLE.sendMessage(plugin.prefix+"§aPlugin Successfully enabled!");
			}
		}
		return true;
	}
	
}
