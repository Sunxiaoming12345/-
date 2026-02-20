package com.example.mailadmin.controller;

import com.example.mailadmin.dto.OrdersPageQueryDTO;
import com.example.mailadmin.dto.UpdateOrderStatusDTO;
import com.example.mailadmin.service.OrdersService;
import com.example.mailadmin.vo.OrderDetailVO;
import com.example.mailadmin.vo.OrderStatisticsVO;
import com.example.result.PageResult;
import com.example.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
@Api(tags = "订单管理")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    //订单分页查询(可根据订单状态)，GET 用查询参数
    @GetMapping("/page")
    @ApiOperation(value = "订单分页查询", notes = "根据条件分页查询订单列表，支持订单状态、订单号等条件搜索")
    public Result<PageResult> page(@ApiParam(name = "ordersPageQueryDTO", value = "订单分页查询参数", required = true) @ModelAttribute OrdersPageQueryDTO ordersPageQueryDTO){

        PageResult pageResult = ordersService.PageQuery(ordersPageQueryDTO);
        return Result.success(pageResult);

    }

    //获取订单详情
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取订单详情", notes = "根据订单ID获取订单的详细信息，包括订单项、支付状态等")
    public Result<OrderDetailVO> getDetail(@ApiParam(name = "id", value = "订单ID", required = true) @PathVariable("id") Long id){
        OrderDetailVO orderDetailVO = ordersService.getDetail(id);
        return Result.success(orderDetailVO);
    }

    //更新订单状态
    @PutMapping("/updateStatus")
    @ApiOperation(value = "更新订单状态", notes = "更新订单的状态，如待支付、已支付、待发货等")
    public Result updateStatus(@ApiParam(name = "updateOrderStatusDTO", value = "更新订单状态参数", required = true) @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO){
        ordersService.updateStatus(updateOrderStatusDTO);
        return Result.success();
    }

    //获取订单统计信息
    @GetMapping("/statistics")
    @ApiOperation(value = "获取订单统计信息", notes = "获取订单数量、销售额等统计信息")
    public Result<OrderStatisticsVO> getStatistics(){
        OrderStatisticsVO orderStatisticsVO = ordersService.getStatistics();
        return Result.success(orderStatisticsVO);
    }

}

