package com.example.mailadmin.controller;


import com.example.mailadmin.dto.AddProductsDTO;
import com.example.mailadmin.dto.EditProductsDTO;
import com.example.mailadmin.dto.ProductsPageQueryDTO;
import com.example.mailadmin.dto.StockUpdateDTO;
import com.example.mailadmin.entity.Products;
import com.example.mailadmin.service.ProductsService;
import com.example.result.PageResult;
import com.example.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;

import static com.example.result.Result.success;


/**
 * 商品Controller
 *
 * @author sunxiaoming
 * @date 2026-01-31
 */
@RestController
@RequestMapping("/products/products")
@Api(tags = "商品管理")
public class ProductsController
{
    @Autowired
    private ProductsService productsService;

    /**
     * 分页查询商品列表
     */

    @GetMapping("/page")
    @ApiOperation(value = "分页查询商品列表", notes = "根据条件分页查询商品列表，支持商品名称、分类等条件搜索")
    public Result<PageResult> Page(@ApiParam(name = "productsPageQueryDTO", value = "商品分页查询参数", required = true) @ModelAttribute ProductsPageQueryDTO productsPageQueryDTO)
    {

        PageResult pageResult = productsService.PageQuery(productsPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 获取商品详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取商品详细信息", notes = "根据商品ID获取商品的详细信息")
    public Result<Products> getInfo(@ApiParam(name = "id", value = "商品ID", required = true) @PathVariable("id") Long id)
    {
        return Result.success(productsService.selectProductsById(id));
    }

    /**
     * 新增商品
     */

    @PostMapping("/add")
    @ApiOperation(value = "新增商品", notes = "添加新的商品信息")
    public Result add(@ApiParam(name = "addProductsDTO", value = "新增商品信息", required = true) @RequestBody AddProductsDTO addProductsDTO)
    {
        productsService.insertProducts(addProductsDTO);
        return Result.success();
    }

    /**
     * 修改商品
     */

    @PutMapping("/edit")
    @ApiOperation(value = "修改商品", notes = "更新商品的详细信息")
    public Result edit(@ApiParam(name = "editProductsDTO", value = "修改商品信息", required = true) @RequestBody EditProductsDTO editProductsDTO)
    {
        productsService.updateProducts(editProductsDTO);
        return Result.success();
    }

    /**
     * 删除商品
     */

    @DeleteMapping("/remove/{ids}")
    @ApiOperation(value = "删除商品", notes = "批量删除商品")
    public Result remove(@ApiParam(name = "ids", value = "商品ID数组", required = true) @PathVariable Long[] ids)
    {
      productsService.deleteProductsByIds(ids);
      return Result.success();
    }

    /**
     * 更新商品库存
     */
    @PutMapping("/updateStock")
    @ApiOperation(value = "更新商品库存", notes = "更新单个商品的库存数量")
    public Result updateStock(@ApiParam(name = "id", value = "商品ID", required = true) @RequestParam Long id, 
                             @ApiParam(name = "stock", value = "库存数量", required = true) @RequestParam Integer stock)
    {
        productsService.updateStock(id, stock);
        return Result.success();
    }

    /**
     * 批量更新商品库存
     */
    @PutMapping("/batchUpdateStock")
    @ApiOperation(value = "批量更新商品库存", notes = "批量更新多个商品的库存数量")
    public Result batchUpdateStock(@ApiParam(name = "stockUpdateDTOs", value = "库存更新列表", required = true) @RequestBody List<StockUpdateDTO> stockUpdateDTOs)
    {
        productsService.batchUpdateStock(stockUpdateDTOs);
        return Result.success();
    }

    /**
     * 上架商品
     */
    @PutMapping("/enable/{ids}")
    @ApiOperation(value = "上架商品", notes = "批量将商品状态设置为上架")
    public Result enable(@ApiParam(name = "ids", value = "商品ID数组", required = true) @PathVariable Long[] ids)
    {
        productsService.enable(ids);
        return Result.success();
    }

    /**
     * 下架商品
     */
    @PutMapping("/disable/{ids}")
    @ApiOperation(value = "下架商品", notes = "批量将商品状态设置为下架")
    public Result disable(@ApiParam(name = "ids", value = "商品ID数组", required = true) @PathVariable Long[] ids)
    {
        productsService.disable(ids);
        return Result.success();
    }
}


