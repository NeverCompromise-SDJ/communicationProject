package utilityTool;

/**
 * @author SongDongJie
 * @create 2023/7/11 - 9:04
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类，提供通用方法来完成一些经常出现的操作
 */
public class UtilityTool {
    public static String getLocalTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
