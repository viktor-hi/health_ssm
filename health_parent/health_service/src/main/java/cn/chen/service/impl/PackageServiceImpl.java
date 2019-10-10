package cn.chen.service.impl;

import cn.chen.dao.PackageDao;
import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.pojo.Package;
import cn.chen.service.PackageService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


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

    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {
        //判断是否进行模糊查询
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //开始进行分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Package> page = packageDao.findPage(queryPageBean.getQueryString());
        //返回pagesult
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public List<Package> findAll() {
        return packageDao.findAll();
    }

    @Override
    public Package findById(int id) {
        return packageDao.findById(id);
    }

}
