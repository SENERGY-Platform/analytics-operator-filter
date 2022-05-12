import org.junit.Test;

public class AllTest {

    public void test(Object actualValue, boolean expectedToTrigger) throws Exception {
        Helper.setEnv("FILTER_TYPE", "ALL");
        Helper.test("", actualValue, expectedToTrigger);
    }
    @Test
    public void string() throws Exception {
        test("foobar",true);
    }


    @Test
    public void integer() throws Exception {
        test(42,true);
    }

    @Test
    public void floatpoint() throws Exception {
        test(4.2, true);
    }
}
