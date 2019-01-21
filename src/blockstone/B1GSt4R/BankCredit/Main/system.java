package blockstone.B1GSt4R.BankCredit.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import blockstone.B1GSt4R.BankCredit.Utils.creditConfigSystem;
import blockstone.B1GSt4R.BankCredit.Utils.schufaSystem;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class system extends JavaPlugin {
	
	public static blockstone.B1GSt4R.BankCredit.Utils.JDBC sql;
	public static blockstone.B1GSt4R.BankCredit.Utils.API api;
	public static blockstone.B1GSt4R.BankCredit.Utils.creditConfigSystem credit;
	public static blockstone.B1GSt4R.BankCredit.Utils.schufaSystem schufa;
	public static blockstone.B1GSt4R.timeRang.Main.system timeRankAPI;
	
	public ConsoleCommandSender CONSOLE = this.getServer().getConsoleSender();
	public PluginManager pm = this.getServer().getPluginManager();
	
	public static Economy eco = null;
	
	public String strich = "§7§m---------------§8< §6§lBank§eCredit §8>§7§m---------------";
	public String strichWarning = "§c§l!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
	public String prefix = "§6§lBank§eCredit §8>> §7";
	public String menuPrefix = "§8Bank of §5BlockStone";
	public String failed = prefix+"§c";
	
	public boolean TimeRank = pm.getPlugin("TimeRank") != null;
	public boolean Vault = pm.getPlugin("Vault") != null;
	
	public File fileCfg = new File("plugins/"+this.getDescription().getName()+"/Settings", "Config.yml");
	public File fileMySQL = new File("plugins/"+this.getDescription().getName()+"/Settings", "MySQL.yml");
	public File filePlayerCreditCfg = new File("plugins/"+this.getDescription().getName()+"/Storage", "Database.PlayerCredits.yml");
	
	public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(fileCfg);
	public YamlConfiguration sqlCfg = YamlConfiguration.loadConfiguration(fileMySQL);
	public YamlConfiguration playerCreditCfg = YamlConfiguration.loadConfiguration(filePlayerCreditCfg);
	
	public static String hosts, ports, dbnames, users, pws;
	public static String Errormsg = "";
	public static int Errorcode = 0;
	boolean host, port, dbname, user, pw;
	static boolean jdbcError = false;
	
	@Override
	public void onEnable() {
		if(Vault) {
			
			if(sqlCfg.get("Host") == null) {
				sqlCfg.set("Host", "localhost");
				saveCfg();
			}
			if(sqlCfg.get("Port") == null) {
				sqlCfg.set("Port", "3306");
				saveCfg();
			}
			if(sqlCfg.get("DBname") == null) {
				sqlCfg.set("DBname", "YourDataBase");
				saveCfg();
			}
			if(sqlCfg.get("Username") == null) {
				sqlCfg.set("Username", "YourUsername");
				saveCfg();
			}
			if(sqlCfg.get("Password") == null) {
				sqlCfg.set("Password", "YourPassword");
				saveCfg();
			}
			
			hosts = sqlCfg.getString("Host");
			ports = sqlCfg.getString("Port");
			dbnames = sqlCfg.getString("DBname");
			users = sqlCfg.getString("Username");
			pws = sqlCfg.getString("Password");
			
			host = sqlCfg.get("Host") != null;
			port = sqlCfg.get("Port") != null;
			dbname = sqlCfg.get("DBname") != null && !sqlCfg.get("DBname").equals("YourDataBase");
			user = sqlCfg.get("Username") != null && !sqlCfg.get("Username").equals("YourUsername");
			
			if(host && port && dbname && user) {
				sql.connect();
			}
			
			msgLoader(true);
			setupEconomy();
			
			new blockstone.B1GSt4R.BankCredit.Events.invClickListenerBank(this);
			new blockstone.B1GSt4R.BankCredit.Events.joinListener(this);
			
			new schufaSystem(this);
			new creditConfigSystem(this);
			
			getCommand("Bank").setExecutor(new blockstone.B1GSt4R.BankCredit.Commands.bankCmd(this));
			
			
			if(!fileCfg.exists() && !filePlayerCreditCfg.exists()) {
				saveCfg();
			}else {
				loadCfg();
			}
			
			ArrayList<String> creditList = api.getAllCreditIds();
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					for(Player all : Bukkit.getOnlinePlayers()) {
						for(int i = 0; i<creditList.size(); i++) {
							if(api.isExistsPlayerCredit(all, creditList.get(i))) {
								if(api.getDate().equals(api.getNextPayDate(api.builderPlayerUUID_CreditID(all, creditList.get(i))))) {
									if(api.getTime().equals(api.getNextPayTime(api.builderPlayerUUID_CreditID(all, creditList.get(i))))) {
										if(api.getDaysLeft(api.builderPlayerUUID_CreditID(all, creditList.get(i))) > 1) {
											double value = api.getRemainingCreditValue(api.builderPlayerUUID_CreditID(all, creditList.get(i))) / api.getDaysLeft(api.builderPlayerUUID_CreditID(all, creditList.get(i)));
											payOffCredit(all, creditList.get(i), value, false);
										}else {
											double value = api.getRemainingCreditValue(api.builderPlayerUUID_CreditID(all, creditList.get(i)));
											payOffCredit(all, creditList.get(i), value, false);
										}
									}
								}
							}
						}
					}
				}
			}, 0, 60*20);
		}else {
			stoppOfVault();
		}
	}

	@Override
	public void onDisable() {
		loadCfg();
		saveCfg();
		sql.disconnect();
		msgLoader(false);
	}
	
	private void msgLoader(boolean statusLoaded) {
		if(statusLoaded) {
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage("§7Name: §6"+this.getDescription().getName());
			CONSOLE.sendMessage("§7Version: §6"+this.getDescription().getVersion());
			CONSOLE.sendMessage("§7Author: §6"+this.getDescription().getAuthors().get(0));
			CONSOLE.sendMessage(" ");
			
			CONSOLE.sendMessage("§7Status Plugin: §2ONLINE");
			if(Vault) {
				CONSOLE.sendMessage("§7Status Vault: §2FOUND");
			}else if(!Vault){
				CONSOLE.sendMessage("§7Status Vault: §4NOT FOUND");
			}
			
			if(TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §2FOUND");
			}else if(!TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §4NOT FOUND");
			}
			
			if(sql.isConnected()) {
				sql.initConnection();
				CONSOLE.sendMessage("§7Status MySQL: §2CONNECTED");
			}else if(!sql.isConnected()){
				if(jdbcError == true) {
					CONSOLE.sendMessage(strichWarning);
					CONSOLE.sendMessage("§7Status MySQL: §4ERROR WHILE STARTING!");
					CONSOLE.sendMessage("§fError Code: §6"+Errorcode);
					CONSOLE.sendMessage("§fError Message: §6"+Errormsg);
					CONSOLE.sendMessage(strichWarning);
				}else if(!host || !port || !dbname || !user){
					CONSOLE.sendMessage("§7Status MySQL: §4NOT FOUND");
				}
			}
			
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
		}else {
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage("§7Name: §6"+this.getDescription().getName());
			CONSOLE.sendMessage("§7Version: §6"+this.getDescription().getVersion());
			CONSOLE.sendMessage("§7Author: §6"+this.getDescription().getAuthors().get(0));
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage("§7Status Plugin: §4OFFLINE");
			if(Vault) {
				CONSOLE.sendMessage("§7Status Vault: §2FOUND");
			}else if(!Vault){
				CONSOLE.sendMessage("§7Status Vault: §4NOT FOUND");
			}
			
			if(TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §2FOUND");
			}else if(!TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §4NOT FOUND");
			}
			
			if(sql.isConnected()) {
				CONSOLE.sendMessage("§7Status MySQL: §4DISCONNECTED");
			}else if(!sql.isConnected()){
				if(jdbcError == true) {
					CONSOLE.sendMessage(strichWarning);
					CONSOLE.sendMessage("§7Status MySQL: §4ERROR WHILE STOPPING!");
					CONSOLE.sendMessage("§fError Code: §6"+Errorcode);
					CONSOLE.sendMessage("§fError Message: §6"+Errormsg);
					CONSOLE.sendMessage(strichWarning);
				}else if(!host || !port || !dbname || !user){
					CONSOLE.sendMessage("§7Status MySQL: §4NOT FOUND");
				}
			}
			
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
		}
	}

	private void stoppOfVault() {
		pm.disablePlugin(this);
		CONSOLE.sendMessage(" ");
		CONSOLE.sendMessage(strichWarning);
		CONSOLE.sendMessage(" ");
		CONSOLE.sendMessage("§fStopping the Plugin!!!");
		CONSOLE.sendMessage("§fPlease Install Vault on your Server!!!");
		CONSOLE.sendMessage(" ");
		CONSOLE.sendMessage(strichWarning);
		CONSOLE.sendMessage(" ");
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> ecoProvider = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if(ecoProvider != null) {
			eco = ecoProvider.getProvider();
		}
		
		return (eco != null);
	}
	
	private void loadCfg() {
		try {
			cfg.load(fileCfg);
			sqlCfg.load(fileMySQL);
			playerCreditCfg.load(filePlayerCreditCfg);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		saveCfg();
	}
	
	public void saveCfg() {
		try {
			cfg.save(fileCfg);
			sqlCfg.save(fileMySQL);
			playerCreditCfg.save(filePlayerCreditCfg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addMoney(Player p, double cash) {
		EconomyResponse r = eco.depositPlayer(p, cash);
		if(r.transactionSuccess()) {
			p.sendMessage(prefix+"Du hast dir einen Kredit von §e"+cash+"$ §7geholt.");
		}else {
			p.sendMessage(failed+r.errorMessage);
		}
	}
	
	public void payOffCredit(Player p, String CreditID, double value, boolean self) {
		if(self) {
			//p.closeInventory();
		}
		String PlayerUUID_CreditID = api.builderPlayerUUID_CreditID(p, CreditID);
		EconomyResponse r = system.eco.withdrawPlayer(p, value);
		if(r.transactionSuccess()) {
			if(api.getRemainingCreditValue(PlayerUUID_CreditID) == value) {
				p.sendMessage(prefix+"Du hast die restlichen §e"+value+ "$ §7zurück gezahlt.");
				api.removePlayerCredit(p, CreditID);
			}else {
				api.subtractDaysLeft(PlayerUUID_CreditID, 1);
				api.subtractRemainingCreditValue(PlayerUUID_CreditID, value);
				api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
				p.sendMessage(prefix+"Du hast §e"+value+"$ §7für deinen Kredit zurück gezahlt.");
			}
			
		}else {
			//p.sendMessage(plugin.failed+r.errorMessage);
			p.sendMessage(strich);
			p.sendMessage(prefix+"§cDu hast nicht genug Geld auf deinem Konto!");
			p.sendMessage("§7 ");
			p.sendMessage(prefix+"Betrag: §e"+api.getRemainingCreditValue(PlayerUUID_CreditID)+"$");
			p.sendMessage(prefix+"Fehlender Betrag: §e"+(api.getRemainingCreditValue(PlayerUUID_CreditID)-eco.getBalance(p))+"$");
			p.sendMessage(strich);
			if(!self) {
				api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
				api.addNotPayedDays(PlayerUUID_CreditID, 1);
			}
		}
	}
}
