package cn.chen.dao;

import cn.chen.pojo.CheckItem;
import com.github.pagehelper.Page;
/**
 * @author haixin
 * @time 2019-09-22
 */

public interface UserDao {
    void add(CheckItem item);

    Page<CheckItem> findPageByFuzzy(String queryString);

    int findCountById(int id);

    void deleteById(int id);
}
