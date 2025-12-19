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
            for (int monthIndex = 0; monthIndex < MONTHS; monthIndex++) {
                String filename = "Data_Files/" + months[monthIndex] + ".txt";

                try {
                    File file = new File(filename);
                    Scanner reader = new Scanner(file);

                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        line = line.trim();

                        if (line.isEmpty()) continue;

                        String[] parts = line.split(",");

                        if (parts.length != 3) continue;

                        try {
                            int dayIndex = Integer.parseInt(parts[0].trim()) - 1;

                            String commodityName = parts[1].trim();

                            int profit = Integer.parseInt(parts[2].trim());

                            int commIndex = -1;
                            for (int i = 0; i < COMMS; i++) {
                                if (commodities[i].equals(commodityName)) {
                                    commIndex = i;
                                    break;
                                }
                            }
                            if (commIndex != -1 && dayIndex >= 0 && dayIndex < DAYS) {
                                data[monthIndex][dayIndex][commIndex] = profit;
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                }
        }
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
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1 || from < 1 || from > DAYS || to < 1 || to > DAYS || from > to) {
            return -99999;
        }

        int total = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d < to; d++) {
                total += data[m][d][commIndex];
            }
        }
        return total;
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
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        int[] WeekTotals = new int[4];

        for (int week = 0; week < 4; week++) {
            int startDay = week*7;
            int endDay = startDay+7;

            for (int d = startDay; d < endDay; d++) {
                for (int c = 0; c < COMMS; c++) {
                    WeekTotals[week] += data[month][d][c];
                }
            }
        }

        int maxWeek = 0;
        for (int w = 1; w < 4; w++) {
            if (WeekTotals[w] > WeekTotals[maxWeek]) {
                maxWeek = w;
            }
        }
        return "Week " + (maxWeek + 1);
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}