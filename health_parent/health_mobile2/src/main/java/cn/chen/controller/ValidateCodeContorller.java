package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.constant.RedisConstant;
import cn.chen.constant.RedisMessageConstant;
import cn.chen.entity.Result;
import cn.chen.utils.SMSUtils;
import cn.chen.utils.ValidateCodeUtils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequestMapping("/validateCode")
@RestController
public class ValidateCodeContorller {

    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //通过redis缓存判断是否发送过短信
        Jedis resource = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER+"_"+telephone;
        if (null!=resource.get(key)) {
            //缓存中存在数据
            return new Result(true,MessageConstant.SENT_VALIDATECODE);
        }

        try {
            String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            resource.setex(key,5*60,validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }finally {
            resource.close();
        }
        return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        Jedis resource = jedisPool.getResource();

        String key = RedisMessageConstant.SENDTYPE_LOGIN+"_"+telephone;
        String value = resource.get(key);

        if (null!=value) {
            return new Result(false,MessageConstant.SENT_VALIDATECODE);
        }

        try {
            String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            resource.setex(key,5*60,validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }finally {
            resource.close();
        }
        return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
