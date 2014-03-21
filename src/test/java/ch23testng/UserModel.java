/*
 * Created on 13-3-4
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
package ch23testng;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-4
 */
public class UserModel {
    private int number;
    private String msg;

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }
}
