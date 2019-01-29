package blockstone.B1GSt4R.BankCredit.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.OfflinePlayer;

@SuppressWarnings("static-access")
public class JDBC {
	
	public static blockstone.B1GSt4R.BankCredit.Main.system plugin;
	
	public static Connection con;
	
	public static void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://"+plugin.hosts+":"+plugin.ports+"/"+plugin.dbnames, plugin.users, plugin.pws);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public static Connection getConnection() {
		return con;
	}
	
	public static void initConnection() {
		try {
			PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS BankCredit_Credits (CreditID VARCHAR(255) NOT NULL, CreditName VARCHAR(255), CreditValue NUMERIC(65,2), PayTax DOUBLE, LeaseTime INTEGER(255), TimeRank_NeededRank BIGINT(255), PRIMARY KEY(CreditID, CreditName))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS BankCredit_PlayerCredits (PlayerUUID_CreditID VARCHAR(255) NOT NULL, PlayerUUID VARCHAR(255) NOT NULL, NextPayDate VARCHAR(255), NextPayTime VARCHAR(255), DaysLeft INTEGER(255), RemainingCreditValue NUMERIC(65,2), DeferralCounter INTEGER(255), NotPayedDays INTEGER(255), ExtraPays NUMERIC(65,2), PunishPay NUMERIC(65,2), PRIMARY KEY (PlayerUUID_CreditID))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> credits = new ArrayList<>();
		credits = getAllCreditIds();
		if(credits.size() == 0) {
			createCredit("C1", "&b5.000$", 5000.00, 2.49, 7, 0);
			createCredit("C2", "&b10.000$", 10000.00, 2.49, 7, 0);
			createCredit("C3", "&b25.000$", 25000.00, 2.49, 7, 5);
			createCredit("C4", "&b50.000$", 50000.00, 2.49, 7, 15);
			createCredit("C5", "&b100.000$", 100000.00, 2.49, 7, 25);
			createCredit("C6", "&b250.000$", 250000.00, 2.49, 7, 50);
		}
	}
	
	/*Credit Table Stuff*/
	
	public static boolean isCreditExists(String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM BankCredit_Credits WHERE CreditID = ?");
			ps.setString(1, creditID);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<String> getAllCreditIds() {
		ArrayList<String> creditList = new ArrayList<>();
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT CreditID FROM BankCredit_Credits");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				creditList.add(rs.getString("CreditID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return creditList;
	}
	
	public static String getCreditName(String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT CreditName FROM BankCredit_Credits WHERE CreditID = ?");
			ps.setString(1, creditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getString("CreditName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static double getCreditValue(String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT CreditValue FROM BankCredit_Credits WHERE CreditID = ?");
			ps.setString(1, creditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getDouble("CreditValue");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.00;
	}
	
	public static double getCreditPayTax(String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT PayTax FROM BankCredit_Credits WHERE CreditID = ?");
			ps.setString(1, creditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getDouble("PayTax");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.00;
	}
	
	public static int getCreditLeaseTime(String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT LeaseTime FROM BankCredit_Credits WHERE CreditID = ?");
			ps.setString(1, creditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("LeaseTime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getCreditNeededRank(String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT TimeRank_NeededRank FROM BankCredit_Credits WHERE CreditID = ?");
			ps.setString(1, creditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("TimeRank_NeededRank");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void createCredit(String creditID, String creditName, double creditValue, double payTax, int leaseTime, int neededRank) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT IGNORE BankCredit_Credits VALUES (?,?,?,?,?,?)");
			ps.setString(1, creditID);
			ps.setString(2, creditName);
			ps.setDouble(3, creditValue);
			ps.setDouble(4, payTax);
			ps.setInt(5, leaseTime);
			ps.setInt(6, neededRank);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setCreditName(String creditID, String creditName) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_Credits SET CreditName = ? WHERE CreditID = ?");
			ps.setString(1, creditName);
			ps.setString(2, creditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setCreditValue(String creditID, double creditValue) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_Credits SET CreditValue = ? WHERE CreditID = ?");
			ps.setDouble(1, creditValue);
			ps.setString(2, creditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setPayTax(String creditID, double payTax) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_Credits SET PayTax = ? WHERE CreditID = ?");
			ps.setDouble(1, payTax);
			ps.setString(2, creditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setLeaseTime(String creditID, int leaseTime) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_Credits SET LeaseTime = ? WHERE CreditID = ?");
			ps.setInt(1, leaseTime);
			ps.setString(2, creditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setNeededRank(String creditID, int neededRank) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_Credits SET TimeRank_NeededRank = ? WHERE CreditID = ?");
			ps.setInt(1, neededRank);
			ps.setString(2, creditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeCredit(String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("DELETE FROM BankCredit_Credits WHERE CreditID = ?");
			ps.setString(1, creditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*PlayerCredit Table Stuff*/
	
	public static boolean isExistsPlayerCredit(OfflinePlayer p, String creditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, p.getUniqueId().toString()+"_"+creditID);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<String> getPlayerUUID_CreditID_List(OfflinePlayer p) {
		ArrayList<String> list = new ArrayList<>();
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT PlayerUUID_CreditID FROM BankCredit_PlayerCredits WHERE PlayerUUID = ?");
			ps.setString(1, p.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("PlayerUUID_CreditID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static String getPlayerUUID(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT PlayerUUID FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getString("PlayerUUID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNextPayDate(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT NextPayDate FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getString("NextPayDate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNextPayTime(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT NextPayTime FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getString("NextPayTime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getDaysLeft(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT DaysLeft FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("DaysLeft");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static double getRemainingCreditValue(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT RemainingCreditValue FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getDouble("RemainingCreditValue");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.00;
	}
	
	public static int getDeferralCounter(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT DeferralCounter FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("DeferralCounter");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getNotPayedDays(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT NotPayedDays FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("NotPayedDays");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static double getExtraPays(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT ExtraPays FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getDouble("ExtraPays");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.00;
	}
	
	public static double getPunishPays(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT PunishPay FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getDouble("PunishPay");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.00;
	}
	
	public static void updateNextPayDate(String PlayerUUID_CreditID, String NextPayDate) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET NextPayDate = ? WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, NextPayDate);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateNextPayTime(String PlayerUUID_CreditID, String NextPayTime) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET NextPayTime = ? WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, NextPayTime);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateDaysLeft(String PlayerUUID_CreditID, int DaysLeft) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET DaysLeft = ? WHERE PlayerUUID_CreditID = ?");
			ps.setInt(1, DaysLeft);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateRemainingCreditValue(String PlayerUUID_CreditID, double RemainingCreditValue) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET RemainingCreditValue = ? WHERE PlayerUUID_CreditID = ?");
			ps.setDouble(1, RemainingCreditValue);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateDeferralCounter(String PlayerUUID_CreditID, int DeferralCounter) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET DeferralCounter = ? WHERE PlayerUUID_CreditID = ?");
			ps.setInt(1, DeferralCounter);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateNotPayedDays(String PlayerUUID_CreditID, int NotPayedDays) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET NotPayedDays = ? WHERE PlayerUUID_CreditID = ?");
			ps.setInt(1, NotPayedDays);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateExtraPays(String PlayerUUID_CreditID, double ExtraPays) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET ExtraPays = ? WHERE PlayerUUID_CreditID = ?");
			ps.setDouble(1, ExtraPays);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePunishPay(String PlayerUUID_CreditID, double PunishPay) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("UPDATE BankCredit_PlayerCredits SET PunishPay = ? WHERE PlayerUUID_CreditID = ?");
			ps.setDouble(1, PunishPay);
			ps.setString(2, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createPlayerCredit(OfflinePlayer p, String CreditID, String NextPayDate, String NextPayTime, int DaysLeft, double RemainingCreditValue, int DeferralCounter, int NotPayedDays, double ExtraPays, double PunishPay) {
		String PlayerUUID = p.getUniqueId().toString();
		String PlayerUUID_CreditID = PlayerUUID+"_"+CreditID;
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT BankCredit_PlayerCredits VALUES (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, PlayerUUID_CreditID);
			ps.setString(2, PlayerUUID);
			ps.setString(3, NextPayDate);
			ps.setString(4, NextPayTime);
			ps.setInt(5, DaysLeft);
			ps.setDouble(6, RemainingCreditValue);
			ps.setInt(7, DeferralCounter);
			ps.setInt(8, NotPayedDays);
			ps.setDouble(9, ExtraPays);
			ps.setDouble(10, PunishPay);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createPlayerCredit(OfflinePlayer p, String CreditID) {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat st = new SimpleDateFormat("HH:mm");
		String df = sd.format(date).toString();
		
		String PlayerUUID = p.getUniqueId().toString();
		String PlayerUUID_CreditID = PlayerUUID+"_"+CreditID;
		String NextPayDate = plugin.api.changeDateDay(df, 1, true);
		String NextPayTime = st.format(date).toString();
		int DaysLeft = 0;
		double RemainingCreditValue = 0.00;
		int DeferralCounter = 0;
		int NotPayedDays = 0;
		double ExtraPays = 0.00;
		double PunishPay = 0.00;
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("INSERT BankCredit_PlayerCredits VALUES (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, PlayerUUID_CreditID);
			ps.setString(2, PlayerUUID);
			ps.setString(3, NextPayDate);
			ps.setString(4, NextPayTime);
			ps.setInt(5, DaysLeft);
			ps.setDouble(6, RemainingCreditValue);
			ps.setInt(7, DeferralCounter);
			ps.setInt(8, NotPayedDays);
			ps.setDouble(9, ExtraPays);
			ps.setDouble(10, PunishPay);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removePlayerCredit(String PlayerUUID_CreditID) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("DELETE FROM BankCredit_PlayerCredits WHERE PlayerUUID_CreditID = ?");
			ps.setString(1, PlayerUUID_CreditID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
