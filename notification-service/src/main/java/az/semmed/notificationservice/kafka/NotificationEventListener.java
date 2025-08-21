package az.semmed.notificationservice.kafka;

import az.semmed.kafkasharedclasses.notification.NotificationConstants;
import az.semmed.kafkasharedclasses.order.OrderConstants;
import az.semmed.kafkasharedclasses.order.OrderFinalizedEvent;
import az.semmed.kafkasharedclasses.order.OrderFinalizedStatus;
import az.semmed.notificationservice.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final EmailService emailService;

    @KafkaListener(topics = OrderConstants.ORDER_FINALIZED_TOPIC, groupId = NotificationConstants.GROUP_ID)
    public void listenToOrderFinalizedEvent(OrderFinalizedEvent orderFinalizedEvent) {
        String subject = "";
        String content = "";

        if (orderFinalizedEvent.status().name().equals(OrderFinalizedStatus.CONFIRMED.name())) {
            subject = "Your order has been confirmed";
            content = "Your order with order id %s has been confirmed".formatted(orderFinalizedEvent.orderId());
        } else if (orderFinalizedEvent.status().name().equals(OrderFinalizedStatus.REJECTED.name())) {
            subject = "Your order has been rejected";
            content = "Your order with order id %s has been rejected".formatted(orderFinalizedEvent.orderId());
        }

        emailService.sendEmail(orderFinalizedEvent.customerEmail(), subject, content);

    }

}
