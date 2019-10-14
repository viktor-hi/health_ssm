package cn.chen.service;

import cn.chen.pojo.Order;

import java.util.Map;

/**
 * @author haixin
 * @time 2019-10-13
 */
public interface OrderService {
    Order submit(Map<String, String> map);


    Map<String, Object> findById(int id);
}
