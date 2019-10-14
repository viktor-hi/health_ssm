package cn.chen.service.impl;

import cn.chen.constant.MessageConstant;
import cn.chen.dao.MemberDao;
import cn.chen.dao.OrderDao;
import cn.chen.dao.OrderSettingDao;
import cn.chen.exception.MyException;
import cn.chen.pojo.Member;
import cn.chen.pojo.Order;
import cn.chen.pojo.OrderSetting;
import cn.chen.service.OrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author haixin
 * @time 2019-10-13
 */

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;
    @Override
    public Order submit(Map<String, String> map) {
/*        - 判断是否可以预约
                - 可预约, 判断是否为会员
                - 是会员，取出它的ID，将添加订单表时可以用
                - 不是会员，注册为会员，取出ID，将添加订单表时可以用
                - 判断是否已经预约过了，通过memeber_id，packageid, orderDate
                - 已预约，报错
                - 没预约则预约成功
                - 更新t_ordersetting已预约人数*/
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        String orderDate = null;
        Date orderDate2 = null;
        try {
            //获取预约当天数据orderSettingByDate
            orderDate2 = sdf.parse(map.get("orderDate"));
            orderDate = orderDate2.toString();

        } catch (ParseException e) {
            e.printStackTrace();
            throw new MyException("日期转换失败");
        }
        OrderSetting orderSettingByDate = orderSettingDao.findOrderSettingByDate(map.get("orderDate"));

        //判断是否可以预约
        if (null == orderSettingByDate) {
            throw new MyException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSettingByDate.getNumber() <= orderSettingByDate.getReservations()){
            throw new MyException(MessageConstant.ORDER_FULL);
        }

        // 可预约,通过手机号码 判断是否为会员
        String telephone = map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);

        Order order = null;
//        - 是会员，取出它的ID，将添加订单表时可以用
        if (null != member) {
            Integer memberId = member.getId();
            order = new Order();
            order.setMemberId(memberId);
            order.setPackageId(Integer.valueOf(map.get("setmealId")));
            order.setOrderDate(orderDate2);
            List<Order> orders = orderDao.findByCondition(order);
            //已预约，报错
            if(null != orders && orders.size() > 0){
                throw new MyException(MessageConstant.HAS_ORDERED);
            }else {
                order.setOrderStatus("未到诊");
                order.setOrderType(map.get("orderType"));
                orderDao.add(order);
            }
        }else {
            //        - 不是会员，注册为会员，取出ID，将添加订单表时可以用
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setSex(map.get("sex"));
            member.setName(map.get("name"));
            member.setIdCard(map.get("idCard"));
            memberDao.add(member);
            //可以预约

            order = new Order();
            order.setMemberId(member.getId());
            order.setPackageId(Integer.valueOf(map.get("setmealId")));
            order.setOrderDate(orderDate2);
            order.setOrderStatus("未到诊");
            order.setOrderType(map.get("orderType"));
            orderDao.add(order);
        }


        return order;
    }

    @Override
    public Map<String, Object> findById(int id) {
        return orderDao.findById4Detail(id);
    }

}
