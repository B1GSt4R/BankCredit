package blockstone.B1GSt4R.BankCredit.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import blockstone.B1GSt4R.BankCredit.Main.system;

public class userListStatsCMD implements CommandExecutor {
	
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public userListStatsCMD(system system) {
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
					int size = 1;
					int counter = 0;
					for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
						if(plugin.api.lastSeenOverTime(op, 4)) {
							counter++;
							if(counter == 10) {
								size++;
							}else if(counter == 19) {
								size++;
							}else if(counter == 28) {
								size++;
							}else if(counter == 37) {
								size++;
							}else if(counter == 46) {
								size++;
							}else if(counter == 55) {
								size++;
							}
						}
					}
					
					Inventory inv = Bukkit.createInventory(null, 9*size, "§aUsers Stats");
					
					ArrayList<ItemStack> inactive = new ArrayList<>();
					ArrayList<ItemStack> inactiveCredit = new ArrayList<>();
					
					for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
						if(plugin.api.lastSeenOverTime(op, 4)) {
							ItemStack item = null;
							if(plugin.api.getPlayerUUID_CreditID_List(op).size() != 0) {
								item = new ItemStack(Material.DIAMOND_SWORD, 1);
								
								ItemMeta itemMeta = item.getItemMeta();
								itemMeta.setDisplayName("§6"+op.getName());
								if(plugin.api.getPlayerUUID_CreditID_List(op).size() != 0) {
									itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
								}
								itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
								item.setItemMeta(itemMeta);
								inactiveCredit.add(item);
							}else {
								item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
								SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
								itemMeta.setDisplayName("§6"+op.getName());
								itemMeta.setOwner(op.getName());
								item.setItemMeta(itemMeta);
								inactive.add(item);
							}
						}
					}
					
					boolean add = true;
					while(add) {
						for(int i = 0; i<inactiveCredit.size(); i++) {
							inv.addItem(inactiveCredit.get(i));
						}
						for(int i = 0; i<inactive.size(); i++) {
							inv.addItem(inactive.get(i));
						}
						add = false;
						inactive.clear();
						inactiveCredit.clear();
					}
					
					p.openInventory(inv);
				}
			}else {
				plugin.CONSOLE.sendMessage("§cDieser Befehl geht in der Konsole nicht!");
			}
		}
		return true;
	}

}
