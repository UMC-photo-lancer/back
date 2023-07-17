package shop.photolancer.photolancer.service.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.PublishMessage;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, PublishMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}