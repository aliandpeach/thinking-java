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
package ch09samples.regex;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-30
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String SIMPLE_PATTERN = "[a-zA-Z0-9\\*\\-/\\s公无]*";

    public SimpleValidator() {
        pattern = Pattern.compile(SIMPLE_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}
