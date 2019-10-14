package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.constant.RedisMessageConstant;
import cn.chen.entity.Result;
import cn.chen.pojo.Order;
import cn.chen.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author haixin
 * @time 2019-10-11
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String>map){
        //通过redis缓存判断是否发送过短信
        Jedis resource = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER+"_"+map.get("telephone");
        String jedisValue = resource.get(key);
        if (!StringUtils.isEmpty(jedisValue) && jedisValue.equalsIgnoreCase(map.get("validateCode"))) {
            //验证码正确
            resource.del(key);
            map.put("orderType","微信预约");
            Order resultOrder = orderService.submit(map);
            return new Result(true,MessageConstant.ORDER_SUCCESS,resultOrder);
        }else {
            //未发送验证码，或者验证码过期错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    @PostMapping("/findById")
    public Result findById(int id){
        // 调用业务查询
        Map<String,Object> data = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, data);
    }
}
