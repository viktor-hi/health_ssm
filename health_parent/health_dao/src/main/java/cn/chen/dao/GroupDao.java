package cn.chen.dao;

import cn.chen.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haixin
 * @time 2019-09-26
 */
public interface GroupDao {

    void addGroup(CheckGroup group);

    void setGroupAndItemId(HashMap<String, Integer> map);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findItemIdsByGroup(Integer id);

    void deleteAssociation(Integer groupId);

    void updataGroup(CheckGroup checkGroup);

    void deleteGroupById(Integer groupId);
}
