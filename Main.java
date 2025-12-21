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
                Scanner reader = new Scanner(new File(filename));

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split(",");
                    if (parts.length == 3) {
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
                }
                reader.close();
            } catch (FileNotFoundException e) {
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        int[] totals = new int[COMMS];

        for (int d = 0; d < DAYS; d++) {
            for (int c = 0; c < COMMS; c++) {
                totals[c] += data[month][d][c];
            }
        }

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
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        int maxDay = 1 ;
        int maxProfit = 0;

        for (int c = 0; c < COMMS; c++) {
            maxProfit += data[month][0][c];
        }

        for (int d = 1; d < DAYS; d++) {
            int dayTotal = 0;
            for (int c = 0; c < COMMS; c++) {
                dayTotal += data[month][d][c];
            }

            if (dayTotal > maxProfit) {
                maxProfit = dayTotal;
                maxDay = d + 1;
            }
        }
        return maxDay;
    }

    public static String bestMonthForCommodity(String comm) {
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }

        if (commIndex == -1) {
            return "INVALID_COMMODITY";
        }

        int maxMonthIndex = 0;
        int maxProfit = 0;

        for (int d = 0; d < DAYS; d++) {
            maxProfit += data[0][d][commIndex];
        }

        for (int m = 1; m < MONTHS; m++) {
            int currentMonthTotal = 0;
            for (int d = 0; d < DAYS; d++) {
                currentMonthTotal += data[m][d][commIndex];
            }

            if (currentMonthTotal > maxProfit) {
                maxProfit = currentMonthTotal;
                maxMonthIndex = m;
            }
        }

        return months[maxMonthIndex];
    }

    public static int consecutiveLossDays(String comm) {
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return -1;
        }
        int maxStreak = 0;
        int currStreak = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (data[m][d][commIndex] < 0) {
                    currStreak++;
                    if (currStreak > maxStreak) {
                        maxStreak = currStreak;
                    }
                } else {
                    currStreak = 0;
                }
            }
        }
        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int commIndex = -1;
        for( int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if(commIndex == -1){
            return -1;
        }

        int count = 0;
        for( int m = 0 ; m < MONTHS ; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (data[m][d][commIndex] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) {
            return -99999;
        }

        int maxSwing = 0;

        for (int d = 0 ; d < DAYS - 1 ; d++) {
            int day1Total = 0;
            int day2Total = 0;

            for (int c = 0; c < COMMS; c++) {
                day1Total += data[month][d][c];
                day2Total += data[month][d + 1][c];
            }

            int swing = Math.abs(day2Total - day1Total);
            if (swing > maxSwing) {
                maxSwing = swing;
            }
        }

        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int c1Index = -1;
        int c2Index = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) c1Index = i;
            if (commodities[i].equals(c2)) c2Index = i;
        }

        if (c1Index == -1 || c2Index == -1) {
            return "INVALID_COMMODITY";
        }

        int c1Total = 0;
        int c2Total = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                c1Total += data[m][d][c1Index];
                c2Total += data[m][d][c2Index];
            }
        }

        int diff = Math.abs(c1Total - c2Total);

        if (c1Total > c2Total) {
            return c1 + " is better by " + diff;
        } else if (c2Total > c1Total) {
            return c2 + " is better by " + diff;
        } else {
            return "Equal";
        }
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
        System.out.println("--- DATA LOADED SUCCESSFULLY ---\n");
    }
}