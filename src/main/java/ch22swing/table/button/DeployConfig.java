/*
 * Created on 13-6-19
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2013 the original author or authors.
 */
package ch22swing.table.button;

/**
 * 发布的配置信息
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-19
 */
public class DeployConfig {
    /**
     * 配置描述
     */
    private String desc;
    /**
     * 本地配置文件根目录
     */
    private String localConfigPath;
    /**
     * 本地工程根目录
     */
    private String localProjectPath;
    /**
     * 服务器IP地址
     */
    private String serverIp;
    /**
     * 服务器用户名
     */
    private String username;
    /**
     * 服务器密码
     */
    private String password;
    /**
     * 服务器配置文件根目录
     */
    private String remoteConfigPath;
    /**
     * 发布的工程类型（grails，maven）
     */
    private String projectType;

    public DeployConfig(String desc, String localConfigPath, String localProjectPath, String serverIp,
                        String username, String password, String remoteConfigPath, String projectType) {
        this.desc = desc;
        this.localConfigPath = localConfigPath;
        this.localProjectPath = localProjectPath;
        this.serverIp = serverIp;
        this.username = username;
        this.password = password;
        this.remoteConfigPath = remoteConfigPath;
        this.projectType = projectType;
    }

    public DeployConfig() {

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocalConfigPath() {
        return localConfigPath;
    }

    public void setLocalConfigPath(String localConfigPath) {
        this.localConfigPath = localConfigPath;
    }

    public String getLocalProjectPath() {
        return localProjectPath;
    }

    public void setLocalProjectPath(String localProjectPath) {
        this.localProjectPath = localProjectPath;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoteConfigPath() {
        return remoteConfigPath;
    }

    public void setRemoteConfigPath(String remoteConfigPath) {
        this.remoteConfigPath = remoteConfigPath;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
