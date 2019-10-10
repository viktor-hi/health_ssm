package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.entity.Result;
import cn.chen.pojo.Package;
import cn.chen.service.PackageService;
import cn.chen.utils.QiNiuUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author haixin
 * @time 2019-10-10
 */

@RestController
@RequestMapping("/package")
public class PackageController {

    @Reference
    private PackageService packageService;

    @PostMapping("/getPackage")
    public Result getAllPackage(){
        try {
            List<Package> resultPackage = packageService.findAll();
            for (Package aPackage : resultPackage) {
                aPackage.setImg(QiNiuUtil.DOMAIN+"/"+aPackage.getImg());
            }
            return new Result(true,MessageConstant.QUERY_PACKAGE_SUCCESS,resultPackage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_PACKAGE_FAIL);
    }

    @PostMapping("/findById")
    public Result findById(int id){
        Package aPackage = packageService.findById(id);
        return new Result(true, MessageConstant.QUERY_PACKAGE_FAIL,aPackage);
    }
}
