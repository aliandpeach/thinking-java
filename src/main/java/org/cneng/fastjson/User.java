package org.cneng.fastjson;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/14
 */
public class User {

    public String name;
    public int height;

    @JSONField(name = "com-google-com")
    public void setName(String name) {
        this.name = name;
    }

    @JSONField(format = "yyyy-MM/dd HH:mm:ss")
    public Date birthday;
}
