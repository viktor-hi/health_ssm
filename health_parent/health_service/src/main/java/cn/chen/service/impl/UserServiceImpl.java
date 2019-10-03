package cn.chen.service.impl;

import cn.chen.dao.UserDao;
import cn.chen.entity.PageResult;
import cn.chen.entity.QueryPageBean;
import cn.chen.pojo.CheckItem;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import cn.chen.service.UserService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author haixin
 * @time 2019-09-22
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;
    @Override
    public void add(CheckItem checkItem) {
        userdao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
//        判断是否需要进行模糊查询
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckItem> checkItemPage = userdao.findPageByFuzzy(queryPageBean.getQueryString());
        return new PageResult<CheckItem>(checkItemPage.getTotal(), checkItemPage.getResult());
    }

    @Override
    public void deleteById(int id) {
        int num = userdao.findCountById(id);
        if (num>0) {
            // 有引用，不允许删除

        }
        userdao.deleteById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return userdao.findAll();
    }
}
