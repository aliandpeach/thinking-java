package ch20annotation.display;

import java.util.List;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-24
 */
public class Dea {
    @Display(name = "唯一ID")
    public String uuid;
    @Display(name = "监控名称")
    public String name;
    @Display(name = "漂不漂亮")
    public boolean beautiful;
    @Display(name = "核心数", threshold = 2)
    public int core;
    @Display(name = "CPU使用率", threshold = 0.7)
    public double cpu;
    public Config config;
    @Display(name = "URIS")
    public List<String> uris;
    public static class Config {
        @Display(name = "请求名称")
        public String request_name;
        public Long years;
        @Display(name = "价格")
        public double price;
        public Logging logging;
    }
    public static class Logging {
        @Display(name = "日志级别")
        public String level;
        public String filename;
    }
}
