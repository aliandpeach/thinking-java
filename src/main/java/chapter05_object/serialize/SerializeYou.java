/*
 * Created on 12-11-29
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
package chapter05_object.serialize;

import java.io.Serializable;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-11-29
 */
class SerializeYou implements Serializable {
    private static final long serialVersionUID = 3L;

    private int data;
    private int data3;

    public SerializeYou(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public int getData3() {
        return data3;
    }
}