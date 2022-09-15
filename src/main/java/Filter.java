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
import org.infai.ses.senergy.models.InputTopicModel;
import org.infai.ses.senergy.models.MappingModel;
import org.infai.ses.senergy.operators.BaseOperator;
import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Message;

import java.util.HashMap;
import java.util.Map;

public class Filter extends BaseOperator {

    final FilterInterface filter;
    final String[] inputs;

    Filter(FilterInterface filter, Config config) {
        this.filter = filter;
        Map<String, Boolean> inputs = new HashMap<>();
        for (InputTopicModel inputTopicModel : config.getInputTopicsConfigs()) {
            for (MappingModel mapping : inputTopicModel.getMappings()) {
                inputs.put(mapping.getDest(), false);
            }
        }
        this.inputs = inputs.keySet().toArray(new String[0]);
    }

    public void run(Message message) {
        try {
            Object value = message.getFlexInput("value").getValue(Object.class);
            if (filter.triggers(value)) {
                for (String input : inputs) {
                    message.output(input, message.getFlexInput(input).getValue(Object.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Message configMessage(Message message) {
        for (String input : inputs) {
            message.addFlexInput(input);
        }
        return message;
    }
}
