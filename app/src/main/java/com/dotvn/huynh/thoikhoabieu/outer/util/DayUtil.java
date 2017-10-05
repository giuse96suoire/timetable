package com.dotvn.huynh.thoikhoabieu.outer.util;

import com.dotvn.huynh.thoikhoabieu.outer.data.Constant;
import com.dotvn.huynh.thoikhoabieu.outer.data.DayConstant;

/**
 * Created by Manh Hoang Huynh on 16/09/2017.
 */

public class DayUtil {
    public static String getSessionFromTime(int startTime) {
        if (startTime >= DayConstant.MORNING_START_TIME
                && startTime < DayConstant.MORNING_END_TIME) {
            return DayConstant.MORNING;
        }
        if (startTime >= DayConstant.AFTERNOON_START_TIME
                && startTime < DayConstant.AFTERNOON_END_TIME) {
            return DayConstant.AFTERNOON;
        }
        if (startTime >= DayConstant.NIGHT_START_TIME
                && startTime < DayConstant.NIGHT_END_TIME) {
            return DayConstant.NIGHT;
        }
        return DayConstant.MORNING;
    }

    public static int getHoursFromTimeString(String timeString) {
        if (timeString == null || !timeString.contains(Constant.TIME_PREFIX)) {
            return 0;
        }
        int prefixIndex = timeString.indexOf(Constant.TIME_PREFIX);
        return Integer.parseInt(timeString.substring(0, prefixIndex));
    }

    public static int getMinFromTimeString(String timeString) {
        if (timeString == null || !timeString.contains(Constant.TIME_PREFIX)) {
            return 0;
        }
        int prefixIndex = timeString.indexOf(Constant.TIME_PREFIX);
        return Integer.parseInt(timeString.substring(prefixIndex + 1, timeString.length()));
    }

    public static String getTimeStringFromNumberOfMin(int numberOfMin) {
        String hoursString;
        String minString;
        int hours = numberOfMin / 60;
        int min = numberOfMin % 60;
        hoursString = hours < 10 ? "0" + hours : hours + "";
        minString = min < 10 ? "0" + min : min + "";
        return hoursString + Constant.TIME_PREFIX + minString;
    }

    public static String getTimeStringFromHoursAndMin(int hours, int min) {
        String hoursString;
        String minString;
        if (hours >= 0 && hours <= 9) {
            hoursString = "0" + hours;
        } else {
            hoursString = hours + "";
        }
        if (min >= 0 && min <= 9) {
            minString = "0" + min;
        } else {
            minString = min + "";
        }
       return hoursString + Constant.TIME_PREFIX + minString;
    }


}
