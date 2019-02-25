package blockstone.B1GSt4R.BankCredit.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.OfflinePlayer;

@SuppressWarnings("static-access")
public class API {
	
	public static blockstone.B1GSt4R.BankCredit.Main.system plugin;
	
	/*Credits*/
	
	public static boolean isCreditExists(String creditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.isCreditExists(creditID);
		}else {
			
		}
		return false;
	}
	
	public static ArrayList<String> getAllCreditIds(){
		if(plugin.sql.isConnected()) {
			return plugin.sql.getAllCreditIds();
		}else {
			
		}
		return null;
	}
	
	public static String getCreditName(String creditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getCreditName(creditID);
		}else {
			
		}
		return null;
	}
	
	public static double getCreditValue(String creditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getCreditValue(creditID);
		}else {
			
		}
		return 0.00;
	}
	
	public static double getCreditPayTax(String creditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getCreditPayTax(creditID);
		}else {
			
		}
		return 0.00;
	}
	
	public static int getCreditLeaseTime(String creditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getCreditLeaseTime(creditID);
		}else {
			
		}
		return 0;
	}
	
	public static int getCreditNeededRank(String creditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getCreditNeededRank(creditID);
		}else {
			
		}
		return 0;
	}
	
	public static void addCredit(String creditID, String creditName, double creditValue, double payTax, int leaseTime, int neededRank) {
		
	}
	
	public static void addCredit(String creditID, String creditName, double creditValue, double payTax, int leaseTime) {
		addCredit(creditID, creditName, creditValue, payTax, leaseTime, 0);
	}
	
	public static void setCreditName(String creditID, String creditName) {
		if(plugin.sql.isConnected()) {
			plugin.sql.setCreditName(creditID, creditName);
		}else {
			
		}
	}
	
	public static void setCreditValue(String creditID, double creditValue) {
		if(plugin.sql.isConnected()) {
			plugin.sql.setCreditValue(creditID, creditValue);
		}else {
			
		}
	}
	
	public static void setPayTax(String creditID, double payTax) {
		if(plugin.sql.isConnected()) {
			plugin.sql.setPayTax(creditID, payTax);
		}else {
			
		}
	}
	
	public static void setLeaseTime(String creditID, int leaseTime) {
		if(plugin.sql.isConnected()) {
			plugin.sql.setLeaseTime(creditID, leaseTime);
		}else {
			
		}
	}
	
	public static void setNeededRank(String creditID, int neededRank) {
		if(plugin.sql.isConnected()) {
			plugin.sql.setNeededRank(creditID, neededRank);
		}else {
			
		}
	}
	
	public static void removeCredit(String creditID) {
		if(plugin.sql.isConnected()) {
			plugin.sql.removeCredit(creditID);
		}else {
			
		}
	}
	
	/*Player Credit*/
	
	public static boolean isExistsPlayerCredit(OfflinePlayer p, String creditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.isExistsPlayerCredit(p, creditID);
		}else {
			
		}
		return false;
	}
	
	public static ArrayList<String> getPlayerUUID_CreditID_List(OfflinePlayer p) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getPlayerUUID_CreditID_List(p);
		}else {
			
		}
		return null;
	}
	
	public static String getPlayerUUID(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getPlayerUUID(PlayerUUID_CreditID);
		}else {
			
		}
		return null;
	}
	
	public static String getNextPayDate(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getNextPayDate(PlayerUUID_CreditID);
		}else {
			
		}
		return null;
	}
	
	public static String getNextPayTime(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getNextPayTime(PlayerUUID_CreditID);
		}else {
			
		}
		return null;
	}
	
	public static int getDaysLeft(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getDaysLeft(PlayerUUID_CreditID);
		}else {
			
		}
		return 0;
	}
	
	public static double getRemainingCreditValue(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getRemainingCreditValue(PlayerUUID_CreditID);
		}else {
			
		}
		return 0.00;
	}
	
	public static int getDeferralCounter(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getDeferralCounter(PlayerUUID_CreditID);
		}else {
			
		}
		return 0;
	}
	
	public static int getNotPayedDays(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getNotPayedDays(PlayerUUID_CreditID);
		}else {
			
		}
		return 0;
	}
	
	public static double getExtraPays(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getExtraPays(PlayerUUID_CreditID);
		}else {
			
		}
		return 0.00;
	}
	
	public static double getPunishPays(String PlayerUUID_CreditID) {
		if(plugin.sql.isConnected()) {
			return plugin.sql.getPunishPays(PlayerUUID_CreditID);
		}else {
			
		}
		return 0.00;
	}
	
	public static void setNextPayDate(String PlayerUUID_CreditID, String NextPayDate) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updateNextPayDate(PlayerUUID_CreditID, NextPayDate);
		}else {
			
		}
	}
	
	public static void setNextPayTime(String PlayerUUID_CreditID, String NextPayTime) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updateNextPayTime(PlayerUUID_CreditID, NextPayTime);
		}else {
			
		}
	}
	
	public static void setDaysLeft(String PlayerUUID_CreditID, int DaysLeft) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updateDaysLeft(PlayerUUID_CreditID, DaysLeft);
		}else {
			
		}
	}
	
	public static void setRemainingCreditValue(String PlayerUUID_CreditID, double RemainingCreditValue) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updateRemainingCreditValue(PlayerUUID_CreditID, RemainingCreditValue);
		}else {
			
		}
	}
	
	public static void setDeferralCounter(String PlayerUUID_CreditID, int DeferralCounter) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updateDeferralCounter(PlayerUUID_CreditID, DeferralCounter);
		}else {
			
		}
	}
	
	public static void setNotPayedDays(String PlayerUUID_CreditID, int NotPayedDays) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updateNotPayedDays(PlayerUUID_CreditID, NotPayedDays);
		}else {
			
		}
	}
	
	public static void setExtraPays(String PlayerUUID_CreditID, double ExtraPays) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updateExtraPays(PlayerUUID_CreditID, ExtraPays);
		}else {
			
		}
	}
	
	public static void setPunishPay(String PlayerUUID_CreditID, double PunishPay) {
		if(plugin.sql.isConnected()) {
			plugin.sql.updatePunishPay(PlayerUUID_CreditID, PunishPay);
		}else {
			
		}
	}
	
	public static void addNextPayDate(String PlayerUUID_CreditID, int years, int months, int days) {
		if(plugin.sql.isConnected()) {
			String currentDate = plugin.sql.getNextPayDate(PlayerUUID_CreditID);
			String newDate = changeDateDay(currentDate, days, true);
			newDate = changeDateMonth(newDate, months, true);
			newDate = changeDateYear(newDate, years, true);
			plugin.sql.updateNextPayDate(PlayerUUID_CreditID, newDate);
		}else {
			
		}
	}
	
	public static void subtractNextPayDate(String PlayerUUID_CreditID, int years, int months, int days) {
		if(plugin.sql.isConnected()) {
			String currentDate = plugin.sql.getNextPayDate(PlayerUUID_CreditID);
			String newDate = changeDateDay(currentDate, days, false);
			newDate = changeDateMonth(newDate, months, false);
			newDate = changeDateYear(newDate, years, false);
			plugin.sql.updateNextPayDate(PlayerUUID_CreditID, newDate);
		}else {
			
		}
	}
	
	public static void addNextPayTime(String PlayerUUID_CreditID, int hours, int minutes) {
		if(plugin.sql.isConnected()) {
			String currentTime = plugin.sql.getNextPayDate(PlayerUUID_CreditID);
			String newTime = changeDateDay(currentTime, hours, true);
			newTime = changeDateMonth(newTime, minutes, true);
			plugin.sql.updateNextPayDate(PlayerUUID_CreditID, newTime);
		}else {
			
		}
	}
	
	public static void subtractNextPayTime(String PlayerUUID_CreditID, int hours, int minutes) {
		if(plugin.sql.isConnected()) {
			String currentTime = plugin.sql.getNextPayDate(PlayerUUID_CreditID);
			String newTime = changeDateDay(currentTime, hours, false);
			newTime = changeDateMonth(newTime, minutes, false);
			plugin.sql.updateNextPayDate(PlayerUUID_CreditID, newTime);
		}else {
			
		}
	}
	
	public static void addDaysLeft(String PlayerUUID_CreditID, int days) {
		if(plugin.sql.isConnected()) {
			int currentDays = plugin.sql.getDaysLeft(PlayerUUID_CreditID);
			plugin.sql.updateDaysLeft(PlayerUUID_CreditID, currentDays+days);
		}else {
			
		}
	}
	
	public static void subtractDaysLeft(String PlayerUUID_CreditID, int days) {
		if(plugin.sql.isConnected()) {
			int currentDays = plugin.sql.getDaysLeft(PlayerUUID_CreditID);
			plugin.sql.updateDaysLeft(PlayerUUID_CreditID, currentDays-days);
		}else {
			
		}
	}
	
	public static void addRemainingCreditValue(String PlayerUUID_CreditID, double value) {
		if(plugin.sql.isConnected()) {
			double currentValue = plugin.sql.getRemainingCreditValue(PlayerUUID_CreditID);
			plugin.sql.updateRemainingCreditValue(PlayerUUID_CreditID, currentValue+value);
		}else {
			
		}
	}
	
	public static void subtractRemainingCreditValue(String PlayerUUID_CreditID, double value) {
		if(plugin.sql.isConnected()) {
			double currentValue = plugin.sql.getRemainingCreditValue(PlayerUUID_CreditID);
			plugin.sql.updateRemainingCreditValue(PlayerUUID_CreditID, currentValue-value);
		}else {
			
		}
	}
	
	public static void addDeferralCounter(String PlayerUUID_CreditID, int deferrals) {
		if(plugin.sql.isConnected()) {
			int currentDeferrals = plugin.sql.getDeferralCounter(PlayerUUID_CreditID);
			plugin.sql.updateDeferralCounter(PlayerUUID_CreditID, currentDeferrals+deferrals);
		}else {
			
		}
	}
	
	public static void subtractDeferralCounter(String PlayerUUID_CreditID, int deferrals) {
		if(plugin.sql.isConnected()) {
			int currentDeferrals = plugin.sql.getDeferralCounter(PlayerUUID_CreditID);
			plugin.sql.updateDeferralCounter(PlayerUUID_CreditID, currentDeferrals-deferrals);
		}else {
			
		}
	}
	
	public static void addNotPayedDays(String PlayerUUID_CreditID, int days) {
		if(plugin.sql.isConnected()) {
			int currentNotPayedDays = plugin.sql.getNotPayedDays(PlayerUUID_CreditID);
			plugin.sql.updateNotPayedDays(PlayerUUID_CreditID, currentNotPayedDays+days);
		}else {
			
		}
	}
	
	public static void subtractNotPayedDays(String PlayerUUID_CreditID, int days) {
		if(plugin.sql.isConnected()) {
			int currentNotPayedDays = plugin.sql.getNotPayedDays(PlayerUUID_CreditID);
			plugin.sql.updateNotPayedDays(PlayerUUID_CreditID, currentNotPayedDays-days);
		}else {
			
		}
	}
	
	public static void addExtraPays(String PlayerUUID_CreditID, double value) {
		if(plugin.sql.isConnected()) {
			double currentExtraPays = plugin.sql.getExtraPays(PlayerUUID_CreditID);
			plugin.sql.updateExtraPays(PlayerUUID_CreditID, currentExtraPays+value);
		}else {
			
		}
	}
	
	public static void subtractExtraPays(String PlayerUUID_CreditID, double value) {
		if(plugin.sql.isConnected()) {
			double currentExtraPays = plugin.sql.getExtraPays(PlayerUUID_CreditID);
			plugin.sql.updateExtraPays(PlayerUUID_CreditID, currentExtraPays-value);
		}else {
			
		}
	}
	
	public static void addPunishPay(String PlayerUUID_CreditID, double value) {
		if(plugin.sql.isConnected()) {
			double currentPunishPay = plugin.sql.getPunishPays(PlayerUUID_CreditID);
			plugin.sql.updatePunishPay(PlayerUUID_CreditID, currentPunishPay+value);
		}else {
			
		}
	}
	
	public static void subtractPunishPay(String PlayerUUID_CreditID, double value) {
		if(plugin.sql.isConnected()) {
			double currentPunishPay = plugin.sql.getPunishPays(PlayerUUID_CreditID);
			plugin.sql.updatePunishPay(PlayerUUID_CreditID, currentPunishPay-value);
		}else {
			
		}
	}
	
	public static void createPlayerCredit(OfflinePlayer p, String CreditID, String NextPayDate, String NextPayTime, int DaysLeft, double RemainingCreditValue, int DeferralCounter, int NotPayedDays, double ExtraPays, double PunishPay) {
		if(plugin.sql.isConnected()) {
			plugin.sql.createPlayerCredit(p, CreditID, NextPayDate, NextPayTime, DaysLeft, RemainingCreditValue, DeferralCounter, NotPayedDays, ExtraPays, PunishPay);
		}else {
			
		}
	}
	
	public static void createPlayerCredit(OfflinePlayer p, String CreditID) {
		if(plugin.sql.isConnected()) {
			plugin.sql.createPlayerCredit(p, CreditID);
		}else {
			
		}
	}
	
	public static void removePlayerCredit(OfflinePlayer p, String CreditID) {
		if(plugin.sql.isConnected()){
			plugin.sql.removePlayerCredit(builderPlayerUUID_CreditID(p, CreditID));
		}else {
			
		}
	}
	
	/*Functions*/
	
	public static String changeDateDay(String date, int days, boolean add) {
		int pos1 = 0;
		int pos2 = 0;
		int counter = 0;
		for(int i = 0; i<date.length(); i++) {
			if(date.charAt(i) == '.') {
				counter++;
				if(counter == 1) {
					pos1 = i;
				}else if(counter  == 2) {
					pos2 = i;
				}
			}
		}
		int day = Integer.valueOf(date.substring(0, pos1));
		int month = Integer.valueOf(date.substring(pos1+1, pos2));
		int year = Integer.valueOf(date.substring(pos2+1, date.length()));
		if(add) {
			day+=days;
		}else {
			day-=days;
		}
		date = String.valueOf(day+"."+month+"."+year);
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date = sd.format(d);
		return date;
	}
	
	public static String changeDateMonth(String date, int months, boolean add) {
		int pos1 = 0;
		int pos2 = 0;
		int counter = 0;
		for(int i = 0; i<date.length(); i++) {
			if(date.charAt(i) == '.') {
				counter++;
				if(counter == 1) {
					pos1 = i;
				}else if(counter  == 2) {
					pos2 = i;
				}
			}
		}
		int day = Integer.valueOf(date.substring(0, pos1));
		int month = Integer.valueOf(date.substring(pos1+1, pos2));
		int year = Integer.valueOf(date.substring(pos2+1, date.length()));
		if(add) {
			month+=months;
		}else {
			month-=months;
		}
		date = String.valueOf(day+"."+month+"."+year);
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date = sd.format(d);
		return date;
	}
	
	public static String changeDateYear(String date, int years, boolean add) {
		int pos1 = 0;
		int pos2 = 0;
		int counter = 0;
		for(int i = 0; i<date.length(); i++) {
			if(date.charAt(i) == '.') {
				counter++;
				if(counter == 1) {
					pos1 = i;
				}else if(counter  == 2) {
					pos2 = i;
				}
			}
		}
		int day = Integer.valueOf(date.substring(0, pos1));
		int month = Integer.valueOf(date.substring(pos1+1, pos2));
		int year = Integer.valueOf(date.substring(pos2+1, date.length()));
		if(add) {
			year+=years;
		}else {
			year-=years;
		}
		date = String.valueOf(day+"."+month+"."+year);
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date = sd.format(d);
		return date;
	}
	
	public static String changeTimeHour(String time, int hours, boolean add) {
		/*int pos1 = 0;
		int counter = 0;
		for(int i = 0; i<time.length(); i++) {
			if(time.charAt(i) == ':') {
				counter++;
				if(counter == 1) {
					pos1 = i;
				}
			}
		}
		int hour = Integer.valueOf(time.substring(0, pos1));
		int minute = Integer.valueOf(time.substring(pos1+1, time.length()));
		if(add) {
			hour+=hours;
		}else {
			hour-=hours;
		}
		time = String.valueOf(hour+":"+minute);
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		Date d = null;
		try {
			d = sd.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(time);
		time = sd.format(time);
		System.out.println(time);*/
		return time;
	}
	
	public static String changeTimeMinute(String PlayerUUID_CreditID, String time, int minutes, boolean add) {
		/*int pos1 = 0;
		int counter = 0;
		for(int i = 0; i<time.length(); i++) {
			if(time.charAt(i) == ':') {
				counter++;
				if(counter == 1) {
					pos1 = i;
				}
			}
		}
		int hour = Integer.valueOf(time.substring(0, pos1));
		int minute = Integer.valueOf(time.substring(pos1+1, time.length()));
		if(add) {
			minute+=minutes;
		}else {
			minute-=minutes;
		}
		time = String.valueOf(hour+":"+minute);
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		Date d = null;
		try {
			d = sd.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(time);
		time = sd.format(time);
		System.out.println(time);
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		Date d = null;
		try {
			d = sd.parse(getNextPayDate(PlayerUUID_CreditID));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d
		d.setHours(hours);*/
		return time;
	}
	
	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
		return sd.format(date).toString();
	}
	
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		return sd.format(date).toString();
	}
	
	public static String readCreditIDFromPlayerUUID_CreditID(String PlayerUUID_CreditID) {
		int pos1 = 0;
		int counter = 0;
		for(int i = 0; i<PlayerUUID_CreditID.length(); i++) {
			if(PlayerUUID_CreditID.charAt(i) == '_') {
				counter++;
				if(counter == 1) {
					pos1 = i;
				}
			}
		}
		String CreditID = PlayerUUID_CreditID.substring(pos1+1);
		return CreditID;
	}
	
	public static String builderPlayerUUID_CreditID(OfflinePlayer p, String CreditID) {
		return p.getUniqueId().toString()+"_"+CreditID.toString();
	}
	
	public static double getPayOffTaxValue(OfflinePlayer p, String CreditID, boolean punishPay) {
		if(plugin.sql.isConnected()) {
			if(!punishPay) {
				if(getDaysLeft(builderPlayerUUID_CreditID(p, CreditID)) > 1) {
					double creditValue = getCreditValue(CreditID);
					double leaseTime = getCreditLeaseTime(CreditID);
					double remaining = getRemainingCreditValue(builderPlayerUUID_CreditID(p, CreditID));
					double nominalzinssatz = getCreditPayTax(CreditID) / 100;
					
					double tilgung = creditValue / leaseTime;
					double zinsen = remaining / leaseTime * nominalzinssatz;
					
					double value = tilgung+zinsen;
					value = Math.round(value*100.0)/100.0;
					return value;
				}else {
					double value = getRemainingCreditValue(builderPlayerUUID_CreditID(p, CreditID));
					return value;
				}
			}else {
				if(getNotPayedDays(builderPlayerUUID_CreditID(p, CreditID)) > 1) {
					double creditValue = getCreditValue(CreditID);
					double leaseTime = getNotPayedDays(builderPlayerUUID_CreditID(p, CreditID));
					double remaining = getPunishPays(builderPlayerUUID_CreditID(p, CreditID));
					double nominalzinssatz = getCreditPayTax(CreditID) / 100.0;
					
					double tilgung = creditValue / leaseTime;
					double zinsen = remaining / leaseTime * nominalzinssatz;
					
					double value = tilgung+zinsen;
					value = Math.round(value*100.0)/100.0;
					System.out.println(value);
					return value;
				}else {
					double value = getPunishPays(builderPlayerUUID_CreditID(p, CreditID));
					return value;
				}
			}
		}else {
			
		}
		return 0.00;
	}
	
	public static double generatePunishPay(OfflinePlayer p, String CreditID) {
		if(plugin.sql.isConnected()) {
			int notPayedDays = getNotPayedDays(builderPlayerUUID_CreditID(p, CreditID));
			double notPayedDaysTax = notPayedDays / 100.0 + 1.0;
			double tax = getCreditPayTax(CreditID) / 100 + 1;
			double creditPayOff = 0.0;
			if(getRemainingCreditValue(builderPlayerUUID_CreditID(p, CreditID)) != 0) {
				creditPayOff = getPayOffTaxValue(p, CreditID, false);
			}else {
				creditPayOff = getPayOffTaxValue(p, CreditID, true);
			}
			
			double value = creditPayOff*tax*notPayedDaysTax;
			return value;
			
		}else {
			
		}
		return 0.00;
	}
	
	public static String addZerosToDouble(double value) {
		String valueZero = String.valueOf(value);
		valueZero += "00";
		for(int x = 0; x<valueZero.length(); x++) {
			if(valueZero.charAt(x) == '.') {
				String tmp = valueZero.substring(x+1);
				if(tmp.length() > 2) {
					tmp = tmp.substring(0, 2);
				}
				valueZero = valueZero.substring(0, x+1);
				valueZero += tmp;
				return valueZero;
			}
		}
		return null;
	}
	
	public static boolean lastSeenOverTime(OfflinePlayer p, int days) {
		long lastSeen = plugin.timeRankAPI.sql.getPlayerLastSeen(p);
		long lDays = days * 24 * 60 * 60 * 1000;
		long current = System.currentTimeMillis();
		return current >= lastSeen+lDays;
	}
	
	public static double getTotalCreditValue(OfflinePlayer p, String CreditID) {
		return getRemainingCreditValue(builderPlayerUUID_CreditID(p, CreditID))+getPunishPays(builderPlayerUUID_CreditID(p, CreditID));
	}
}
