package utilityTool;

/**
 * @author SongDongJie
 * @create 2023/7/11 - 9:04
 */

import java.time.LocalDateTime;

/**
 * 工具类，提供通用方法来完成一些经常出现的操作
 */
public class UtilityTool {
    public static String getLocalTime() {
        LocalDateTime sendTime = LocalDateTime.now();
        String sendTimeOfYear = String.valueOf(sendTime.getYear());
        String sendTimeOfMonth = String.valueOf(sendTime.getMonthValue());
        String sendTimeOfDay = String.valueOf(sendTime.getDayOfMonth());
        String sendTimeOfHour = String.valueOf(sendTime.getHour());
        String sendTimeOfMinute = String.valueOf(sendTime.getMinute());
        String sendTimeOfSecond = String.valueOf(sendTime.getSecond());
        return sendTimeOfYear + "-" + sendTimeOfMonth + "-" + sendTimeOfDay + " " + sendTimeOfHour + ":" +
                sendTimeOfMinute + ":" + sendTimeOfSecond;
    }
}
