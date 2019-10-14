package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.constant.RedisMessageConstant;
import cn.chen.entity.Result;
import cn.chen.exception.MyException;
import cn.chen.pojo.Member;
import cn.chen.service.MemberService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author haixin
 * @time 2019-10-14
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;
    @PostMapping("/check")
    public Result checkLogin(@RequestBody Map<String,String>map, HttpServletResponse httpServletResponse){
        String telephone = map.get("telepjone");
        String validateCode = map.get("validateCode");

        Jedis resource = jedisPool.getResource();
        String redisValidateCode = resource.get(RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone);

        if (StringUtils.isEmpty(redisValidateCode) || !redisValidateCode.equalsIgnoreCase(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else {
            //清除redis缓存
            resource.del(RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone);
            //查看是否会员，不是则注册
            Member member = memberService.findByTelephone(telephone);
            if (null == member) {
                Member member1 = new Member();
                member1.setPhoneNumber(telephone);
                member1.setRegTime(new Date());
                memberService.add(member1);
            }
        }
        //跟踪用户行为
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setMaxAge(60*60*24*30);
        //访问任何网页都会带上这个cookie
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
