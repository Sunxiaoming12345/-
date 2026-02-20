package com.example.mailadmin.controller;

import com.example.mailadmin.dto.PaymentsPageQueryDTO;
import com.example.mailadmin.service.PaymentService;
import com.example.mailadmin.vo.PaymentStatisticsVO;
import com.example.result.PageResult;
import com.example.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/payments")
@Api(tags = "支付管理")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 支付记录分页查询，GET 用查询参数
    @GetMapping("/page")
    @ApiOperation(value = "支付记录分页查询", notes = "根据条件分页查询支付记录，支持支付状态、订单号等条件搜索")
    public Result<PageResult> page(@ApiParam(name = "paymentsPageQueryDTO", value = "支付记录分页查询参数", required = true) @ModelAttribute PaymentsPageQueryDTO paymentsPageQueryDTO) {
        PageResult pageResult = paymentService.pageQuery(paymentsPageQueryDTO);
        return Result.success(pageResult);
    }

    // 更新支付状态
    @PutMapping("/updateStatus")
    @ApiOperation(value = "更新支付状态", notes = "更新支付记录的状态，如未支付、已支付、支付失败等")
    public Result updateStatus(@ApiParam(name = "paymentId", value = "支付记录ID", required = true) @RequestParam Long paymentId, 
                             @ApiParam(name = "status", value = "支付状态", required = true) @RequestParam Integer status) {
        paymentService.updateStatus(paymentId, status);
        return Result.success();
    }

    // 获取支付统计信息
    @GetMapping("/statistics")
    @ApiOperation(value = "获取支付统计信息", notes = "获取支付金额、支付方式分布等统计信息")
    public Result<PaymentStatisticsVO> getStatistics() {
        PaymentStatisticsVO paymentStatisticsVO = paymentService.getStatistics();
        return Result.success(paymentStatisticsVO);
    }

}

