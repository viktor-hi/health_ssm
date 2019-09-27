package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.entity.Result;
import cn.chen.pojo.CheckGroup;
import cn.chen.service.GroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haixin
 * @time 2019-09-26
 */
@RestController
@RequestMapping("/group")
public class GroupController {
    @Reference
    private GroupService groupService;

    @RequestMapping("/addGroup")
    public Result addGroup(@RequestBody CheckGroup group,Integer[] checkitemIds){
        groupService.addGroup(group,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckGroup> pageResult = groupService.findPage(queryPageBean);
       return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @GetMapping("/findById")
    public Result findById(Integer id){
        CheckGroup checkGroup = groupService.findById(id);
        if (checkGroup != null) {
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL,null);
    }

    @GetMapping("/findItemIdsByGroupId")
    public Result findItemIdsByGroupId(Integer id){
        List<Integer> itemIds = groupService.findItemIdsByGroup(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,itemIds);
    }

    @PostMapping("/edit")
    public Result editGroup(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        groupService.editGroup(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result deleteGroupById(Integer id){
        groupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
