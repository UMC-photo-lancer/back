package util;

public class LevelCalculator {

    private static final int[] EXPERIENCE_THRESHOLDS = {
            0, 50, 100, 150, 200, 300, 400, 500, 650, 800, 1000, 1200, 1400, 1650, 2000
    };

    public static int getLevel(double experience) {
        for (int i = 1; i < EXPERIENCE_THRESHOLDS.length; i++) {
            if (experience < EXPERIENCE_THRESHOLDS[i]) {
                return i;
            }
        }
        return 15; // If experience is 2000 or above
    }
}