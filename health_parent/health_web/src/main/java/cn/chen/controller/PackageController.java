package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.constant.RedisConstant;
import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.entity.Result;
import cn.chen.pojo.Package;
import cn.chen.service.PackageService;
import cn.chen.utils.QiNiuUtil;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author haixin
 * @time 2019-10-01
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference
    private PackageService service;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile.getOriginalFilename());
//        取得上传文件原文件名
        String filename = imgFile.getOriginalFilename();
//            产生唯一的uuid
        UUID uuid = UUID.randomUUID();
//        生成新的文件名，取原有的文件名扩展名拼接uuid
        String newFileName = uuid.toString() + filename.substring(filename.lastIndexOf("."));
//        使用七牛云util上传,并放回图片新名称和图片域名给前端
        try {
            QiNiuUtil.uploadViaByte(imgFile.getBytes(),newFileName);
            //将上传图片名称存入redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newFileName);
            HashMap<String, String> map = new HashMap<>();
            map.put("picName",newFileName);
            map.put("domain",QiNiuUtil.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Package pkg, Integer[] checkgroupIds){
        service.add(pkg,checkgroupIds);
        //将实际需要上传图片存入redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pkg.getImg());
        return new Result(true,MessageConstant.ADD_PACKAGE_SUCCESS,null);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Package> pageResult = service.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_PACKAGE_SUCCESS,pageResult);
    }
}
