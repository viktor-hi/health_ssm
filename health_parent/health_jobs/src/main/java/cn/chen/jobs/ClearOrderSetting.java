package cn.chen.jobs;

import cn.chen.dao.OrderSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;


/**
 * @author haixin
 * @time 2019-10-14
 */
public class ClearOrderSetting {

    @Autowired
    private OrderSettingDao orderSettingDao;
    public void clearDB(){
        System.out.println(new Date()+"被调用");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String yearandmonth = simpleDateFormat.format(date);
        String startDate=yearandmonth+"-01";
        String endDate=yearandmonth+"-31";
        orderSettingDao.deleteByDay(startDate,endDate);
    }

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-dbjobs.xml");
    }
}
