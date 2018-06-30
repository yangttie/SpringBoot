package cn.edu.cwnu.util.resultutil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadDictionary {
    //日志记录
    protected static Logger log = Logger.getLogger(ReadDictionary.class);
    public static Map<String, String> map = new HashMap<String, String>();

    static {
        Properties pro = new Properties();
        try {
            String path = ReadDictionary.class.getResource("/") + "dictionary.properties";
            String p = null;
            if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
                //window环境下
                p = path.replace("file:/", "").replace("%20", " ");
            } else {
                //linux环境下
                p = path.replace("file:", "").replace("%20", " ");
            }
            InputStream in = new BufferedInputStream(new FileInputStream(p));
            pro.load(in);
            Iterator<String> it = pro.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                map.put(key, pro.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("读取dictionary字典失败！", e);
        }
    }
}
