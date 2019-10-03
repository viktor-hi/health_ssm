package cn.chen.dao;

import cn.chen.pojo.Package;

/**
 * @author haixin
 * @time 2019-10-03
 */
public interface PackageDao {
    void addPackage(Package pkg);

    void addPackageCheckGroup(Integer pkgId, Integer checkgroupId);
}
