package com.hp.controller;


import com.hp.pojo.TitleCategory;
import com.hp.resp.RespBean;
import com.hp.service.ITitleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 严敏
 * @since 2023-01-03
 */
@Controller
@RequestMapping("/titleCategory")
public class TitleCategoryController {
    @Autowired
    private ITitleCategoryService titleCategoryService;

    @RequestMapping("/index")
    public String index(){
        return "title_category/index";
    }

    /**
     * 职位管理列表查询
     */
    @RequestMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('100882')")
    public Map<String,Object> titleCategoryList(){
        return titleCategoryService.titleCategoryList();
    }

    /**
     * 进入到职位添加页面
     */
    @GetMapping("/addTitleCategoryPage")
    public String addTitleCategoryPage(Integer level, Integer parentId, Model model){
        model.addAttribute("titleCategory",titleCategoryService.getById(parentId));
        model.addAttribute("parentId",parentId);
        model.addAttribute("level",level);
        return "title_category/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    @PreAuthorize("hasAuthority('100881')")
    public RespBean saveTitleCategory(TitleCategory titleCategory){
        titleCategoryService.saveTitleCategory(titleCategory);
        return RespBean.success("职位添加成功");
    }

    /**
     * 进入到修改职位页面
     */
    @RequestMapping("/updateTitleCategoryPage")
    public String updateTitleCategoryPage(Integer id,Model model){
        //查询要修改的职位
        TitleCategory titleCategory = titleCategoryService.getById(id);
        //查询父职位
        TitleCategory parentTitleCategory = titleCategoryService.getById(titleCategory.getParentId());
        model.addAttribute("titleCategory",titleCategory);
        model.addAttribute("parentTitleCategory",parentTitleCategory);
        return "title_category/update";
    }

    /**
     * 更新职位
     */
    @RequestMapping("/update")
    @ResponseBody
    @PreAuthorize("hasAuthority('100883')")
    public RespBean updateTitleCategoy(TitleCategory titleCategory){
        titleCategoryService.updateTitleCategory(titleCategory);
        return RespBean.success("修改职位成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('100884')")
    public RespBean deleteDept(Integer id){
        titleCategoryService.deleteDept(id);
        return RespBean.success("部门删除成功");
    }

    @RequestMapping("/queryAllTitleCategories")
    @ResponseBody
    public List<TitleCategory> queryAllTitleCategories(){
        return titleCategoryService.list();
    }
}
