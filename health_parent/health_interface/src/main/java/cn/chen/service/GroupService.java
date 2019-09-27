package cn.chen.service;

import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.pojo.CheckGroup;

import java.util.List;

/**
 * @author haixin
 * @time 2019-09-26
 */
public interface GroupService {
    void addGroup(CheckGroup group, Integer[] itemIds);

    void setGroupAndItems(Integer groupId, Integer[] itemIds);

    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findItemIdsByGroup(Integer id);

    void editGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(Integer groupId);
}
