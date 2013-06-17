/*
 * Created on 13-6-8
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
package chapter12_exception;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-6-8
 */
public class FinallyDemo {
    public static boolean yeah() {
        boolean result;
        try {
            result = false;
            System.out.println("before return:" + result);
            return result;
        } catch (Exception e) {
            System.out.println("Exception");
            result = false;
            return result;
        } finally {
            result = true;
            System.out.println("finally result:" + result);
        }
    }

    public static boolean yeah2(Data data) {
        boolean result;
        try {
            data.result = false;
            result = data.result;
            System.out.println("before return:" + result);
            return result;
        } catch (Exception e) {
            System.out.println("Exception");
            data.result = false;
            result = data.result;
            return result;
        } finally {
            data.result = true;
            result = data.result;
            System.out.println("finally result:" + result);
        }
    }

    private static class Data {
        boolean result;
    }

    public static void main(String[] args) {
        System.out.println(yeah());
        Data data = new Data();
        System.out.println(yeah2(data));

        System.out.println("last..." + data.result);
    }
}
