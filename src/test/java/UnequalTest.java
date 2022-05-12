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


import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;


public class UnequalTest {


    public void test(String configuredValue, Object actualValue, boolean expectedToTrigger) throws Exception {
        Helper.setEnv("FILTER_TYPE", "UNEQUAL");
        Helper.test(configuredValue, actualValue, expectedToTrigger);
    }

    @Test
    public void stringUnequalFalse() throws Exception {
        test("\"foobar\"", "foobar", false);
    }

    @Test
    public void stringUnequalTrue() throws Exception {
        test("\"foobar\"", "foo", true);
    }

    @Test
    public void numberUnequalFalse() throws Exception {
        test("42", 42, false);
    }

    @Test
    public void floatUnequalFalse() throws Exception {
        test("4.2", 4.2, false);
    }

    @Test
    public void floatUnequalFalse2() throws Exception {
        test("42.0", 42.0, false);
    }

    @Test
    public void floatUnequalFalse3() throws Exception {
        test("42", 42.0, false);
    }


    @Test
    public void floatUnequalTrue() throws Exception {
        test("4.2", 13, true);
    }

    @Test
    public void numberUnequalTrue() throws Exception {
        test("42", 13, true);
    }

    @Test
    @Ignore("this test can not be successful; operator will interpret number as string (\"42\"); test-helper compares with original")
    public void stringNumber() throws Exception {
        test("\"foobar\"", 42, true);
    }

    @Test
    public void numberString() throws Exception {
        test("42", "foo", true);
    }
}
