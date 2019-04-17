package com.second.service.Configuration;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;


@Configuration
public class RabbitMqConfiguation {
    private Logger log = LoggerFactory.getLogger(RabbitMqConfiguation.class);
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public Queue queue() {
        return new Queue("testMq", true); //持久化队列（默认值也是true）
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("testMq", true, false);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("testMq");
    }

    /**
     * i->replyCode
     * s->replyText
     * s1->exchange
     * s2->routingKey
     **/
    //消息从交换器发送到队列失败时触发
    RabbitTemplate.ReturnCallback msgReturnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int i, String s, String s1, String s2) {

            log.info("消息：{}，错误码：{}，失败原因：{}，交换器：{}，路由key：{}", message.getMessageProperties().getCorrelationId(), i, s, s1, s2);
        }
    };

    //消息发送到交换器时触发
    RabbitTemplate.ConfirmCallback msgConfirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(@Nullable CorrelationData correlationData, boolean b, @Nullable String s) {
            if (b) {
                log.info("消息{}发送exchange成功", correlationData.getId());
            } else {
                log.info("消息发送到exchange失败，原因：{}", s);
            }
        }
    };

    /***
     * 消费者确认（方式二）
     * **/
    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("testMq");
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(10);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    log.info("接收消息：{}", new String(message.getBody()));
                } catch (Exception ex) {

                    //channel.basicReject
                    //channel.basicNack

                }

            }
        });

        return container;
    }


    /**
     * 生产者的回调都在这里
     **/
    @Autowired
    public RabbitTemplate rabbitTemplate() {
        //消息发送失败后返回到队列中
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setReturnCallback(msgReturnCallback);
        rabbitTemplate.setConfirmCallback(msgConfirmCallback);

        return rabbitTemplate;
    }
}
