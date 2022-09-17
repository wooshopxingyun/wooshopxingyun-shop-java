package com.wooshop.modules.home.rest;

import com.wooshop.annotation.Log;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.home_total.HomeRateVo;
import com.wooshop.modules.user.WooUserService;
import com.wooshop.modules.user_visit_record.service.WooshopUserVisitRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Api(tags = "主页统计数据")
@RestController
@RequestMapping("/api/hometotal")
public class HomeTotalController {

    private final IGenerator generator;
    private final WooshopUserVisitRecordService userVisitRecordService;//访问记录
    private final WooUserService userService;//用户

    @GetMapping(path = "/index")
    @Log("首页头部统计数据")
    @ApiOperation("首页头部统计数据")
//    @PreAuthorize("@el.check('admin','hometotal:list')")
    public ResponseEntity<HomeRateVo> getHomeTatla(){

        HomeRateVo homeRateVo=new HomeRateVo();
        //今天销售额
        homeRateVo.setSales(new BigDecimal(200.00));
        //总销售额
        homeRateVo.setTotalSales(new BigDecimal(200.00));
        //今天用户访问量
        homeRateVo.setPageviews(userVisitRecordService.todayPageviews());
        //本月订单量
        homeRateVo.setMonthOrderNum(10);
        //今天订单量
        homeRateVo.setOrderNum(1);
        //总订单量
        homeRateVo.setTotalOrderNum(500);
        //今天新增用户
        homeRateVo.setNewUserNum(userService.getTodayNewUser());
        //总用户
        homeRateVo.setTotalUserNum(userService.getTotalUserNUm());
        return new ResponseEntity<>(homeRateVo, HttpStatus.OK);
    }


    @GetMapping(path = "/count/MonthSales")
    @Log("首页数据本月成交额")
    @ApiOperation("首页数据本月成交额")
    public ResponseEntity<Map<String, Object>> getHomeMonthSales(){
        HashMap<String,Object> hashMap =new HashMap<>();
        hashMap.put("chart",20);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }


    @GetMapping(path = "/count/MonthOrderNum")
    @Log("首页数据本月订单数")
    @ApiOperation("首页数据本月订单数")
    public ResponseEntity<Map<String, Object>> getHomeMonthOrderNum(){
        HashMap<String,Object> hashMap =new HashMap<>();
        hashMap.put("chartT", 30);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }


    @GetMapping(path = "/count/classification")
    @Log("首页数据商品分类统计")
    @ApiOperation("首页数据商品分类统计")
    public ResponseEntity<Object> getHomeClassification(){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
