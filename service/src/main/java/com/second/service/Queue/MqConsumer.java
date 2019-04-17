package com.second.service.Queue;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqConsumer {
    private Logger log = LoggerFactory.getLogger(MqConsumer.class);

    @RabbitListener(queues = "testMq")
    public void handler(Message message, Channel channel) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("接收消息：｛｝", message.getBody().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
