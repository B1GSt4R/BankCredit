package blockstone.B1GSt4R.BankCredit.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blockstone.B1GSt4R.BankCredit.Main.system;

@SuppressWarnings("static-access")
public class inactiveGuiCMD implements CommandExecutor {

	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public inactiveGuiCMD(system system) {
		this.plugin = system;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			if(sender instanceof Player) {
				Player p = (Player)sender;
				boolean perm = p.isOp() ||
						p.hasPermission(plugin.adminPerms[0]) ||
						p.hasPermission(plugin.adminPerms[1]) ||
						p.hasPermission(plugin.adminPerms[2]) ||
						p.hasPermission(plugin.adminPerms[14]);
				if(perm) {
					plugin.GuiAPI.fillLists("InactiveCredit");
					p.openInventory(plugin.GuiAPI.InactivePlayersWithCredit(p));
					plugin.GuiAPI.clearLists(true);
				}
			}else {
				plugin.CONSOLE.sendMessage("§cDieser Befehl geht in der Konsole nicht!");
			}
		}
		return true;
	}
}
