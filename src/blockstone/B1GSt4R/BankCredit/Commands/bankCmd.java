package blockstone.B1GSt4R.BankCredit.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import blockstone.B1GSt4R.BankCredit.Main.system;

@SuppressWarnings("static-access")
public class bankCmd implements CommandExecutor {
	
	private blockstone.B1GSt4R.timeRank.Main.system TimeRank;
	
	private blockstone.B1GSt4R.BankCredit.Main.system plugin;
	public bankCmd(system system) {
		this.plugin = system;
	}
	
	ArrayList<String> credits = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				//if(p.isOp())
				//bankMenu(p);
				credits = plugin.api.getAllCreditIds();
				creditMenu(p, true);
				return true;
			}else {
				sender.sendMessage("§cYou are not a Player!");
				return true;
			}
		}
		return false;
	}
	
	public void bankMenu(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, plugin.menuPrefix);
		
		ItemStack info = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta infoMeta = (BookMeta) info.getItemMeta();
		infoMeta.setAuthor("§5BlockStone");
		infoMeta.setDisplayName("§7Informationen");
		infoMeta.setTitle("§7Informationen");
		info.setItemMeta(infoMeta);
		
		ItemStack credit = new ItemStack(Material.GOLD_INGOT);
		ItemMeta creditMeta = credit.getItemMeta();
		creditMeta.setDisplayName("§6Kredit");
		credit.setItemMeta(creditMeta);
		
		inv.setItem(2, info);
		inv.setItem(6, credit);
		
		p.openInventory(inv);
	}
	
	@SuppressWarnings({ "deprecation" })
	public void creditMenu(Player p, boolean exitMenu) {
		
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
				PlayerHasRank = TimeRank.api.getPlayerLevel(p) >= plugin.api.getCreditNeededRank(credits.get(i));
			}
			
			if(PlayerHasCredit) {
				double creditValue = plugin.api.getCreditValue(credits.get(i));
				double leaseTime = plugin.api.getCreditLeaseTime(credits.get(i));
				double remaining = plugin.api.getRemainingCreditValue(plugin.api.builderPlayerUUID_CreditID(p, credits.get(i)));
				double nominalzinssatz = plugin.api.getCreditPayTax(credits.get(i)) / 100;
				
				double tilgung = creditValue / leaseTime;
				double zinsen = remaining / leaseTime * nominalzinssatz;
				
				double value = tilgung+zinsen;
				value = Math.round(value*100)/100.0;
				
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
				lore.add("§7Deferrals: §6"+plugin.api.getDeferralCounter(PlayerUUID_CreditID)+"x");
				lore.add("§7Not Payed Days: §6"+plugin.api.getNotPayedDays(PlayerUUID_CreditID)+" days");
				lore.add("§7Extra Pays: §6"+plugin.api.addZerosToDouble(plugin.api.getExtraPays(PlayerUUID_CreditID))+" Münzen");
				lore.add("§7Punish Pay: §6"+plugin.api.addZerosToDouble(plugin.api.getPunishPays(PlayerUUID_CreditID))+" Münzen");
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
				lore.add("§7Value: §6"+plugin.api.addZerosToDouble(plugin.api.getCreditValue(credits.get(i)))+" Münzen");
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
