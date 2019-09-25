package cn.chen.service;

import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.exception.MyException;
import cn.chen.pojo.CheckItem;

/**
 * @author haixin
 * @time 2019-09-22
 */
public interface UserService {
    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void deleteById(int id) throws MyException;
}
