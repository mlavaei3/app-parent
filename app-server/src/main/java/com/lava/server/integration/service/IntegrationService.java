package com.lava.server.integration.service;

import com.lava.server.customer.CustomerAddingEvent;
import com.lava.server.customer.model.entity.Customer;
import com.lava.server.integration.IntegrationExternalAPI;
import com.lava.server.integration.IntegrationInternalAPI;
import com.lava.server.integration.converter.MyConverter;
import com.lava.server.integration.rest.MyCallRest;
import com.lava.server.integration.script.MyScript;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.event.inbound.ApplicationEventListeningMessageProducer;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService implements IntegrationExternalAPI, IntegrationInternalAPI {
    final MyScript.BaseScript baseScript;
    final  MyConverter.BaseConverter baseConverter;
    final  MyCallRest.BaseCallRest baseCallRest;

    public IntegrationService(MyScript.BaseScript baseScript, MyConverter.BaseConverter baseConverter, MyCallRest.BaseCallRest baseCallRest) {
        this.baseScript = baseScript;
        this.baseConverter = baseConverter;
        this.baseCallRest = baseCallRest;
    }

    ///
    // Main Process
    ///
    @Bean
    public ApplicationEventListeningMessageProducer customerEventsAdapter() {
        ApplicationEventListeningMessageProducer producer =
                new ApplicationEventListeningMessageProducer();
        producer.setEventTypes(CustomerAddingEvent.class);
        return producer;
    }

   /* @Bean
    public IntegrationFlow customerEventFlow(ApplicationEventListeningMessageProducer customerEventsAdapter) {
        return IntegrationFlow.from(customerEventsAdapter)
                .handle(p -> {
                    if (((CustomerAddingEvent) p.getPayload()).getCustomerDTO().getName().equalsIgnoreCase("mehdi"))
                        ((CustomerAddingEvent) p.getPayload()).setError("ddd");
                })
                .get();
    }*/

    @Bean
    public IntegrationFlow customerEventClassify(ApplicationEventListeningMessageProducer customerEventsAdapter) {
        return
        IntegrationFlow.from(customerEventsAdapter)
                .publishSubscribeChannel(subscription ->
                        subscription
                                .subscribe(subflow -> subflow
                                        //.filter(source -> source instanceof CustomerAddingEvent)
                                        .filter("(payload.class.name == 'com.lava.server.customer.CustomerAddingEvent') and (payload.customerDTO.name=='mehdi')")
                                        .handle(p->((CustomerAddingEvent) p.getPayload()).setError("ddd")))
                                .subscribe(subflow -> subflow
                                        .filter("payload.customerDTO.name=='mehdi1'")
                                        .handle(p->((CustomerAddingEvent) p.getPayload()).setError("ddd1")))
                                .subscribe(subflow -> subflow
                                        .filter("payload.customerDTO.name=='mehdi2'")
                                        .handle(p->((CustomerAddingEvent) p.getPayload()).setError("ddd2"))))
                .get();
    }

    ///
    // IntegrationExternalAPI
    ///
    @Override
    public String toUpperCase(String term) {
        return baseConverter.toUpperCase(term);

    }

    @Override
    public String callTest() {
        return baseCallRest.callTest(new GenericMessage("")).getPayload();
    }

    @Override
    public String callScript(String script) {
        return baseScript.eval(script);
    }

    ///
    // IntegrationInternalAPI
    ///

}
