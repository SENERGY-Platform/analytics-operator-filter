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


import filters.FilterInterface;
import org.infai.ses.senergy.models.DeviceMessageModel;
import org.infai.ses.senergy.models.MessageModel;
import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Message;
import org.infai.ses.senergy.testing.utils.JSONHelper;
import org.infai.ses.senergy.utils.ConfigProvider;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    protected static void setEnv(String key, String value) throws Exception {
        Map<String, String> newenv = new HashMap<>();
        newenv.put(key, value);
        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        } catch (NoSuchFieldException e) {
            Class[] classes = Collections.class.getDeclaredClasses();
            Map<String, String> env = System.getenv();
            for (Class cl : classes) {
                if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                    Field field = cl.getDeclaredField("m");
                    field.setAccessible(true);
                    Object obj = field.get(env);
                    Map<String, String> map = (Map<String, String>) obj;
                    map.clear();
                    map.putAll(newenv);
                }
            }
        }
    }

    protected static MessageModel test(String configuredValue, Object actualValue, boolean expectedToTrigger) throws Exception {
        Map<String, Object> conf = new JSONHelper().parseFile("config.json");
        JSONObject filterConfig = new JSONObject();
        filterConfig.put("filter", configuredValue);
        conf.put("config", filterConfig);
        Config config = new Config(((JSONObject) conf).toJSONString());
        ConfigProvider.setConfig(config);

        Filter op = new Filter(FilterInterface.create(config), config);
        MessageModel model = new MessageModel();
        Message message = new Message();
        op.configMessage(message);
        JSONObject m = new JSONHelper().parseFile("message.json");
        ((JSONObject) ((JSONObject) m.get("value")).get("reading")).put("value", actualValue);
        DeviceMessageModel deviceMessageModel = JSONHelper.getObjectFromJSONString(m.toString(), DeviceMessageModel.class);
        assert deviceMessageModel != null;
        String topicName = config.getInputTopicsConfigs().get(0).getName();
        model.putMessage(topicName, org.infai.ses.senergy.operators.Helper.deviceToInputMessageModel(deviceMessageModel, topicName));
        message.setMessage(model);
        op.run(message);
        Assert.assertEquals(model.getOutputMessage().getAnalytics().containsKey("value"), expectedToTrigger);
        return model;
    }
}
