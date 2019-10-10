package cn.chen.service;

import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.pojo.Package;

import java.util.List;

/**
 * @author haixin
 * @time 2019-10-03
 */
public interface PackageService {
    void add(Package pkg, Integer[] checkgroupIds);

    PageResult<Package> findPage(QueryPageBean queryPageBean);

    List<Package> findAll();

    Package findById(int id);
}
