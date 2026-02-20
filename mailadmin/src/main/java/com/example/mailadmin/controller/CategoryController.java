package com.example.mailadmin.controller;

import com.example.mailadmin.dto.AddCategoryDTO;
import com.example.mailadmin.dto.EditCategoryDTO;
import com.example.mailadmin.entity.Category;
import com.example.mailadmin.service.CategoryService;
import com.example.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类Controller
 *
 * @author sunxiaoming
 * @date 2026-02-06
 */
@RestController
@RequestMapping("/admin/categories")
@Api(tags = "分类管理")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取所有分类列表", notes = "获取所有商品分类，按排序权重排序")
    public Result<List<Category>> list() {
        List<Category> categories = categoryService.selectAll();
        return Result.success(categories);
    }

    /**
     * 获取分类详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取分类详细信息", notes = "根据分类ID获取分类的详细信息")
    public Result<Category> getInfo(@ApiParam(name = "id", value = "分类ID", required = true) @PathVariable("id") Long id) {
        return Result.success(categoryService.selectById(id));
    }

    /**
     * 新增分类
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增分类", notes = "添加新的商品分类")
    public Result add(@ApiParam(name = "addCategoryDTO", value = "新增分类信息", required = true) @RequestBody AddCategoryDTO addCategoryDTO) {
        categoryService.insertCategory(addCategoryDTO);
        return Result.success();
    }

    /**
     * 修改分类
     */
    @PutMapping("/edit")
    @ApiOperation(value = "修改分类", notes = "更新分类的详细信息")
    public Result edit(@ApiParam(name = "editCategoryDTO", value = "修改分类信息", required = true) @RequestBody EditCategoryDTO editCategoryDTO) {
        categoryService.updateCategory(editCategoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/remove/{ids}")
    @ApiOperation(value = "删除分类", notes = "批量删除商品分类")
    public Result remove(@ApiParam(name = "ids", value = "分类ID数组", required = true) @PathVariable Long[] ids) {
        categoryService.deleteByIds(ids);
        return Result.success();
    }
}

