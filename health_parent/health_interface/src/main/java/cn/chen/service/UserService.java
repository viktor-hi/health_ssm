package cn.chen.service;

import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.pojo.CheckItem;

import java.util.List;

/**
 * @author haixin
 * @time 2019-09-22
 */
public interface UserService {
    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void deleteById(int id);

    List<CheckItem> findAll();
}
