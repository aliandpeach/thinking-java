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

import java.util.*;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-4
 */
public class TestNGTest1 {
    private Collection<String> collection;

    @BeforeClass
    public void oneTimeSetUp() {
        // one-time initialization code
        System.out.println("@BeforeClass - oneTimeSetUp");
    }

    @AfterClass
    public void oneTimeTearDown() {
        // one-time cleanup code
        System.out.println("@AfterClass - oneTimeTearDown");
    }

    @BeforeMethod
    public void setUp() {
        collection = new ArrayList<String>();
        System.out.println("@BeforeMethod - setUp");
    }

    @AfterMethod
    public void tearDown() {
        collection.clear();
        System.out.println("@AfterMethod - tearDown");
    }

    @Test
    public void testEmptyCollection() {
        Assert.assertEquals(collection.isEmpty(), true);
        System.out.println("@Test - testEmptyCollection");
    }

    @Test
    public void testOneItemCollection() {
        collection.add("itemA");
        Assert.assertEquals(collection.size(), 1);
        System.out.println("@Test - testOneItemCollection");
    }
}
