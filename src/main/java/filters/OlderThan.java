/*
 * Copyright 2022 InfAI (CC SES)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package filters;

import org.infai.ses.senergy.util.DateParser;
import org.joda.time.DateTimeUtils;

import java.time.Duration;
import java.util.regex.Pattern;

public class OlderThan implements FilterInterface {
    private final long durationMillis;


    OlderThan(String filter) {
        Pattern pattern = Pattern.compile("\\d+[hms].*", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(filter).find()) {
            filter = "T" + filter;
        }
        if (!filter.startsWith("P")) {
            filter = "P" + filter;
        }
        durationMillis = Duration.parse(filter).toMillis();
    }

    @Override
    public boolean triggers(Object value) {
        long millis = DateParser.parseDateMills(value.toString());
        long now = DateTimeUtils.currentTimeMillis(); //Needs to use this method for testing
        return now - millis > durationMillis;
    }
}
