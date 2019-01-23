package blockstone.B1GSt4R.BankCredit.Utils;

import org.bukkit.entity.Player;

import blockstone.B1GSt4R.BankCredit.Main.system;
@SuppressWarnings("static-access")
public class creditConfigSystem {
	
	private static blockstone.B1GSt4R.BankCredit.Main.system plugin;
	
	public creditConfigSystem(system system) {
		this.plugin = system;
	}

	public static void addPlayer(Player player) {
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".creditStatus", 50);
//		Credit 1
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit1.used", false);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit1.sum", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit1.rest", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit1.daysLeft", 0);
//		Credit 2
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit2.used", false);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit2.sum", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit2.rest", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit2.daysLeft", 0);
//		Credit 3
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit3.used", false);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit3.sum", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit3.rest", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit3.daysLeft", 0);
//		Credit 4
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit4.used", false);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit4.sum", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit4.rest", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit4.daysLeft", 0);
//		Credit 5
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit5.used", false);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit5.sum", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit5.rest", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit5.daysLeft", 0);
//		Credit 6
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit6.used", false);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit6.sum", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit6.rest", 0);
		plugin.playerCreditCfg.set(player.getUniqueId().toString()+".credits.credit6.daysLeft", 0);
		
		plugin.saveCfg();
	}
	
	public static void updatePlayer(Player player, int newCredit) {
		switch(newCredit) {
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		case 6:
			
			break;
		default:
			break;
		}
	}
	
	public static boolean[] getPlayerCredit(Player player) {
		boolean[] used = {true, false};
		return used;
	}
	
}
