package cn.chen.service.impl;

import cn.chen.dao.OrderSettingDao;
import cn.chen.pojo.OrderSetting;
import cn.chen.service.OrderSettingService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haixin
 * @time 2019-10-08
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(ArrayList<OrderSetting> orderSettingList) {
        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
        for (OrderSetting orderSetting : orderSettingList) {
            OrderSetting osInDB = orderSettingDao.findOrderSettingByDate(formatter.format(orderSetting.getOrderDate()));
            if (null != osInDB) {
                osInDB.setNumber(orderSetting.getNumber());
                orderSettingDao.updata(osInDB);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<OrderSetting> getOrderSettingByMouth(String mouth) {
        String startDate = mouth + "-01";
        String endDate = mouth + "-31";
        return orderSettingDao.findOrderSettingByDates(startDate,endDate);
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
        OrderSetting osInDB = orderSettingDao.findOrderSettingByDate(formatter.format(orderSetting.getOrderDate()));
        if (null != osInDB) {
            osInDB.setNumber(orderSetting.getNumber());
            orderSettingDao.updata(osInDB);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
