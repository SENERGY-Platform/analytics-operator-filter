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


import org.infai.ses.senergy.util.DateParser;
import org.joda.time.DateTimeUtils;
import org.junit.Test;



public class OlderThanTest {


    public void test(String configuredValue, Object actualValue, boolean expectedToTrigger) throws Exception {
        Helper.setEnv("FILTER_TYPE", "OLDER_THAN");
        Helper.test(configuredValue, actualValue, expectedToTrigger);
    }

    @Test
    public void oneHourEqual() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(DateParser.parseDateMills("2000-01-01T00:00:00Z"));
        test("1h", "2000-01-01T00:00:00Z", false);
    }

    @Test
    public void oneHourTriggers() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(DateParser.parseDateMills("2000-01-01T01:00:01Z"));
        test("1h", "2000-01-01T00:00:00Z", true);
    }

    @Test
    public void oneHourFuture() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(DateParser.parseDateMills("2000-01-01T00:00:00Z"));
        test("1h", "2000-01-01T01:00:01Z", false);
    }

    @Test
    public void oneSecondsTriggers() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(DateParser.parseDateMills("2000-01-01T00:00:02Z"));
        test("1s", "2000-01-01T00:00:00Z", true);
    }

    @Test
    public void oneMinuteTriggers() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(DateParser.parseDateMills("2000-01-01T00:01:01Z"));
        test("1m", "2000-01-01T00:00:00Z", true);
    }

    @Test
    public void oneDayTriggers() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(DateParser.parseDateMills("2000-01-02T00:00:01Z"));
        test("1d", "2000-01-01T00:00:00Z", true);
    }

    @Test
    public void exactHitDoesNotTrigger() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(DateParser.parseDateMills("2000-01-02T00:00:00Z"));
        test("1d", "2000-01-01T00:00:00Z", false);
    }
}
