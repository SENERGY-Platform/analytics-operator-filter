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

import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Helper;
import org.json.JSONException;

import java.text.ParseException;

public interface FilterInterface {
    boolean triggers(Object actual);

    static FilterInterface create(Config config) throws IllegalArgumentException, JSONException, ParseException {
        String filter = config.getConfigValue("filter", "");
        switch (Helper.getEnv("FILTER_TYPE", "")) {
            case "ALL":
                return new All();
            case "EQUAL":
                return new Equal(filter);
            case "INTERVAL":
                return new Interval(filter);
            case "UNEQUAL":
                return new Unequal(filter);
            case "OLDER_THAN":
                return new OlderThan(filter);
            default:
                throw new IllegalArgumentException();
        }
    }
}
