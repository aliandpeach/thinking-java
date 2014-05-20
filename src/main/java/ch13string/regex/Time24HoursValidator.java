/*
 * Created on 13-3-30
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
package ch13string.regex;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-30
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time24HoursValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public Time24HoursValidator() {
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }

    /**
     * Validate time in 24 hours format with regular expression
     *
     * @param time time address for validation
     * @return true valid time fromat, false invalid time format
     */
    public boolean validate(final String time) {

        matcher = pattern.matcher(time);
        return matcher.matches();

    }
}
