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
package chapter09_samples.regex;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-29
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    public UsernameValidator() {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }

    /**
     * Validate username with regular expression
     *
     * @param username username for validation
     * @return true valid username, false invalid username
     */
    public boolean validate(final String username) {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
