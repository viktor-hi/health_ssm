package cn.chen.service;

import cn.chen.pojo.Package;

/**
 * @author haixin
 * @time 2019-10-03
 */
public interface PackageService {
    void add(Package pkg, Integer[] checkgroupIds);
}
