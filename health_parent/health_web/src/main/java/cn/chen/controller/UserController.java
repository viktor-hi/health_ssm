package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.entity.Result;
import cn.chen.pojo.CheckItem;
import cn.chen.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haixin
 * @time 2019-09-22
 */
@RestController
@RequestMapping("/checkitem")
public class UserController {
    @Reference
    private UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem item){
        try {
            userService.add(item);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult<CheckItem> pageResult=userService.findPage(queryPageBean);

        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    @PostMapping("/delete")
    public Result deleteById(int id){
        userService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
