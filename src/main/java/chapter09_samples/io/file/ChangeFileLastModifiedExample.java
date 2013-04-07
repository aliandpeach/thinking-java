/*
 * Created on 13-3-29
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
package chapter09_samples.io.file;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeFileLastModifiedExample {
    public static void main(String[] args) {

        try {

            File file = new File("C:\\logfile.log");

            //print the original last modified date
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            System.out.println("Original Last Modified Date : "
                    + sdf.format(file.lastModified()));

            //set this date
            String newLastModified = "01/31/1998";

            //need convert the above date to milliseconds in long value
            Date newDate = sdf.parse(newLastModified);
            file.setLastModified(newDate.getTime());

            //print the latest last modified date
            System.out.println("Lastest Last Modified Date : "
                    + sdf.format(file.lastModified()));

        } catch (ParseException e) {

            e.printStackTrace();

        }

    }
}
