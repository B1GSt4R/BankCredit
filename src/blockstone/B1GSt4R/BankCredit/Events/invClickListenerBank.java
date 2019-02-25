package blockstone.B1GSt4R.BankCredit.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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

@SuppressWarnings("static-access")
public class invClickListenerBank implements Listener {
	
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public invClickListenerBank(system system) {
		this.plugin = system;
		this.plugin.pm.registerEvents(this, plugin);
	}
	
	public ArrayList<String> credits = new ArrayList<>();
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		
		
		if(e.getInventory() != null && e.getInventory().getTitle() != null && e.getClickedInventory() != null) {
			plugin.GuiAPI.fillLists("");
			plugin.GuiAPI.clearLists(false);
			boolean bName = e.getInventory().getTitle().equals(plugin.titleAllUsers+plugin.GuiAPI.titleStats) || e.getInventory().getTitle().equals(plugin.titleActiveUsers+plugin.GuiAPI.titleStats) ||
					e.getInventory().getTitle().equals(plugin.titleInactiveUsers+plugin.GuiAPI.titleStats) || e.getInventory().getTitle().equals(plugin.titleInactiveCreditUsers+plugin.GuiAPI.titleStats) ||
					e.getInventory().getTitle().equals(plugin.titleOnlyInactiveCreditUsers+plugin.GuiAPI.titleStats);
			if(bName) {
				e.setCancelled(true);
				credits = plugin.api.getAllCreditIds();
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					String sName = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
					for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {	
						if(sName.equals(op.getName())) {
							p.closeInventory();
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {
									p.openInventory(creditMenu(op, false, e.getCurrentItem().getItemMeta().getDisplayName()));
								}
							}, 1);
						}
					}
				}
			}
			
			if(e.getInventory().getTitle().startsWith("§8Bank of ")) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cExit")){
					p.closeInventory();
				}
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cZurück")) {
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {	
							plugin.GuiAPI.clearLists(true);
							plugin.GuiAPI.fillLists("InactiveCredit");
							p.openInventory(plugin.GuiAPI.InactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}
			}
			
			if(e.getInventory().getTitle().equals(plugin.titleAllUsers+plugin.GuiAPI.titleStats)) {
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemAllUsers)){

				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemActiveUsers)) {
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Active");
							p.openInventory(plugin.GuiAPI.allActiveUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Inactive");
							p.openInventory(plugin.GuiAPI.InactivePlayers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("InactiveCredit");
							p.openInventory(plugin.GuiAPI.InactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemOnlyInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("OnlyInactiveCredit");
							p.openInventory(plugin.GuiAPI.OnlyInactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}
			}else if(e.getInventory().getTitle().equals(plugin.titleActiveUsers+plugin.GuiAPI.titleStats)) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemAllUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("All");
							p.openInventory(plugin.GuiAPI.AllUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemActiveUsers)) {

				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Inactive");
							p.openInventory(plugin.GuiAPI.InactivePlayers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("InactiveCredit");
							p.openInventory(plugin.GuiAPI.InactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemOnlyInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("OnlyInactiveCredit");
							p.openInventory(plugin.GuiAPI.OnlyInactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}
			}else if(e.getInventory().getTitle().equals(plugin.titleInactiveUsers+plugin.GuiAPI.titleStats)) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemAllUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("All");
							p.openInventory(plugin.GuiAPI.AllUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemActiveUsers)) {
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Active");
							p.openInventory(plugin.GuiAPI.allActiveUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveUsers)){

				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("InactiveCredit");
							p.openInventory(plugin.GuiAPI.InactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemOnlyInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("OnlyInactiveCredit");
							p.openInventory(plugin.GuiAPI.OnlyInactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}
			}else if(e.getInventory().getTitle().equals(plugin.titleInactiveCreditUsers+plugin.GuiAPI.titleStats)) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemAllUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("All");
							p.openInventory(plugin.GuiAPI.AllUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemActiveUsers)) {
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Active");
							p.openInventory(plugin.GuiAPI.allActiveUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Inactive");
							p.openInventory(plugin.GuiAPI.InactivePlayers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveCreditUsers)){

				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemOnlyInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("OnlyInactiveCredit");
							p.openInventory(plugin.GuiAPI.OnlyInactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}
			}else if(e.getInventory().getTitle().equals(plugin.titleOnlyInactiveCreditUsers+plugin.GuiAPI.titleStats)) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemAllUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("All");
							p.openInventory(plugin.GuiAPI.AllUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemActiveUsers)) {
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Active");
							p.openInventory(plugin.GuiAPI.allActiveUsers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("Inactive");
							p.openInventory(plugin.GuiAPI.InactivePlayers(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemInactiveCreditUsers)){
					p.closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.GuiAPI.fillLists("InactiveCredit");
							p.openInventory(plugin.GuiAPI.InactivePlayersWithCredit(p));
							plugin.GuiAPI.clearLists(true);
						}
					}, 1);
				}else if(e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemOnlyInactiveCreditUsers)){

				}
			}
		}
		
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
						double getRemaingPunishPay = plugin.api.getPunishPays(plugin.api.builderPlayerUUID_CreditID(p, CreditID));
						plugin.payOffCredit(p, CreditID, getRemainingValue, getRemaingPunishPay, true);
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
	
	@SuppressWarnings({ "deprecation" })
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
				double value = 0;
				
				if(plugin.api.getRemainingCreditValue(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i))) != 0) {
					value = plugin.api.getPayOffTaxValue(p, credits.get(i), false);
				}else {
					value = plugin.api.getPayOffTaxValue(p, credits.get(i), true);
				}
				
				credit = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta creditMeta = (BookMeta) credit.getItemMeta();
				creditMeta.setDisplayName("§aAlready in use");
				creditMeta.setAuthor(p.getName());
				String PlayerUUID_CreditID = plugin.api.builderPlayerUUID_CreditID(p, credits.get(i));
				ArrayList<String> lore = new ArrayList<>();
				lore.add("§7 ");
				lore.add("§7§m-----<§6 Credit Informations §7§m>-----");
				lore.add("§7CreditID: §6"+credits.get(i));
				lore.add("§7Value: §6"+plugin.api.addZerosToDouble(plugin.api.getCreditValue(credits.get(i)))+" Münzen");
				lore.add("§7Tax: §6"+plugin.api.getCreditPayTax(credits.get(i))+"%");
				lore.add("§7LeaseTime: §6"+plugin.api.getCreditLeaseTime(credits.get(i))+" days");
				lore.add("§7 ");
				lore.add("§7§m-----<§6 Personal Informations §7§m>-----");
				lore.add("§7Remaining Value: §6"+plugin.api.addZerosToDouble(plugin.api.getRemainingCreditValue(PlayerUUID_CreditID))+" Münzen");
				lore.add("§7Remaining Days: §6"+plugin.api.getDaysLeft(PlayerUUID_CreditID)+" days");
				if(plugin.eco.getBalance(p) >= value) {
					lore.add("§7Next Pay Value: §a"+plugin.api.addZerosToDouble(value)+" Münzen");
				}else {
					lore.add("§7Next Pay Value: §c"+plugin.api.addZerosToDouble(value)+" Münzen");
				}
				lore.add("§7Next Pay Date: §6"+plugin.api.getNextPayDate(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i))));
				lore.add("§7Next Pay Time: §6"+plugin.api.getNextPayTime(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i)))+" §7Uhr");
				lore.add("§7Deferrals: §6"+plugin.api.getDeferralCounter(PlayerUUID_CreditID)+"x");
				lore.add("§7Not Payed Days: §6"+plugin.api.getNotPayedDays(PlayerUUID_CreditID)+" days");
				lore.add("§7Extra Pays: §6"+plugin.api.addZerosToDouble(plugin.api.getExtraPays(PlayerUUID_CreditID))+" Münzen");
				lore.add("§7Punish Pay: §6"+plugin.api.addZerosToDouble(plugin.api.getPunishPays(PlayerUUID_CreditID))+" Münzen");
				creditMeta.setLore(lore);
				credit.setItemMeta(creditMeta);
			}else {
				double creditValue = plugin.api.getCreditValue(credits.get(i));
				double leaseTime = plugin.api.getCreditLeaseTime(credits.get(i));
				double nominalzinssatz = plugin.api.getCreditPayTax(credits.get(i)) / 100;
				double remaining = creditValue * (nominalzinssatz + 1);
				
				double tilgung = creditValue / leaseTime;
				double zinsen = remaining / leaseTime * nominalzinssatz;
				
				double value = tilgung+zinsen;
				value = Math.round(value*100.0)/100.0;
				
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
				lore.add("§7Value: §6"+plugin.api.addZerosToDouble(plugin.api.getCreditValue(credits.get(i)))+" Münzen");
				lore.add("§7Tax: §6"+plugin.api.getCreditPayTax(credits.get(i))+"%");
				lore.add("§7LeaseTime: §6"+plugin.api.getCreditLeaseTime(credits.get(i))+" days");
				lore.add("§7 ");
				lore.add("§7First Pay Date: §6"+plugin.api.changeDateDay(plugin.api.getDate(), 1, true));
				lore.add("§7First Pay Time: §6"+plugin.api.getTime()+" §7Uhr");
				lore.add("§7First Pay Value: §6"+plugin.api.addZerosToDouble(value)+" Münzen");
				double totalValue = plugin.api.getCreditValue(credits.get(i)) * (plugin.api.getCreditPayTax(credits.get(i)) / 100 + 1);
				lore.add("§7Total Pay Value: §6"+plugin.api.addZerosToDouble(totalValue)+" Münzen");
				creditMeta.setLore(lore);
				credit.setItemMeta(creditMeta);
			}
			
			inv.setItem(i, credit);
		}
		
		p.openInventory(inv);
	}
	
	public Inventory creditMenu(OfflinePlayer p, boolean exitMenu, String playerDisplayname) {
		ArrayList<String> credits = plugin.api.getAllCreditIds();
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
			inv = Bukkit.createInventory(null, 9*1, "§8Bank of "+playerDisplayname);
			if(exitMenu) {
				inv.setItem(9*1-1, exit);
			}else {
				inv.setItem(9*1-1, back);
			}
		}else if(credits.size() <= 9*2) {
			inv = Bukkit.createInventory(null, 9*2, "§8Bank of "+playerDisplayname);
			if(exitMenu) {
				inv.setItem(9*2-1, exit);
			}else {
				inv.setItem(9*2-1, back);
			}
		}else if(credits.size() <= 9*3) {
			inv = Bukkit.createInventory(null, 9*3, "§8Bank of "+playerDisplayname);
			if(exitMenu) {
				inv.setItem(9*3-1, exit);
			}else {
				inv.setItem(9*3-1, back);
			}
		}else if(credits.size() <= 9*4) {
			inv = Bukkit.createInventory(null, 9*4, "§8Bank of "+playerDisplayname);
			if(exitMenu) {
				inv.setItem(9*4-1, exit);
			}else {
				inv.setItem(9*4-1, back);
			}
		}else if(credits.size() <= 9*5) {
			inv = Bukkit.createInventory(null, 9*5, "§8Bank of "+playerDisplayname);
			if(exitMenu) {
				inv.setItem(9*5-1, exit);
			}else {
				inv.setItem(9*5-1, back);
			}
		}else if(credits.size() < 9*6) {
			inv = Bukkit.createInventory(null, 9*6, "§8Bank of "+playerDisplayname);
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
				double value = 0;
				
				if(plugin.api.getRemainingCreditValue(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i))) != 0) {
					value = plugin.api.getPayOffTaxValue(p, credits.get(i), false);
				}else {
					value = plugin.api.getPayOffTaxValue(p, credits.get(i), true);
				}
				
				credit = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta creditMeta = (BookMeta) credit.getItemMeta();
				creditMeta.setDisplayName("§aAlready in use");
				creditMeta.setAuthor(p.getName());
				String PlayerUUID_CreditID = plugin.api.builderPlayerUUID_CreditID(p, credits.get(i));
				ArrayList<String> lore = new ArrayList<>();
				lore.add("§7 ");
				lore.add("§7§m-----<§6 Credit Informations §7§m>-----");
				lore.add("§7CreditID: §6"+credits.get(i));
				lore.add("§7Value: §6"+plugin.api.addZerosToDouble(plugin.api.getCreditValue(credits.get(i)))+" Münzen");
				lore.add("§7Tax: §6"+plugin.api.getCreditPayTax(credits.get(i))+"%");
				lore.add("§7LeaseTime: §6"+plugin.api.getCreditLeaseTime(credits.get(i))+" days");
				lore.add("§7 ");
				lore.add("§7§m-----<§6 Personal Informations §7§m>-----");
				lore.add("§7Remaining Value: §6"+plugin.api.addZerosToDouble(plugin.api.getRemainingCreditValue(PlayerUUID_CreditID))+" Münzen");
				lore.add("§7Remaining Days: §6"+plugin.api.getDaysLeft(PlayerUUID_CreditID)+" days");
				if(plugin.eco.getBalance(p) >= value) {
					lore.add("§7Next Pay Value: §a"+plugin.api.addZerosToDouble(value)+" Münzen");
				}else {
					lore.add("§7Next Pay Value: §c"+plugin.api.addZerosToDouble(value)+" Münzen");
				}
				lore.add("§7Next Pay Date: §6"+plugin.api.getNextPayDate(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i))));
				lore.add("§7Next Pay Time: §6"+plugin.api.getNextPayTime(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i)))+" §7Uhr");
				lore.add("§7Deferrals: §6"+plugin.api.getDeferralCounter(PlayerUUID_CreditID)+"x");
				lore.add("§7Not Payed Days: §6"+plugin.api.getNotPayedDays(PlayerUUID_CreditID)+" days");
				lore.add("§7Extra Pays: §6"+plugin.api.addZerosToDouble(plugin.api.getExtraPays(PlayerUUID_CreditID))+" Münzen");
				lore.add("§7Punish Pay: §6"+plugin.api.addZerosToDouble(plugin.api.getPunishPays(PlayerUUID_CreditID))+" Münzen");
				creditMeta.setLore(lore);
				credit.setItemMeta(creditMeta);
			}else {
				double creditValue = plugin.api.getCreditValue(credits.get(i));
				double leaseTime = plugin.api.getCreditLeaseTime(credits.get(i));
				double nominalzinssatz = plugin.api.getCreditPayTax(credits.get(i)) / 100;
				double remaining = creditValue * (nominalzinssatz + 1);
				
				double tilgung = creditValue / leaseTime;
				double zinsen = remaining / leaseTime * nominalzinssatz;
				
				double value = tilgung+zinsen;
				value = Math.round(value*100.0)/100.0;
				
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
				lore.add("§7Value: §6"+plugin.api.addZerosToDouble(plugin.api.getCreditValue(credits.get(i)))+" Münzen");
				lore.add("§7Tax: §6"+plugin.api.getCreditPayTax(credits.get(i))+"%");
				lore.add("§7LeaseTime: §6"+plugin.api.getCreditLeaseTime(credits.get(i))+" days");
				lore.add("§7 ");
				lore.add("§7First Pay Date: §6"+plugin.api.changeDateDay(plugin.api.getDate(), 1, true));
				lore.add("§7First Pay Time: §6"+plugin.api.getTime()+" §7Uhr");
				lore.add("§7First Pay Value: §6"+plugin.api.addZerosToDouble(value)+" Münzen");
				double totalValue = plugin.api.getCreditValue(credits.get(i)) * (plugin.api.getCreditPayTax(credits.get(i)) / 100 + 1);
				lore.add("§7Total Pay Value: §6"+plugin.api.addZerosToDouble(totalValue)+" Münzen");
				creditMeta.setLore(lore);
				credit.setItemMeta(creditMeta);
			}
			
			inv.setItem(i, credit);
		}
		return inv;	
	}
}
