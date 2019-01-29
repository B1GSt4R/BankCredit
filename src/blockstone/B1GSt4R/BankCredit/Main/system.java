package blockstone.B1GSt4R.BankCredit.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import blockstone.B1GSt4R.BankCredit.Utils.creditConfigSystem;
import blockstone.B1GSt4R.BankCredit.Utils.schufaSystem;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

@SuppressWarnings("static-access")
public class system extends JavaPlugin {
	
	public static blockstone.B1GSt4R.BankCredit.Utils.JDBC sql;
	public static blockstone.B1GSt4R.BankCredit.Utils.API api;
	public static blockstone.B1GSt4R.BankCredit.Utils.creditConfigSystem credit;
	public static blockstone.B1GSt4R.BankCredit.Utils.schufaSystem schufa;
	public static blockstone.B1GSt4R.timeRank.Main.system timeRankAPI;
	
	public ConsoleCommandSender CONSOLE = this.getServer().getConsoleSender();
	public PluginManager pm = this.getServer().getPluginManager();
	
	public static Economy eco = null;
	
	public String strich = "§7§m---------------§8< §6§lBank§eCredit §8>§7§m---------------";
	public String strichWarning = "§c§l!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
	public String prefix = "§6§lBank§eCredit §8>> §7";
	public String menuPrefix = "§8Bank of §5BlockStone";
	public String failed = prefix+"§c";
	public String versionURL = "https://www.B1GSt4R.de/bukkit-plugins/BankCredit/version.rss";
	public String license;
	
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
	public boolean useSQL = false;
	public boolean validLicense = false;
	
	public String[] adminPerms = {
			/*1.*/		"*", 
			/*2.*/		"BankCredit.*", 
			/*3.*/		"BankCredit.admin.*", 
			/*4.*/		"BankCredit.admin.time.*",
			/*5.*/		"BankCredit.admin.time.add",
			/*6.*/		"BankCredit.admin.time.set",
			/*7.*/		"BankCredit.admin.time.del",
			/*8.*/		"BankCredit.admin.rank.*",
			/*9.*/		"BankCredit.admin.rank.add",
			/*10.*/		"BankCredit.admin.rank.set",
			/*11.*/		"BankCredit.admin.rank.del",
			/*12.*/		"BankCredit.admin.help.*",
			/*13.*/		"BankCredit.admin.help.admin",
			/*14.*/		"BankCredit.admin.version"
					};
			
			public String[] userPerms = {
			/*1.*/		"BankCredit.user.*", 
			/*2.*/		"BankCredit.user.time",
			/*3.*/		"BankCredit.user.rank",
			/*4.*/		"BankCredit.user.help.user"
					};
	
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
			
			if(cfg.get("License") == null) {
				cfg.set("License", "ABCDE-12345-FGH67-890IJ-KLMNOPQRST");
				saveCfg();
			}
			
			hosts = sqlCfg.getString("Host");
			ports = sqlCfg.getString("Port");
			dbnames = sqlCfg.getString("DBname");
			users = sqlCfg.getString("Username");
			pws = sqlCfg.getString("Password");
			license = cfg.getString("License");
			
			host = sqlCfg.get("Host") != null;
			port = sqlCfg.get("Port") != null;
			dbname = sqlCfg.get("DBname") != null && !sqlCfg.get("DBname").equals("YourDataBase");
			user = sqlCfg.get("Username") != null && !sqlCfg.get("Username").equals("YourUsername");
			
			msgLoader(true);
			
			getCommand(this.getDescription().getName()).setExecutor(new blockstone.B1GSt4R.BankCredit.Commands.bankCreditCMD(this));
			
			if(checkLicense(license)) {
				if(host && port && dbname && user) {
					sql.connect();
					useSQL = true;
				}
				
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
						if(useSQL && sql.isConnected()) {
							sql.disconnect();
							sql.connect();
						}
						for(OfflinePlayer all : Bukkit.getOfflinePlayers()) {
							for(int i = 0; i<creditList.size(); i++) {
								if(api.isExistsPlayerCredit(all, creditList.get(i))) {
									if(api.getDate().equals(api.getNextPayDate(api.builderPlayerUUID_CreditID(all, creditList.get(i))))) {
										if(api.getTime().equals(api.getNextPayTime(api.builderPlayerUUID_CreditID(all, creditList.get(i))))) {
											if(api.getDaysLeft(api.builderPlayerUUID_CreditID(all, creditList.get(i))) > 1) {
												double creditValue = api.getCreditValue(creditList.get(i));
												double leaseTime = api.getCreditLeaseTime(creditList.get(i));
												double remaining = api.getRemainingCreditValue(api.builderPlayerUUID_CreditID(all, creditList.get(i)));
												double nominalzinssatz = api.getCreditPayTax(creditList.get(i)) / 100;
												
												double tilgung = creditValue / leaseTime;
												double zinsen = remaining / leaseTime * nominalzinssatz;
												
												double value = Math.round(tilgung+zinsen);
												value = Math.round(value*100)/100.0;
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
			}
		}else {
			stoppOfVault();
		}
	}

	@Override
	public void onDisable() {
		msgLoader(false);
		if(validLicense) {
			loadCfg();
			saveCfg();
			if(useSQL && sql.isConnected()) {
				sql.disconnect();
			}
		}
	}
	
	private void msgLoader(boolean statusLoaded) {
		String newVersion = ReadURL(versionURL);
		if(statusLoaded) {
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage("§7Name: §6"+this.getDescription().getName());
			CONSOLE.sendMessage("§7Version: §6"+this.getDescription().getVersion());
			if(!newVersion.equals(this.getDescription().getVersion())) {
				CONSOLE.sendMessage("§cNew Update Found!");
			}
			CONSOLE.sendMessage("§7Author: §6"+this.getDescription().getAuthors().get(0));
			CONSOLE.sendMessage(" ");
			
			if(!license.equals("ABCDE-12345-FGH67-890IJ-KLMNOPQRST") && checkLicense(license)) {
				CONSOLE.sendMessage("§7Status Plugin: §2ONLINE");
			}else {
				CONSOLE.sendMessage("§7Status Plugin: §eNOT AUTHORISED");
			}
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
				}else if(!validLicense) {
					CONSOLE.sendMessage("§7Status MySQL: §4DISABLED");
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
			if(!newVersion.equals(this.getDescription().getVersion())) {
				CONSOLE.sendMessage("§cNew Update Found");
			}
			CONSOLE.sendMessage("§7Author: §6"+this.getDescription().getAuthors().get(0));
			CONSOLE.sendMessage(" ");
			if(!license.equals("ABCDE-12345-FGH67-890IJ-KLMNOPQRST") && checkLicense(license)) {
				CONSOLE.sendMessage("§7Status Plugin: §4OFFLINE");
			}else {
				CONSOLE.sendMessage("§7Status Plugin: §eNOT AUTHORISED");
			}
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
				}else if(!validLicense) {
					CONSOLE.sendMessage("§7Status MySQL: §4DISABLED");
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
	
	public boolean checkLicense(String license) {
//		String url = "ftp://76ymNJQkrp35UDMsVNVK8RsZQS65KjELN2MxabtMN4upQEQuyr:eJVxvy5yqGhrjjRSGxKuUW34VxUXMen62zknKmaxXyUDSU2Rgz@GnnSDDRT24dt8j4mZ2yCUEfkXTRDsT6A.B1GSt4R.de/bukkit-plugins/TimeRank/U9qSGkE6VY4F3zKZcaZVujBkqUuwcWXBvMS7JdGAJuFgu2ajvk7neegj62L3Xt6GLfYE9msPGDYMUGC4MBxEmwwXZUSj4D2PQxuyHU4wjvh49FthJ8FRQANKCgtzjncN.rss";
		String url = "ftp://web-3:xZVjTgyS7uDNPEhdx3BhyqCBT@wQFDLz9WtqwskSqxECg7JwxyQ.user.server.blockstone.de/htdocs/bukkit-plugins/BankCredit/5AU4WK6KVVUE2M8hbduf7BZC6SqUQpXedUseCeDRepK2CWD8fsrAsQurbRujEAGYb3wkhrxxevmC7yxuxe4e68GRrMrNFUW5TkvMTxSKeWw7LGpqEctun9bCq4TKYYuE.rss";
		HashMap<String, Boolean> LizenzList = ReadLicenseList(url);
		boolean valid = false;
		String hashText = "";
//		for(String license : LizenzList.keySet()) {
			try {
				//Lizenz to MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.reset();
				md.update(license.getBytes());
				byte[] digest = md.digest();
				BigInteger bigInt = new BigInteger(1,digest);
				String MD5 = bigInt.toString(16);
				
				//Lizenz to SHA-256
				md = MessageDigest.getInstance("SHA-256");
				md.reset();
				md.update(license.getBytes());
				digest = md.digest();
				bigInt = new BigInteger(1,digest);
				String SHA256 = bigInt.toString(16);
				
				//MD5 to SHA-1
				md = MessageDigest.getInstance("SHA-1");
				md.reset();
				md.update(MD5.getBytes());
				digest = md.digest();
				bigInt = new BigInteger(1,digest);
				String SHA1 = bigInt.toString(16);
				
				String tmp = SHA256+SHA1;
				
				//SHA-256+SHA-1 to SHA-512
				md = MessageDigest.getInstance("SHA-512");
				md.reset();
				md.update(tmp.getBytes());
				digest = md.digest();
				bigInt = new BigInteger(1,digest);
				hashText = bigInt.toString(16);
//				CONSOLE.sendMessage("§c"+license+" §8- §a"+hashText);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
//		}
//		for(String key : LizenzList.keySet()) {
//			CONSOLE.sendMessage("§b"+key+"§7 / §c"+LizenzList.get(key));
//			CONSOLE.sendMessage("§e"+hashText+"§7 / §d"+license);
//		}
		
		if(!LizenzList.containsKey(hashText) || !LizenzList.get(hashText)) {
//			CONSOLE.sendMessage("§cINVALID LICENSE");
//			this.license = null;
			valid = false;
		}else {
//			CONSOLE.sendMessage("§aVALID LICENSE");
			valid = true;
			validLicense = true;
		}
		LizenzList = null;
		return valid;
	}
	
	public String ReadURL(String URL) {
		String ver = "";
		try {
			URL url = new URL(URL);
			Reader is = new InputStreamReader(url.openStream());
			BufferedReader in = new BufferedReader(is);
			for(String s; (s = in.readLine()) != null;)
				ver += s;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ver;
	}
	
	public HashMap<String, Boolean> ReadLicenseList(String URL){
		HashMap<String, Boolean> licenseList = new HashMap<>();
		try {
			InputStream is = new URL(URL).openConnection().getInputStream();
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
			int list = doc.getElementsByTagName("license").getLength();
			for(int i = 0; i < list; i++) {
				Node keys = doc.getElementsByTagName("license").item(i);
				NodeList child = keys.getChildNodes();
				licenseList.put(child.item(1).getTextContent().toString(), Boolean.parseBoolean(child.item(3).getTextContent().toString()));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return licenseList;
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
	
	public void payOffCredit(OfflinePlayer player, String CreditID, double value, boolean self) {
		if(self) {
			//p.closeInventory();
		}
		String PlayerUUID_CreditID = api.builderPlayerUUID_CreditID(player, CreditID);
		EconomyResponse r = system.eco.withdrawPlayer(player, value);
		Player p = null;
		if(Bukkit.getPlayer(player.getUniqueId()) != null) {
			p = Bukkit.getPlayer(player.getUniqueId());
		}
		if(r.transactionSuccess()) {
			if(api.getRemainingCreditValue(PlayerUUID_CreditID) == value) {
				if(p != null) {
					p.sendMessage(prefix+"Du hast die restlichen §e"+value+ "$ §7zurück gezahlt.");
				}
				api.removePlayerCredit(player, CreditID);
			}else {
				api.subtractDaysLeft(PlayerUUID_CreditID, 1);
				api.subtractRemainingCreditValue(PlayerUUID_CreditID, value);
				api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
				if(p != null) {
					p.sendMessage(prefix+"Du hast §e"+value+"$ §7für deinen Kredit zurück gezahlt.");
				}
			}
			
		}else {
			//p.sendMessage(plugin.failed+r.errorMessage);
			if(p != null) {
				p.sendMessage(strich);
				p.sendMessage(prefix+"§cDu hast nicht genug Geld auf deinem Konto!");
				p.sendMessage("§7 ");
				p.sendMessage(prefix+"Betrag: §e"+api.getRemainingCreditValue(PlayerUUID_CreditID)+"$");
				p.sendMessage(prefix+"Fehlender Betrag: §e"+(api.getRemainingCreditValue(PlayerUUID_CreditID)-eco.getBalance(p))+"$");
				p.sendMessage(strich);
			}
			if(!self) {
				api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
				api.addNotPayedDays(PlayerUUID_CreditID, 1);
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase(this.getDescription().getName())) {
			String newVersion = ReadURL(versionURL);
			if(sender instanceof Player) {
				Player p = (Player)sender;
				boolean perm = p.isOp() ||
						p.hasPermission(adminPerms[0]) ||
						p.hasPermission(adminPerms[1]) ||
						p.hasPermission(adminPerms[2]) ||
						p.hasPermission(adminPerms[13]);
				if(perm && !newVersion.equals(getDescription().getVersion())) {
					p.sendMessage(strichWarning);
					p.sendMessage(prefix+"There is a new Version!");
					p.sendMessage(prefix+"Current Version: §6"+getDescription().getVersion());
					p.sendMessage(prefix+"New Version: §6"+newVersion);
					p.sendMessage(" ");
					p.sendMessage(prefix+"Download Link below:");
					p.sendMessage(prefix+"https://www.B1GSt4R.de/my-account/downloads/");
					p.sendMessage(strichWarning);
					
					CONSOLE.sendMessage(strichWarning);
					CONSOLE.sendMessage(prefix+"There is a new Version!");
					CONSOLE.sendMessage(prefix+"Current Version: §6"+getDescription().getVersion());
					CONSOLE.sendMessage(prefix+"New Version: §6"+newVersion);
					CONSOLE.sendMessage(" ");
					CONSOLE.sendMessage(prefix+"Download Link below:");
					CONSOLE.sendMessage(prefix+"https://www.B1GSt4R.de/my-account/downloads/");
					CONSOLE.sendMessage(strichWarning);
				}
			}else {
				if(!newVersion.equals(getDescription().getVersion())) {
					CONSOLE.sendMessage(strichWarning);
					CONSOLE.sendMessage(prefix+"There is a new Version!");
					CONSOLE.sendMessage(prefix+"Current Version: §6"+getDescription().getVersion());
					CONSOLE.sendMessage(prefix+"New Version: §6"+newVersion);
					CONSOLE.sendMessage(" ");
					CONSOLE.sendMessage(prefix+"Download Link below:");
					CONSOLE.sendMessage(prefix+"https://www.B1GSt4R.de/my-account/downloads/");
					CONSOLE.sendMessage(strichWarning);
				}
			}
		}
		return true;
	}
}
