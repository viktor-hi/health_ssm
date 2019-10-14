package cn.chen.dao;

import cn.chen.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author haixin
 * @time 2019-10-08
 */

public interface OrderSettingDao {
    OrderSetting findOrderSettingByDate(@Param("orderDate") String orderDate);

    void updata(OrderSetting osInDB);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findOrderSettingByDates(@Param("startDate") String startDate,@Param("endDate")  String endDate);

    void deleteByDay(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
