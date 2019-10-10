package cn.chen.service;

import cn.chen.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haixin
 * @time 2019-10-08
 */
public interface OrderSettingService {
    void add(ArrayList<OrderSetting> orderSettingList);

    List<OrderSetting> getOrderSettingByMouth(String mouth);

    void editNumberByDate(OrderSetting orderSetting);
}
