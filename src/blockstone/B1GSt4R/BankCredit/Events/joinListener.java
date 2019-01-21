package blockstone.B1GSt4R.BankCredit.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import blockstone.B1GSt4R.BankCredit.Main.system;

public class joinListener implements Listener {
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public joinListener(system system) {
		this.plugin = system;
		this.plugin.pm.registerEvents(this, plugin);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		plugin.credit.addPlayer(p);
	}
}
