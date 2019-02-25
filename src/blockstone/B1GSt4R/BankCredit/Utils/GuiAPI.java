package blockstone.B1GSt4R.BankCredit.Utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("static-access")
public class GuiAPI {
	
	private static blockstone.B1GSt4R.BankCredit.Main.system plugin;
	
	private static ArrayList<ItemStack> active = new ArrayList<>();
	private static ArrayList<ItemStack> inactive = new ArrayList<>();
	private static ArrayList<ItemStack> inactiveCredit = new ArrayList<>();
	public static String titleStats = "§7All: §6%allUsers%§7 ￨ Active: §6%activeUsers%§7 ￨ Inactive Total: §6%inactiveTotalUsers%§7 ￨ Inactive: §6%inactiveUsers%§7 ￨ InactiveCredit: §6%inactiveCreditUsers%";
	private static String title = "";
	
	private static int allUsers = 0;
	private static int activeUsers = 0;
	private static int inactiveUsers = 0;
	private static int inactiveCreditUsers = 0;
	private static int inactiveTotalUsers = 0;
	
	public static void fillLists(String mode) {
		
		allUsers = Bukkit.getOfflinePlayers().length;
		for(OfflinePlayer tmp : Bukkit.getOfflinePlayers()) {
			if(plugin.api.lastSeenOverTime(tmp, 4)) {
				//Inaktiv
				inactiveTotalUsers++;
				if(plugin.api.getPlayerUUID_CreditID_List(tmp).size() != 0) {
					//Kredit
					inactiveCreditUsers++;
				}else {
					//Kein Kredit
					inactiveUsers++;
				}
			}else {
				//Aktiv
				activeUsers++;
			}
		}
		
		titleStats = titleStats.replace("%allUsers%", ""+allUsers);
		titleStats = titleStats.replace("%activeUsers%", ""+activeUsers);
		titleStats = titleStats.replace("%inactiveUsers%", ""+inactiveUsers);
		titleStats = titleStats.replace("%inactiveCreditUsers%", ""+inactiveCreditUsers);
		titleStats = titleStats.replace("%inactiveTotalUsers%", ""+inactiveTotalUsers);
		
		switch (mode) {
		case "All":
			title = plugin.titleAllUsers;
			for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
				if(plugin.api.lastSeenOverTime(op, 4)) {
					if(plugin.api.getPlayerUUID_CreditID_List(op).size() != 0) {
						//Hat Kredit
						ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName("§c"+op.getName());
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7");
						lore.add("§7UUID: §6"+op.getUniqueId().toString());
						if(Bukkit.getOnlinePlayers().contains(op)) {
							lore.add("§7Online Status: §2ON");
						}else {
							lore.add("§7Online Status: §4OFF");
						}
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Date: §6"+sdf.format(res));
						sdf = new SimpleDateFormat("HH:mm");
						res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Time: §6"+sdf.format(res));
						lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
						lore.add("§7 ");
						lore.add("§7§m-----<§6 User Credits §7§m>-----");
						ArrayList<String> tmp = plugin.api.getPlayerUUID_CreditID_List(op);
//						String userCredits = "";
						for(int x = 0; x<tmp.size(); x++) {
							lore.add("§7"+tmp.get(x));
							/*if(x < tmp.size()-1) {
								userCredits += tmp.get(x) + ", ";
							}else {
								userCredits += tmp.get(x);
							}*/
						}
//						lore.add(userCredits);
						itemMeta.setLore(lore);
						itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
						itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						item.setItemMeta(itemMeta);
						inactiveCredit.add(item);
					}else {
						//Hat kein Kredit
						ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName("§e"+op.getName());
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7");
						lore.add("§7UUID: §6"+op.getUniqueId().toString());
						if(Bukkit.getOnlinePlayers().contains(op)) {
							lore.add("§7Online Status: §2ON");
						}else {
							lore.add("§7Online Status: §4OFF");
						}
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Date: §6"+sdf.format(res));
						sdf = new SimpleDateFormat("HH:mm");
						res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Time: §6"+sdf.format(res));
						lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
						itemMeta.setLore(lore);
						itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						item.setItemMeta(itemMeta);
						inactive.add(item);
					}
				}else {
					//ist Aktiv
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
					itemMeta.setDisplayName("§a"+op.getName());
					ArrayList<String> lore = new ArrayList<>();
					lore.add("§7");
					lore.add("§7UUID: §6"+op.getUniqueId().toString());
					if(Bukkit.getOnlinePlayers().contains(op)) {
						lore.add("§7Online Status: §2ON");
					}else {
						lore.add("§7Online Status: §4OFF");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Date: §6"+sdf.format(res));
					sdf = new SimpleDateFormat("HH:mm");
					res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Time: §6"+sdf.format(res));
					lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
					itemMeta.setLore(lore);
					itemMeta.setOwningPlayer(op);
					item.setItemMeta(itemMeta);
					active.add(item);
				}
			}
			break;
		case "Active":
			title = plugin.titleActiveUsers;
			for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
				if(!plugin.api.lastSeenOverTime(op, 4)) {
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
					itemMeta.setDisplayName("§a"+op.getName());
					ArrayList<String> lore = new ArrayList<>();
					lore.add("§7");
					lore.add("§7UUID: §6"+op.getUniqueId().toString());
					if(Bukkit.getOnlinePlayers().contains(op)) {
						lore.add("§7Online Status: §2ON");
					}else {
						lore.add("§7Online Status: §4OFF");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Date: §6"+sdf.format(res));
					sdf = new SimpleDateFormat("HH:mm");
					res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Time: §6"+sdf.format(res));
					lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
					itemMeta.setLore(lore);
					itemMeta.setOwningPlayer(op);
					item.setItemMeta(itemMeta);
					active.add(item);
				}
			}
			break;
		case "Inactive":
			title = plugin.titleInactiveUsers;
			for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
				if(plugin.api.lastSeenOverTime(op, 4)) {
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
					itemMeta.setDisplayName("§e"+op.getName());
					ArrayList<String> lore = new ArrayList<>();
					lore.add("§7");
					lore.add("§7UUID: §6"+op.getUniqueId().toString());
					if(Bukkit.getOnlinePlayers().contains(op)) {
						lore.add("§7Online Status: §2ON");
					}else {
						lore.add("§7Online Status: §4OFF");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Date: §6"+sdf.format(res));
					sdf = new SimpleDateFormat("HH:mm");
					res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Time: §6"+sdf.format(res));
					lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
					itemMeta.setLore(lore);
					itemMeta.setOwningPlayer(op);
					item.setItemMeta(itemMeta);
					inactive.add(item);
				}
			}
			break;
		case "InactiveCredit":
			title = plugin.titleInactiveCreditUsers;
			for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
				if(plugin.api.lastSeenOverTime(op, 4)) {
					ItemStack item = null;
					if(plugin.api.getPlayerUUID_CreditID_List(op).size() != 0) {
						item = new ItemStack(Material.DIAMOND_SWORD, 1);
						
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName("§4"+op.getName());
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7");
						lore.add("§7UUID: §6"+op.getUniqueId().toString());
						if(Bukkit.getOnlinePlayers().contains(op)) {
							lore.add("§7Online Status: §2ON");
						}else {
							lore.add("§7Online Status: §4OFF");
						}
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Date: §6"+sdf.format(res));
						sdf = new SimpleDateFormat("HH:mm");
						res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Time: §6"+sdf.format(res));
						lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
						lore.add("§7 ");
						lore.add("§7§m-----<§6 User Credits §7§m>-----");
						ArrayList<String> tmp = plugin.api.getPlayerUUID_CreditID_List(op);
//						String userCredits = "";
						for(int x = 0; x<tmp.size(); x++) {
							lore.add("§7"+tmp.get(x));
							/*if(x < tmp.size()-1) {
								userCredits += tmp.get(x) + ", ";
							}else {
								userCredits += tmp.get(x);
							}*/
						}
//						lore.add(userCredits);
						itemMeta.setLore(lore);
						itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
						itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						item.setItemMeta(itemMeta);
						inactiveCredit.add(item);
					}else {
						item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
						SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
						itemMeta.setDisplayName("§c"+op.getName());
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7");
						lore.add("§7UUID: §6"+op.getUniqueId().toString());
						if(Bukkit.getOnlinePlayers().contains(op)) {
							lore.add("§7Online Status: §2ON");
						}else {
							lore.add("§7Online Status: §4OFF");
						}
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Date: §6"+sdf.format(res));
						sdf = new SimpleDateFormat("HH:mm");
						res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
						lore.add("§7Last Played Time: §6"+sdf.format(res));
						lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
						itemMeta.setLore(lore);
						itemMeta.setOwningPlayer(op);
						item.setItemMeta(itemMeta);
						inactive.add(item);
					}
				}
			}
			break;
		case "OnlyInactiveCredit":
			title = plugin.titleOnlyInactiveCreditUsers;
			for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
				if(plugin.api.lastSeenOverTime(op, 4) && plugin.api.getPlayerUUID_CreditID_List(op).size() != 0) {
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
					itemMeta.setDisplayName("§4"+op.getName());
					ArrayList<String> lore = new ArrayList<>();
					lore.add("§7");
					lore.add("§7UUID: §6"+op.getUniqueId().toString());
					if(Bukkit.getOnlinePlayers().contains(op)) {
						lore.add("§7Online Status: §2ON");
					}else {
						lore.add("§7Online Status: §4OFF");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					Date res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Date: §6"+sdf.format(res));
					sdf = new SimpleDateFormat("HH:mm");
					res = new Date(plugin.timeRankAPI.sql.getPlayerLastSeen(op));
					lore.add("§7Last Played Time: §6"+sdf.format(res));
					lore.add("§7Playtime: §6"+plugin.timeRankAPI.sql.getPlayerTime(op));
					lore.add("§7 ");
					lore.add("§7§m-----<§6 User Credits §7§m>-----");
					ArrayList<String> tmp = plugin.api.getPlayerUUID_CreditID_List(op);
//					String userCredits = "";
					for(int x = 0; x<tmp.size(); x++) {
						lore.add("§7"+tmp.get(x));
						/*if(x < tmp.size()-1) {
							userCredits += tmp.get(x) + ", ";
						}else {
							userCredits += tmp.get(x);
						}*/
					}
//					lore.add(userCredits);
					itemMeta.setLore(lore);
					itemMeta.setOwningPlayer(op);
					item.setItemMeta(itemMeta);
					inactiveCredit.add(item);
				}
			}
			break;
		default:
			break;
		}
	}
	
	public static void clearLists(boolean clearTitle) {
		active.clear();
		inactive.clear();
		inactiveCredit.clear();
		allUsers = 0;
		activeUsers = 0;
		inactiveCreditUsers = 0;
		inactiveTotalUsers = 0;
		inactiveUsers = 0;
		if(clearTitle) {
			clearTitle();
		}
	}
	
	public static void clearTitle() {
		titleStats = "§7All: §6%allUsers%§7 ￨ Active: §6%activeUsers%§7 ￨ Inactive Total: §6%inactiveTotalUsers%§7 ￨ Inactive: §6%inactiveUsers%§7 ￨ InactiveCredit: §6%inactiveCreditUsers%";
	}
	
	public static Inventory AllUsers(Player p) {
		int size = 2;
		if(allUsers >= 1) {
			size++;
		}
		if(allUsers >= 10) {
			size++;
		}
		if(allUsers >= 19) {
			size++;
		}
		if(allUsers >= 28) {
			size++;
		}
		if(allUsers >= 37) {
			size++;
		}
		if(allUsers >= 46) {
			size++;
		}
		if(allUsers >= 55) {
			size++;
		}
		Inventory inv = Bukkit.createInventory(null, 9*size, title+titleStats);
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§7");
		placeholder.setItemMeta(placeholderMeta);
		
		ItemStack allItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta allMeta = (SkullMeta) allItem.getItemMeta();
		allMeta.setDisplayName(plugin.itemAllUsers);
		allMeta.setOwningPlayer(p);
		allItem.setItemMeta(allMeta);
		
		ItemStack allActiveUserItem = new ItemStack(Material.EMERALD);
		ItemMeta allActiveUserItemMeta = allActiveUserItem.getItemMeta();
		allActiveUserItemMeta.setDisplayName(plugin.itemActiveUsers);
		allActiveUserItem.setItemMeta(allActiveUserItemMeta);
		
		ItemStack inactiveItem = new ItemStack(Material.IRON_SWORD);
		ItemMeta inactiveMeta = inactiveItem.getItemMeta();
		inactiveMeta.setDisplayName(plugin.itemInactiveUsers);
		inactiveMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveItem.setItemMeta(inactiveMeta);
		
		ItemStack inactiveCreditItem = new ItemStack(Material.GOLD_SWORD);
		ItemMeta inactiveCreditItemMeta = inactiveCreditItem.getItemMeta();
		inactiveCreditItemMeta.setDisplayName(plugin.itemInactiveCreditUsers);
		inactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveCreditItem.setItemMeta(inactiveCreditItemMeta);
		
		ItemStack onlyInactiveCreditItem = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta onlyInactiveCreditItemMeta = onlyInactiveCreditItem.getItemMeta();
		onlyInactiveCreditItemMeta.setDisplayName(plugin.itemOnlyInactiveCreditUsers);
		onlyInactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		onlyInactiveCreditItem.setItemMeta(onlyInactiveCreditItemMeta);
		
		inv.setItem(0, allItem);
		inv.setItem(2, allActiveUserItem);
		inv.setItem(4, inactiveItem);
		inv.setItem(6, inactiveCreditItem);
		inv.setItem(8, onlyInactiveCreditItem);
		
		inv.setItem(9, placeholder);
		inv.setItem(10, placeholder);
		inv.setItem(11, placeholder);
		inv.setItem(12, placeholder);
		inv.setItem(13, placeholder);
		inv.setItem(14, placeholder);
		inv.setItem(15, placeholder);
		inv.setItem(16, placeholder);
		inv.setItem(17, placeholder);
		boolean add = true;
		int placePos = 18;
		while(add) {
			for(int i = 0; i<inactiveCredit.size(); i++) {
				inv.setItem(placePos, inactiveCredit.get(i));
				placePos++;
			}
			for(int i = 0; i<inactive.size(); i++) {
				inv.setItem(placePos, inactive.get(i));
				placePos++;
			}
			for(int i = 0; i<active.size(); i++) {
				inv.setItem(placePos, active.get(i));
				placePos++;
			}
			add = false;
		}
		return inv;
	}
	
	public static Inventory allActiveUsers(Player p) {
		int size = 2;
		if(activeUsers >= 1) {
			size++;
		}
		if(activeUsers >= 10) {
			size++;
		}
		if(activeUsers >= 19) {
			size++;
		}
		if(activeUsers >= 28) {
			size++;
		}
		if(activeUsers >= 37) {
			size++;
		}
		if(activeUsers >= 46) {
			size++;
		}
		if(activeUsers >= 55) {
			size++;
		}
		Inventory inv = Bukkit.createInventory(null, 9*size, title+titleStats);
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§7");
		placeholder.setItemMeta(placeholderMeta);
		
		ItemStack allItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta allMeta = (SkullMeta) allItem.getItemMeta();
		allMeta.setDisplayName(plugin.itemAllUsers);
		allMeta.setOwningPlayer(p);
		allItem.setItemMeta(allMeta);
		
		ItemStack allActiveUserItem = new ItemStack(Material.EMERALD);
		ItemMeta allActiveUserItemMeta = allActiveUserItem.getItemMeta();
		allActiveUserItemMeta.setDisplayName(plugin.itemActiveUsers);
		allActiveUserItem.setItemMeta(allActiveUserItemMeta);
		
		ItemStack inactiveItem = new ItemStack(Material.IRON_SWORD);
		ItemMeta inactiveMeta = inactiveItem.getItemMeta();
		inactiveMeta.setDisplayName(plugin.itemInactiveUsers);
		inactiveMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveItem.setItemMeta(inactiveMeta);
		
		ItemStack inactiveCreditItem = new ItemStack(Material.GOLD_SWORD);
		ItemMeta inactiveCreditItemMeta = inactiveCreditItem.getItemMeta();
		inactiveCreditItemMeta.setDisplayName(plugin.itemInactiveCreditUsers);
		inactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveCreditItem.setItemMeta(inactiveCreditItemMeta);
		
		ItemStack onlyInactiveCreditItem = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta onlyInactiveCreditItemMeta = onlyInactiveCreditItem.getItemMeta();
		onlyInactiveCreditItemMeta.setDisplayName(plugin.itemOnlyInactiveCreditUsers);
		onlyInactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		onlyInactiveCreditItem.setItemMeta(onlyInactiveCreditItemMeta);
		
		inv.setItem(0, allItem);
		inv.setItem(2, allActiveUserItem);
		inv.setItem(4, inactiveItem);
		inv.setItem(6, inactiveCreditItem);
		inv.setItem(8, onlyInactiveCreditItem);
		
		inv.setItem(9, placeholder);
		inv.setItem(10, placeholder);
		inv.setItem(11, placeholder);
		inv.setItem(12, placeholder);
		inv.setItem(13, placeholder);
		inv.setItem(14, placeholder);
		inv.setItem(15, placeholder);
		inv.setItem(16, placeholder);
		inv.setItem(17, placeholder);
		
		int placePos = 18;
		for(int i = 0; i<active.size(); i++) {
			inv.setItem(placePos, active.get(i));
			placePos++;
		}
		return inv;
	}
	
	public static Inventory InactivePlayers(Player p) {
		int size = 2;
		if(inactiveTotalUsers >= 1) {
			size++;
		}
		if(inactiveTotalUsers >= 10) {
			size++;
		}
		if(inactiveTotalUsers >= 19) {
			size++;
		}
		if(inactiveTotalUsers >= 28) {
			size++;
		}
		if(inactiveTotalUsers >= 37) {
			size++;
		}
		if(inactiveTotalUsers >= 46) {
			size++;
		}
		if(inactiveTotalUsers >= 55) {
			size++;
		}
		Inventory inv = Bukkit.createInventory(null, 9*size, title+titleStats);
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§7");
		placeholder.setItemMeta(placeholderMeta);
		
		ItemStack allItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta allMeta = (SkullMeta) allItem.getItemMeta();
		allMeta.setDisplayName(plugin.itemAllUsers);
		allMeta.setOwningPlayer(p);
		allItem.setItemMeta(allMeta);
		
		ItemStack allActiveUserItem = new ItemStack(Material.EMERALD);
		ItemMeta allActiveUserItemMeta = allActiveUserItem.getItemMeta();
		allActiveUserItemMeta.setDisplayName(plugin.itemActiveUsers);
		allActiveUserItem.setItemMeta(allActiveUserItemMeta);
		
		ItemStack inactiveItem = new ItemStack(Material.IRON_SWORD);
		ItemMeta inactiveMeta = inactiveItem.getItemMeta();
		inactiveMeta.setDisplayName(plugin.itemInactiveUsers);
		inactiveMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveItem.setItemMeta(inactiveMeta);
		
		ItemStack inactiveCreditItem = new ItemStack(Material.GOLD_SWORD);
		ItemMeta inactiveCreditItemMeta = inactiveCreditItem.getItemMeta();
		inactiveCreditItemMeta.setDisplayName(plugin.itemInactiveCreditUsers);
		inactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveCreditItem.setItemMeta(inactiveCreditItemMeta);
		
		ItemStack onlyInactiveCreditItem = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta onlyInactiveCreditItemMeta = onlyInactiveCreditItem.getItemMeta();
		onlyInactiveCreditItemMeta.setDisplayName(plugin.itemOnlyInactiveCreditUsers);
		onlyInactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		onlyInactiveCreditItem.setItemMeta(onlyInactiveCreditItemMeta);
		
		inv.setItem(0, allItem);
		inv.setItem(2, allActiveUserItem);
		inv.setItem(4, inactiveItem);
		inv.setItem(6, inactiveCreditItem);
		inv.setItem(8, onlyInactiveCreditItem);
		
		inv.setItem(9, placeholder);
		inv.setItem(10, placeholder);
		inv.setItem(11, placeholder);
		inv.setItem(12, placeholder);
		inv.setItem(13, placeholder);
		inv.setItem(14, placeholder);
		inv.setItem(15, placeholder);
		inv.setItem(16, placeholder);
		inv.setItem(17, placeholder);
		boolean add = true;
		int placePos = 18;
		while(add) {
			for(int i = 0; i<inactiveCredit.size(); i++) {
				inv.setItem(placePos, inactiveCredit.get(i));
				placePos++;
			}
			for(int i = 0; i<inactive.size(); i++) {
				inv.setItem(placePos, inactive.get(i));
				placePos++;
			}
			add = false;
		}
		return inv;
	}
	
	public static Inventory InactivePlayersWithCredit(Player p) {
		int size = 2;
		if(inactiveTotalUsers >= 1) {
			size++;
		}
		if(inactiveTotalUsers >= 10) {
			size++;
		}
		if(inactiveTotalUsers >= 19) {
			size++;
		}
		if(inactiveTotalUsers >= 28) {
			size++;
		}
		if(inactiveTotalUsers >= 37) {
			size++;
		}
		if(inactiveTotalUsers >= 46) {
			size++;
		}
		if(inactiveTotalUsers >= 55) {
			size++;
		}
		Inventory inv = Bukkit.createInventory(null, 9*size, title+titleStats);
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§7");
		placeholder.setItemMeta(placeholderMeta);
		
		ItemStack allItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta allMeta = (SkullMeta) allItem.getItemMeta();
		allMeta.setDisplayName(plugin.itemAllUsers);
		allMeta.setOwningPlayer(p);
		allItem.setItemMeta(allMeta);
		
		ItemStack allActiveUserItem = new ItemStack(Material.EMERALD);
		ItemMeta allActiveUserItemMeta = allActiveUserItem.getItemMeta();
		allActiveUserItemMeta.setDisplayName(plugin.itemActiveUsers);
		allActiveUserItem.setItemMeta(allActiveUserItemMeta);
		
		ItemStack inactiveItem = new ItemStack(Material.IRON_SWORD);
		ItemMeta inactiveMeta = inactiveItem.getItemMeta();
		inactiveMeta.setDisplayName(plugin.itemInactiveUsers);
		inactiveMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveItem.setItemMeta(inactiveMeta);
		
		ItemStack inactiveCreditItem = new ItemStack(Material.GOLD_SWORD);
		ItemMeta inactiveCreditItemMeta = inactiveCreditItem.getItemMeta();
		inactiveCreditItemMeta.setDisplayName(plugin.itemInactiveCreditUsers);
		inactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveCreditItem.setItemMeta(inactiveCreditItemMeta);
		
		ItemStack onlyInactiveCreditItem = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta onlyInactiveCreditItemMeta = onlyInactiveCreditItem.getItemMeta();
		onlyInactiveCreditItemMeta.setDisplayName(plugin.itemOnlyInactiveCreditUsers);
		onlyInactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		onlyInactiveCreditItem.setItemMeta(onlyInactiveCreditItemMeta);
		
		inv.setItem(0, allItem);
		inv.setItem(2, allActiveUserItem);
		inv.setItem(4, inactiveItem);
		inv.setItem(6, inactiveCreditItem);
		inv.setItem(8, onlyInactiveCreditItem);
		
		inv.setItem(9, placeholder);
		inv.setItem(10, placeholder);
		inv.setItem(11, placeholder);
		inv.setItem(12, placeholder);
		inv.setItem(13, placeholder);
		inv.setItem(14, placeholder);
		inv.setItem(15, placeholder);
		inv.setItem(16, placeholder);
		inv.setItem(17, placeholder);
		boolean add = true;
		int placePos = 18;
		while(add) {
			for(int i = 0; i<inactiveCredit.size(); i++) {
				inv.setItem(placePos, inactiveCredit.get(i));
				placePos++;
			}
			for(int i = 0; i<inactive.size(); i++) {
				inv.setItem(placePos, inactive.get(i));
				placePos++;
			}
			add = false;
		}
		return inv;
	}
	
	public static Inventory OnlyInactivePlayersWithCredit(Player p) {
		int size = 2;
		if(inactiveCreditUsers >= 1) {
			size++;
		}
		if(inactiveCreditUsers >= 10) {
			size++;
		}
		if(inactiveCreditUsers >= 19) {
			size++;
		}
		if(inactiveCreditUsers >= 28) {
			size++;
		}
		if(inactiveCreditUsers >= 37) {
			size++;
		}
		if(inactiveCreditUsers >= 46) {
			size++;
		}
		if(inactiveCreditUsers >= 55) {
			size++;
		}
		Inventory inv = Bukkit.createInventory(null, 9*size, title+titleStats);
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§7");
		placeholder.setItemMeta(placeholderMeta);
		
		ItemStack allItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta allMeta = (SkullMeta) allItem.getItemMeta();
		allMeta.setDisplayName(plugin.itemAllUsers);
		allMeta.setOwningPlayer(p);
		allItem.setItemMeta(allMeta);
		
		ItemStack allActiveUserItem = new ItemStack(Material.EMERALD);
		ItemMeta allActiveUserItemMeta = allActiveUserItem.getItemMeta();
		allActiveUserItemMeta.setDisplayName(plugin.itemActiveUsers);
		allActiveUserItem.setItemMeta(allActiveUserItemMeta);
		
		ItemStack inactiveItem = new ItemStack(Material.IRON_SWORD);
		ItemMeta inactiveMeta = inactiveItem.getItemMeta();
		inactiveMeta.setDisplayName(plugin.itemInactiveUsers);
		inactiveMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveItem.setItemMeta(inactiveMeta);
		
		ItemStack inactiveCreditItem = new ItemStack(Material.GOLD_SWORD);
		ItemMeta inactiveCreditItemMeta = inactiveCreditItem.getItemMeta();
		inactiveCreditItemMeta.setDisplayName(plugin.itemInactiveCreditUsers);
		inactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inactiveCreditItem.setItemMeta(inactiveCreditItemMeta);
		
		ItemStack onlyInactiveCreditItem = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta onlyInactiveCreditItemMeta = onlyInactiveCreditItem.getItemMeta();
		onlyInactiveCreditItemMeta.setDisplayName(plugin.itemOnlyInactiveCreditUsers);
		onlyInactiveCreditItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		onlyInactiveCreditItem.setItemMeta(onlyInactiveCreditItemMeta);
		
		inv.setItem(0, allItem);
		inv.setItem(2, allActiveUserItem);
		inv.setItem(4, inactiveItem);
		inv.setItem(6, inactiveCreditItem);
		inv.setItem(8, onlyInactiveCreditItem);
		
		inv.setItem(9, placeholder);
		inv.setItem(10, placeholder);
		inv.setItem(11, placeholder);
		inv.setItem(12, placeholder);
		inv.setItem(13, placeholder);
		inv.setItem(14, placeholder);
		inv.setItem(15, placeholder);
		inv.setItem(16, placeholder);
		inv.setItem(17, placeholder);
		int placePos = 18;
		for(int i = 0; i<inactiveCredit.size(); i++) {
			inv.setItem(placePos, inactiveCredit.get(i));
			placePos++;
		}
		return inv;
	}
}
