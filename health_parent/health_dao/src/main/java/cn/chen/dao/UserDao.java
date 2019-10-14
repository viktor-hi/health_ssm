package cn.chen.dao;

import cn.chen.pojo.CheckItem;
import cn.chen.pojo.User;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author haixin
 * @time 2019-09-22
 */

public interface UserDao {
    void add(CheckItem item);

    Page<CheckItem> findPageByFuzzy(String queryString);

    int findCountById(int id);

    void deleteById(int id);

    List<CheckItem> findAll();

    User findUserByUsername(String username);
}
