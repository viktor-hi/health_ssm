package cn.chen.controller;

import cn.chen.constant.MessageConstant;
import cn.chen.entity.Result;
import cn.chen.pojo.OrderSetting;
import cn.chen.service.OrderSettingService;
import cn.chen.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author haixin
 * @time 2019-10-08
 */

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list != null && list.size() > 0) {
                ArrayList<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String mouth){//参数格式为：2019-03
        // 转成leftobj的格式 { date: 6, number: 120, reservations: 1 } => map
        // [{key:{key2:[{},{key3:[]}]}}] => List<Map<key,Map<ke2,List<Map<key3,List>>>>>
        // 返回的结果集
        List<OrderSetting> mouthOrderSetting = orderSettingService.getOrderSettingByMouth(mouth);

        //格式化返回结果返回前端显示
        ArrayList<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d");

        for (OrderSetting orderSetting : mouthOrderSetting) {
            HashMap<String, Object> dayData = new HashMap<>();
            dayData.put("number",orderSetting.getNumber());
            dayData.put("reservations",orderSetting.getReservations());
            dayData.put("date",Integer.parseInt(simpleDateFormat.format(orderSetting.getOrderDate())));

            resultMap.add(dayData);
        }

        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,resultMap);
    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}
