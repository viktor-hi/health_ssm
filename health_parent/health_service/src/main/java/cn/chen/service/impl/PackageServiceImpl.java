package cn.chen.service.impl;

import cn.chen.dao.PackageDao;
import cn.chen.pojo.Package;
import cn.chen.service.PackageService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author haixin
 * @time 2019-10-03
 */
@Service(interfaceClass = PackageService.class)
@Transactional
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;
    @Override
    public void add(Package pkg, Integer[] checkgroupIds) {
        packageDao.addPackage(pkg);
        Integer pkgId = pkg.getId();
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                packageDao.addPackageCheckGroup(pkgId,checkgroupId);
            }
        }
    }
    
}
