//package com.xzg.order.test;
//
//import com.xzg.library.config.infrastructure.model.Money;
//import com.xzg.orchestrator.kit.command.Command;
//import com.xzg.orchestrator.kit.command.CommandWithDestination;
//import com.xzg.orchestrator.kit.orchestration.CommandWithDestinationAndType;
//import com.xzg.orchestrator.kit.orchestration.saga.SagaCommandProducer;
//import com.xzg.order.sagas.createorder.CreateOrderSaga;
//import jakarta.annotation.Resource;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Collections;
//
///**
// * @projectName: saga-architecture
// * @package: com.xzg.order.test
// * @className: TestOrder
// * @author: xzg
// * @description: TODO
// * @date: 19/11/2023-下午 8:36
// * @version: 1.0
// */
//@SpringBootTest
//public class TestOrder {
//    @Resource
//    private SagaCommandProducer sagaCommandProducer;
//    @Resource
//    private SagaMessagingTestHelper sagaMessagingTestHelper;
//    @Test
//    public void shouldSuccessfullyCreateTicket() {
//        String sagaType = CreateOrderSaga.class.getName();
//
//        long orderId = 1L;
//        Long customerId = 1511300065921L;
//        Money orderTotal = new Money("61.70");
//
//        CustomerCreditReserved reply = sagaMessagingTestHelper.sendAndReceiveCommand(customerServiceProxy.reserveCredit(orderId, customerId, orderTotal), CustomerCreditReserved.class, sagaType);
//
//        assertNotNull(reply);
//
//    }
//    public <C extends Command, R> R sendAndReceiveCommand(CommandWithDestination commandWithDestination, Class<R> replyClass, String sagaType) {
//        // TODO verify that replyClass is allowed
//
//        String sagaId = idGenerator.genIdAsString();
//
//        String replyTo = sagaType + "-reply";
//        sagaCommandProducer.sendCommands(sagaType, sagaId, Collections.singletonList(CommandWithDestinationAndType.command(commandWithDestination)), replyTo);
//
//        ContractVerifierMessage response = contractVerifierMessaging.receive(replyTo);
//
//        if (response == null)
//            return null;
//        String payload = (String) response.getPayload();
//        return (R) JSonMapper.fromJson(payload, replyClass);
//    }
//}
//
//
//