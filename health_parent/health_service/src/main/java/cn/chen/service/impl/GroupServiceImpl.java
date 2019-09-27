package cn.chen.service.impl;

import cn.chen.dao.GroupDao;
import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.pojo.CheckGroup;
import cn.chen.service.GroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;

/**
 * @author haixin
 * @time 2019-09-26
 */
@Service(interfaceClass = GroupService.class)
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupDao GroupDao;


    @Override
    public void addGroup(CheckGroup group, Integer[] itemIds) {
        GroupDao.addGroup(group);
        setGroupAndItems(group.getId(),itemIds);
    }
    @Override
    public void setGroupAndItems(Integer groupId, Integer[] itemIds){
        if (itemIds != null && itemIds.length > 0) {
            for (Integer itemId : itemIds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("groupId",groupId);
                map.put("itemId",itemId);
                GroupDao.setGroupAndItemId(map);
            }
        }
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
//        判断是否进行模糊查询
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
//        使用pagehelper插件，下一条查询语句被分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
//        取得分页查询结果
        Page<CheckGroup> groupPage = GroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(groupPage.getTotal(),groupPage.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return GroupDao.findById(id);
    }

    @Override
    public List<Integer> findItemIdsByGroup(Integer id) {
        return GroupDao.findItemIdsByGroup(id);
    }

    @Override
    public void editGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
//        删除原有的组和项的关系
        GroupDao.deleteAssociation(checkGroup.getId());
//        更组信息
        GroupDao.updataGroup(checkGroup);
//        添加新的组和项的关系表
        setGroupAndItems(checkGroup.getId(),checkitemIds);
    }

    @Override
    public void deleteById(Integer groupId) {
        GroupDao.deleteAssociation(groupId);
        GroupDao.deleteGroupById(groupId);
    }
}
