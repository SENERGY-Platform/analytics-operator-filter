import org.infai.ses.senergy.models.MessageModel;
import org.junit.Assert;
import org.junit.Test;

public class AdditionalInputTest {

    public MessageModel test(Object actualValue, boolean expectedToTrigger) throws Exception {
        Helper.setEnv("FILTER_TYPE", "ALL");
        return Helper.test("", actualValue, expectedToTrigger);
    }
    @Test
    public void string() throws Exception {
        final MessageModel model = test("foobar",true);
        Assert.assertEquals(1, model.getOutputMessage().getAnalytics().get("anotherValue"));
        Assert.assertEquals("foo", model.getOutputMessage().getAnalytics().get("yetAnotherValue"));
    }


    @Test
    public void integer() throws Exception {
        final MessageModel model = test(42,true);
        Assert.assertEquals(1, model.getOutputMessage().getAnalytics().get("anotherValue"));
        Assert.assertEquals("foo", model.getOutputMessage().getAnalytics().get("yetAnotherValue"));
    }

    @Test
    public void floatpoint() throws Exception {
        final MessageModel model = test(4.2, true);
        Assert.assertEquals(1, model.getOutputMessage().getAnalytics().get("anotherValue"));
        Assert.assertEquals("foo", model.getOutputMessage().getAnalytics().get("yetAnotherValue"));
    }
}
