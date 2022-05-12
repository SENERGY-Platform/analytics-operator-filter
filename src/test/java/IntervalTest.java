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


public class IntervalTest {


    public void test(String configuredValue, Object actualValue, boolean expectedToTrigger) throws Exception {
        Helper.setEnv("FILTER_TYPE", "INTERVAL");
        Helper.test(configuredValue, actualValue, expectedToTrigger);
    }

    @Test
    public void testOpenInt() throws Exception {
        test("(0, 8)", -1, false);
        test("(0, 8)", 0, false);
        test("(0, 8)", 1, true);
        test("(0, 8)", 2, true);
        test("(0, 8)", 3, true);
        test("(0, 8)", 4, true);
        test("(0, 8)", 5, true);
        test("(0, 8)", 6, true);
        test("(0, 8)", 7, true);
        test("(0, 8)", 8, false);
        test("(0, 8)", 9, false);
    }

    @Test
    public void testOpen2Int() throws Exception {
        test("]0, 8[", -1, false);
        test("]0, 8[", 0, false);
        test("]0, 8[", 1, true);
        test("]0, 8[", 2, true);
        test("]0, 8[", 3, true);
        test("]0, 8[", 4, true);
        test("]0, 8[", 5, true);
        test("]0, 8[", 6, true);
        test("]0, 8[", 7, true);
        test("]0, 8[", 8, false);
        test("]0, 8[", 9, false);
    }

    @Test
    public void testClosedInt() throws Exception {
        test("[0, 8]", -1, false);
        test("[0, 8]", 0, true);
        test("[0, 8]", 1, true);
        test("[0, 8]", 2, true);
        test("[0, 8]", 3, true);
        test("[0, 8]", 4, true);
        test("[0, 8]", 5, true);
        test("[0, 8]", 6, true);
        test("[0, 8]", 7, true);
        test("[0, 8]", 8, true);
        test("[0, 8]", 9, false);
    }

    @Test
    public void testClosedFloat() throws Exception {
        test("[1.3, 2.4]", 0, false);
        test("[1.3, 2.4]", 1, false);
        test("[1.3, 2.4]", 1.2, false);
        test("[1.3, 2.4]", 1.3, true);
        test("[1.3, 2.4]", 1.4, true);
        test("[1.3, 2.4]", 2, true);
        test("[1.3, 2.4]", 2.4, true);
        test("[1.3, 2.4]", 2.5, false);
        test("[1.3, 2.4]", 3, false);
        test("[1.3, 2.4]", 4, false);
        test("[1.3, 2.4]", 5, false);
    }

    @Test
    public void testOpenFloat() throws Exception {
        test("(1.3, 2.4)", 0, false);
        test("(1.3, 2.4)", 1, false);
        test("(1.3, 2.4)", 1.2, false);
        test("(1.3, 2.4)", 1.3, false);
        test("(1.3, 2.4)", 1.4, true);
        test("(1.3, 2.4)", 2, true);
        test("(1.3, 2.4)", 2.4, false);
        test("(1.3, 2.4)", 2.5, false);
        test("(1.3, 2.4)", 3, false);
        test("(1.3, 2.4)", 4, false);
        test("(1.3, 2.4)", 5, false);
    }

    @Test
    public void testOpen2Float() throws Exception {
        test("]1.3, 2.4[", 0, false);
        test("]1.3, 2.4[", 1, false);
        test("]1.3, 2.4[", 1.2, false);
        test("]1.3, 2.4[", 1.3, false);
        test("]1.3, 2.4[", 1.4, true);
        test("]1.3, 2.4[", 2, true);
        test("]1.3, 2.4[", 2.4, false);
        test("]1.3, 2.4[", 2.5, false);
        test("]1.3, 2.4[", 3, false);
        test("]1.3, 2.4[", 4, false);
        test("]1.3, 2.4[", 5, false);
    }

    @Test
    public void testOpenInfinity() throws Exception {
        test("(*, *)", 0, true);
        test("(*, *)", 1, true);
        test("(*, *)", 1.2, true);
        test("(*, *)", 1.3, true);
        test("(*, *)", 1.4, true);
        test("(*, *)", 2, true);
        test("(*, *)", 2.4, true);
        test("(*, *)", 2.5, true);
        test("(*, *)", 3, true);
        test("(*, *)", 4, true);
        test("(*, *)", 5, true);
    }

    @Test
    public void testOpen2Infinity() throws Exception {
        test("]*, *[", 0, true);
        test("]*, *[", 1, true);
        test("]*, *[", 1.2, true);
        test("]*, *[", 1.3, true);
        test("]*, *[", 1.4, true);
        test("]*, *[", 2, true);
        test("]*, *[", 2.4, true);
        test("]*, *[", 2.5, true);
        test("]*, *[", 3, true);
        test("]*, *[", 4, true);
        test("]*, *[", 5, true);
    }

    @Test
    public void testClosedInfinity() throws Exception {
        test("[*, *]", 0, true);
        test("[*, *]", 1, true);
        test("[*, *]", 1.2, true);
        test("[*, *]", 1.3, true);
        test("[*, *]", 1.4, true);
        test("[*, *]", 2, true);
        test("[*, *]", 2.4, true);
        test("[*, *]", 2.5, true);
        test("[*, *]", 3, true);
        test("[*, *]", 4, true);
        test("[*, *]", 5, true);
    }

    @Test
    public void testString() throws Exception {
        test("[*, *]", "0", true);
        test("[*, *]", "1", true);
        test("[*, *]", "1.2", true);
        test("[*, *]", "1.3", true);
        test("[*, *]", "1.4", true);
        test("[*, *]", "2", true);
        test("[*, *]", "2.4", true);
        test("[*, *]", "2.5", true);
        test("[*, *]", "3", true);
        test("[*, *]", "4", true);
        test("[*, *]", "5", true);
        test("[*, *]", "foo", false);
    }
}
