/*
 * Created on 12-12-5
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
 * Copyright @2012 the original author or authors.
 */
package ch14reflect.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-12-5
 */
public class TargetDomain implements Serializable{

    private static final long serialVersionUID = -6126804875224161611L;
    /** 保险公司id*/
    String insCompanyId;
    /**是否是外挂*/
    Boolean addon;
    boolean addoff;
    SerialObject serialObject;
    public static NotSerialization notSerialization;
    Map<Serializable, Serializable> map;
    Map anotherMap;

    public SerialObject getSerialObject() {
        return serialObject;
    }

    public void setSerialObject(SerialObject serialObject) {
        this.serialObject = serialObject;
    }

    public Map<Serializable, Serializable> getMap() {
        return map;
    }

    public void setMap(Map<Serializable, Serializable> map) {
        this.map = map;
    }

    public Map getAnotherMap() {
        return anotherMap;
    }

    public void setAnotherMap(Map anotherMap) {
        this.anotherMap = anotherMap;
    }

    public String getInsCompanyId() {
        return insCompanyId;
    }

    public void setInsCompanyId(String insCompanyId) {
        this.insCompanyId = insCompanyId;
    }

    public Boolean getAddon() {
        return addon;
    }

    public void setAddon(Boolean addon) {
        this.addon = addon;
    }

    public boolean isAddoff() {
        return addoff;
    }

    public void setAddoff(boolean addoff) {
        this.addoff = addoff;
    }
}
