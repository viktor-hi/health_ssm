package cn.chen.dao;

import cn.chen.pojo.Package;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haixin
 * @time 2019-10-03
 */
public interface PackageDao {
    void addPackage(Package pkg);

    void addPackageCheckGroup(@Param("pkgId") Integer pkgId,@Param("checkgroupId") Integer checkgroupId);

    Page<Package> findPage(String queryString);

    List<Package> findAll();

    Package findById(int id);
}
