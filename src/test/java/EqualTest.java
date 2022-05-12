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


import org.junit.Test;

public class EqualTest {


    public void test(String configuredValue, Object actualValue, boolean expectedToTrigger) throws Exception {
        Helper.setEnv("FILTER_TYPE", "EQUAL");
        Helper.test(configuredValue, actualValue, expectedToTrigger);
    }

    @Test
    public void stringEqualTrue() throws Exception {
        test("\"foobar\"", "foobar",true);
    }

    @Test
    public void stringEqualFalse() throws Exception {
        test("\"foobar\"", "foo",false);
    }

    @Test
    public void numberEqualTrue() throws Exception {
        test("42", 42,true);
    }

    @Test
    public void floatEqualTrue() throws Exception {
        test("4.2", 4.2, true);
    }

    @Test
    public void floatEqualTrue2() throws Exception {
        test("42.0", 42.0, true);
    }

    @Test
    public void floatEqualTrue3() throws Exception {
        test("42", 42.0, true);
    }


    @Test
    public void floatEqualFalse() throws Exception {
        test("4.2", 13, false);
    }

    @Test
    public void numberEqualFalse() throws Exception {
        test("42", 13, false);
    }

    @Test
    public void stringNumber() throws Exception {
        test("\"foobar\"", 42, false);
    }

    @Test
    public void numberString() throws Exception {
        test("42", "foo", false);
    }
}
