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
package chapter23_testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Vector;

/**
 * TestNG Parameterized Test - Advance
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-4
 */
public class TestNGTest6_2 {
    @Test(dataProvider = "Data-Provider-Function")
    public void parameterIntTest(Class clzz, String[] number) {
        System.out.println("Parameterized Number is : " + number[0]);
        System.out.println("Parameterized Number is : " + number[1]);
    }

    //This function will provide the parameter data
    @DataProvider(name = "Data-Provider-Function")
    public Object[][] parameterIntTestProvider() {
        return new Object[][]{
                {Vector.class, new String[]{"java.util.AbstractList",
                        "java.util.AbstractCollection"}},
                {String.class, new String[]{"1", "2"}},
                {Integer.class, new String[]{"1", "2"}}
        };
    }
}
