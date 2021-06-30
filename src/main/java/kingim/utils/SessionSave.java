package kingim.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dameizi
 * @description 全局SESSION
 * @dateTime 2019-05-16 19:19
 * @className kingim.utils.SessionSave
 */
public class SessionSave {

    private static Map<String, String> SessionIdSave = new HashMap<String,String>();

    public static Map<String, String> getSessionIdSave() {
        return SessionIdSave;
    }

    public static void setSessionIdSave(Map<String, String> sessionIdSave) {
        SessionIdSave = sessionIdSave;
    }

}
