package com.tenyon.web.core.cache;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * RedisManager
 *
 * @author tenyon
 * @date 2024/11/22
 */
@Component
public class RedisCache<V> {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Resource
    private RedisTemplate<String, V> redisTemplate;

    /**
     * 普通缓存放入并设置时间
     *
     * @param key
     * @param value
     * @param expired
     * @return
     */
    public boolean setex(String key, V value, long expired) {
        try {
            // 如果过期时间大于0，则设置过期时间
            if (expired > 0) {
                redisTemplate.opsForValue().set(key, value, expired, TimeUnit.SECONDS);
            } else {
                // 否则，直接设置缓存
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            // 如果设置失败，则记录错误日志
            logger.error("设置redisKey:{},value:{}失败", key, value);
            return false;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key
     * @param value
     */
    public void set(String key, V value) {
        try {
            // 使用redisTemplate的opsForValue方法，将key和value放入redis缓存中
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            // 如果出现异常，则记录错误日志
            logger.error("设置redisKey:{},value:{}失败", key, value);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void delete(String... key) {
        // 判断key是否为空
        if (key != null && key.length > 0) {
            // 判断key的长度是否为1
            if (key.length == 1) {
                // 如果key的长度为1，则删除该key
                redisTemplate.delete(key[0]);
            } else {
                // 如果key的长度大于1，则将key转换为集合，并删除集合中的所有key
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 从列表中移除指定的值，移除的数量为 1
     *
     * @param key
     * @param value
     * @return
     */
    public long remove(String key, Object value) {
        try {
            // 从列表中移除指定的值，移除的数量为 1
            Long remove = redisTemplate.opsForList().remove(key, 1, value);
            return remove;
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据 key获取 value
     *
     * @param key
     * @return
     */
    public V get(String key) {
        // 如果key为空，则返回null
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取列表类型键的所有元素
     *
     * @param key 键
     * @return 返回列表类型键的所有元素
     */
    public List<V> getQueueList(String key) {
        // 使用redisTemplate的opsForList()方法获取列表类型键的所有元素
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 将一个值插入到列表的左边（头部）。如果指定了过期时间，则设置过期时间
     *
     * @param key 键
     * @param value 值
     * @param expired s
     * @return
     */
    public boolean lpush(String key, V value, Long expired) {
        try {
            // 将值插入到列表的左边（头部）
            redisTemplate.opsForList().leftPush(key, value);
            // 如果指定了过期时间，则设置过期时间
            if (expired != null && expired > 0) {
                expire(key, expired);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将多个值插入到列表的左边（头部）。如果指定了过期时间，则设置过期时间
     *
     * @param key 键
     * @param values 值列表
     * @param expired s
     * @return
     */
    public boolean lpushAll(String key, List<V> values, long expired) {
        try {
            // 将多个值插入到列表的左边（头部）
            redisTemplate.opsForList().leftPushAll(key, values);
            // 如果指定了过期时间，则设置过期时间
            if (expired > 0) {
                expire(key, expired);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从列表的右边（尾部）弹出一个值
     *
     * @param key
     * @return
     */
    public V rpop(String key) {
        // 从redisTemplate中获取opsForList对象
        try {
            // 从列表的右边（尾部）弹出一个值
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            // 返回null
            return null;
        }
    }

    /**
     * key是否存在
     *
     * @param key
     * @return
     */
    public boolean keyExists(String key) {
        // 判断redisTemplate中是否存在key
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据键前缀获取所有匹配的键
     *
     * @param keyPrifix 键前缀
     * @return 匹配的键集合
     */
    public Set<String> getByKeyPrefix(String keyPrifix) {
        // 使用redisTemplate的keys方法获取所有匹配的键
        Set<String> keyList = redisTemplate.keys(keyPrifix + "*");
        return keyList;
    }

    /**
     * 根据键前缀批量获取键值对
     *
     * @param keyPrifix 键前缀
     * @return 返回键值对集合
     */
    public Map<String, V> getBatch(String keyPrifix) {
        // 根据键前缀获取所有键
        Set<String> keySet = redisTemplate.keys(keyPrifix + "*");
        // 将键集合转换为列表
        List<String> keyList = new ArrayList<>(keySet);
        // 根据键列表批量获取值
        List<V> keyValueList = redisTemplate.opsForValue().multiGet(keyList);
        // 将键列表和值列表转换为键值对集合
        Map<String, V> resultMap = keyList.stream().collect(Collectors.toMap(key -> key, value -> keyValueList.get(keyList.indexOf(value))));
        // 返回键值对集合
        return resultMap;
    }

    /**
     * 为指定的键设置过期时间（以毫秒为单位）
     *
     * @param key
     * @param expired s
     */
    public void expire(String key, long expired) {
        try {
            // 如果过期时间大于0，则设置过期时间
            if (expired > 0) {
                redisTemplate.expire(key, expired, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        }
    }

}