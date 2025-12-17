// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};

    static int[][][] data = new int[MONTHS][DAYS][COMMS];


    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        int[] totals = new int[COMMS];
        // total profit for every comm per month
        for (int d = 0; d < DAYS; d++) {
            for (int c = 0; c < COMMS; c++) {
                totals[c] += data[month][d][c];
            }
        }
        // highest profit
        int maxIndex = 0;
        for (int c = 1; c < COMMS; c++) {
            if (totals[c] > totals[maxIndex]) {
                maxIndex = c;
            }
        }
        return commodities[maxIndex] + " " + totals[maxIndex];
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;
        }

        int total = 0;
        for (int c = 0; c < COMMS; c++) {
            total += data[month][day - 1][c];
        }

        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) {
        return 1234;
    }

    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    public static int consecutiveLossDays(String comm) {
        return 1234;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}