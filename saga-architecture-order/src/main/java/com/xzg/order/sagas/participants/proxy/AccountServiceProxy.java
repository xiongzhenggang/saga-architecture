package com.xzg.order.sagas.participants.proxy;


import com.xzg.library.config.infrastructure.model.Money;
import com.xzg.orchestrator.kit.command.CommandWithDestination;
import com.xzg.orchestrator.kit.command.business.ReserveCreditCommand;
import com.xzg.orchestrator.kit.common.SagaServiceEnum;
import org.springframework.stereotype.Component;

import static com.xzg.orchestrator.kit.command.CommandWithDestinationBuilder.send;

/**
 * @author xiongzhenggang
 * @Date 2023/12/19
 */
@Component
public class AccountServiceProxy {
  /**
   * 发送账户服务校验并扣减余额
   * @param orderId
   * @param customerId
   * @param orderTotal
   * @return
   */
  public CommandWithDestination reserveCredit(long orderId, Long customerId, Money orderTotal) {
  return send(new ReserveCreditCommand(customerId, orderId, orderTotal))
          .to(SagaServiceEnum.ACCOUNT_SERVICE.getType())
          .build();
  }

}
