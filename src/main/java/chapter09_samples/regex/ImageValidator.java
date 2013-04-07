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
package chapter09_samples.regex;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-3-30
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public ImageValidator() {
        pattern = Pattern.compile(IMAGE_PATTERN);
    }

    /**
     * Validate image with regular expression
     *
     * @param image image for validation
     * @return true valid image, false invalid image
     */
    public boolean validate(final String image) {

        matcher = pattern.matcher(image);
        return matcher.matches();

    }
}
