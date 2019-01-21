package blockstone.B1GSt4R.BankCredit.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import blockstone.B1GSt4R.BankCredit.Main.system;
import net.milkbowl.vault.economy.EconomyResponse;

public class invClickListenerBank implements Listener {
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public invClickListenerBank(system system) {
		this.plugin = system;
		this.plugin.pm.registerEvents(this, plugin);
	}
	
	public ArrayList<String> credits = new ArrayList<>();
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		credits = plugin.api.getAllCreditIds();
		
		if(e.getInventory() != null && e.getInventory().getTitle() != null && e.getInventory().getTitle().equals(plugin.menuPrefix) && e.getClickedInventory() != null) {
			e.setCancelled(true);
			if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
				for(int i = 0; i<credits.size(); i++){
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals(plugin.api.getCreditName(credits.get(i)).replace('&', '§'))) {
						double cValue = plugin.api.getCreditValue(credits.get(i));
						plugin.addMoney(p, cValue);
						double value = plugin.api.getCreditValue(credits.get(i)) * (plugin.api.getCreditPayTax(credits.get(i)) / 100 + 1);
						plugin.api.createPlayerCredit(p, credits.get(i), plugin.api.changeDateDay(plugin.api.getDate(), 1, true), plugin.api.getTime(), plugin.api.getCreditLeaseTime(credits.get(i)), value, 0, 0, 0.00, 0.00);
						i = credits.size();
						refreshCreditMenu(p, true);
					}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§aAlready in use")) {
						String CreditID = e.getCurrentItem().getItemMeta().getLore().get(2).substring(14);
						double getRemainingValue = plugin.api.getRemainingCreditValue(plugin.api.builderPlayerUUID_CreditID(p, CreditID));
						plugin.payOffCredit(p, CreditID, getRemainingValue, true);
						i = credits.size();
						refreshCreditMenu(p, true);
					}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cExit")) {
						p.closeInventory();
					}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cBack")) {
					}
				}
			}
		}
	}
	
	public void refreshCreditMenu(Player p, boolean exitMenu) {
		ItemStack exit = new ItemStack(Material.BARRIER);
		ItemMeta exitMeta = exit.getItemMeta();
		exitMeta.setDisplayName("§cExit");
		exit.setItemMeta(exitMeta);
		
		ItemStack back = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta backMeta = (SkullMeta) back.getItemMeta();
		backMeta.setDisplayName("§cZurück");
		backMeta.setOwner("MHF_ArrowLeft");
		back.setItemMeta(backMeta);
		
		Inventory inv = null;
		if(credits.size() <= 9*1) {
			inv = Bukkit.createInventory(null, 9*1, plugin.menuPrefix);
			if(exitMenu) {
				inv.setItem(9*1-1, exit);
			}else {
				inv.setItem(9*1-1, back);
			}
		}else if(credits.size() <= 9*2) {
			inv = Bukkit.createInventory(null, 9*2, plugin.menuPrefix);
			if(exitMenu) {
				inv.setItem(9*2-1, exit);
			}else {
				inv.setItem(9*2-1, back);
			}
		}else if(credits.size() <= 9*3) {
			inv = Bukkit.createInventory(null, 9*3, plugin.menuPrefix);
			if(exitMenu) {
				inv.setItem(9*3-1, exit);
			}else {
				inv.setItem(9*3-1, back);
			}
		}else if(credits.size() <= 9*4) {
			inv = Bukkit.createInventory(null, 9*4, plugin.menuPrefix);
			if(exitMenu) {
				inv.setItem(9*4-1, exit);
			}else {
				inv.setItem(9*4-1, back);
			}
		}else if(credits.size() <= 9*5) {
			inv = Bukkit.createInventory(null, 9*5, plugin.menuPrefix);
			if(exitMenu) {
				inv.setItem(9*5-1, exit);
			}else {
				inv.setItem(9*5-1, back);
			}
		}else if(credits.size() < 9*6) {
			inv = Bukkit.createInventory(null, 9*6, plugin.menuPrefix);
			if(exitMenu) {
				inv.setItem(9*6-1, exit);
			}else {
				inv.setItem(9*6-1, back);
			}
		}
		
		for(int i = 0; i<credits.size(); i++) {
			ItemStack credit = null;
			boolean PlayerHasCredit = plugin.api.getPlayerUUID_CreditID_List(p).contains(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i)));
			boolean PlayerHasRank = true;
			if(plugin.TimeRank) {
				PlayerHasRank = plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.api.getCreditNeededRank(credits.get(i));
			}
			
			if(PlayerHasCredit) {
				credit = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta creditMeta = (BookMeta) credit.getItemMeta();
				creditMeta.setDisplayName("§aAlready in use");
				creditMeta.setAuthor(p.getName());
				String PlayerUUID_CreditID = plugin.api.builderPlayerUUID_CreditID(p, credits.get(i));
				ArrayList<String> lore = new ArrayList<>();
				lore.add("§7 ");
				lore.add("§7§m-----<§6 Credit Informations §7§m>-----");
				lore.add("§7CreditID: §6"+credits.get(i));
				lore.add("§7Value: §6"+plugin.api.getCreditValue(credits.get(i))+"$");
				lore.add("§7Tax: §6"+plugin.api.getCreditPayTax(credits.get(i))+"%");
				lore.add("§7LeaseTime: §6"+plugin.api.getCreditLeaseTime(credits.get(i))+" days");
				lore.add("§7 ");
				lore.add("§7§m-----<§6 Personal Informations §7§m>-----");
				lore.add("§7Remaining Value: §6"+plugin.api.getRemainingCreditValue(PlayerUUID_CreditID)+"$");
				lore.add("§7Remaining Days: §6"+plugin.api.getDaysLeft(PlayerUUID_CreditID)+" days");
				lore.add("§7Deferrals: §6"+plugin.api.getDeferralCounter(PlayerUUID_CreditID)+"x");
				lore.add("§7Not Payed Days: §6"+plugin.api.getNotPayedDays(PlayerUUID_CreditID)+" days");
				lore.add("§7Extra Pays: §6"+plugin.api.getExtraPays(PlayerUUID_CreditID)+"$");
				lore.add("§7Punish Pay: §6"+plugin.api.getPunishPays(PlayerUUID_CreditID)+"$");
				creditMeta.setLore(lore);
				credit.setItemMeta(creditMeta);
			}else {
				credit = new ItemStack(Material.BOOK); // 5.000$
				ItemMeta creditMeta = credit.getItemMeta();
				if(PlayerHasRank) {
					String creditName = plugin.sql.getCreditName(credits.get(i)).replace('&', '§');
					creditMeta.setDisplayName(creditName);
				}else {
					creditMeta.setDisplayName("§7Needed Rank: §6"+plugin.api.getCreditNeededRank(credits.get(i)));
				}
				ArrayList<String> lore = new ArrayList<>();
				lore.add("§7 ");
				lore.add("§7CreditID: §6"+credits.get(i));
				lore.add("§7Value: §6"+plugin.api.getCreditValue(credits.get(i))+"$");
				lore.add("§7Tax: §6"+plugin.api.getCreditPayTax(credits.get(i))+"%");
				lore.add("§7LeaseTime: §6"+plugin.api.getCreditLeaseTime(credits.get(i))+" days");
				creditMeta.setLore(lore);
				credit.setItemMeta(creditMeta);
			}
			
			inv.setItem(i, credit);
		}
		
		p.openInventory(inv);
	}
}
