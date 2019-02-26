package blockstone.B1GSt4R.BankCredit.Events;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import blockstone.B1GSt4R.BankCredit.Main.system;

@SuppressWarnings("static-access")
public class joinListener implements Listener {
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public joinListener(system system) {
		this.plugin = system;
		this.plugin.pm.registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String newVersion = plugin.ReadURL(plugin.versionURL);
		boolean perm = p.isOp() ||
				p.hasPermission(plugin.adminPerms[0]) ||
				p.hasPermission(plugin.adminPerms[1]) ||
				p.hasPermission(plugin.adminPerms[2]) ||
				p.hasPermission(plugin.adminPerms[13]);
		if(perm && !newVersion.equals(plugin.getDescription().getVersion())) {
			p.sendMessage(plugin.strichWarning);
			p.sendMessage(plugin.prefix+"There is a new Version!");
			p.sendMessage(plugin.prefix+"Current Version: §6"+plugin.getDescription().getVersion());
			p.sendMessage(plugin.prefix+"New Version: §6"+newVersion);
			p.sendMessage(" ");
			p.sendMessage(plugin.prefix+"Download Link below:");
			p.sendMessage(plugin.prefix+"https://www.B1GSt4R.de/my-account/downloads/");
			p.sendMessage(plugin.strichWarning);
		}
		if(perm) {
			int counter = 0;
			int counterCredit = 0;
			for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
				if(plugin.api.lastSeenOverTime(op, 4)) {
					counter++;
					if(plugin.api.getPlayerUUID_CreditID_List(op).size() != 0) {
						counterCredit++;
					}
				}
			}
			p.sendMessage(plugin.prefix+"There are §6"+counter+" §7inactive users!");
			p.sendMessage(plugin.prefix+"§6"+counterCredit+" §7of them have a credit!");
		}
		plugin.credit.addPlayer(p);
	}
}
