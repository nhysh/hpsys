package com.hp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hp.pojo.Dept;
import com.hp.pojo.TitleCategory;
import com.hp.mapper.TitleCategoryMapper;
import com.hp.service.ITitleCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.utils.AssertUtil;
import com.hp.utils.PageResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-03
 */
@Service
public class TitleCategoryServiceImpl extends ServiceImpl<TitleCategoryMapper, TitleCategory> implements ITitleCategoryService {

    @Override
    public Map<String, Object> titleCategoryList() {
        List<TitleCategory> list = this.baseMapper.selectAllTitleCateggory();
        return PageResultUtil.getResult((long)list.size(),list);
    }

    @Override
    public void saveTitleCategory(TitleCategory titleCategory) {
        //1.参数的校验
        checkTitleCategoryParams(titleCategory.getTitleName(),titleCategory.getTitleNum());
        //校验是否重复
        AssertUtil.isTrue(null != this.findTitleCategoryByTileNum(titleCategory.getTitleNum()),"职位编号不能重复！");
        //查询同级别下的职位名称
        TitleCategory temp = this.findTitleCategoryByTitleNameAndLevl(titleCategory.getTitleName(),titleCategory.getLevel());
        AssertUtil.isTrue(null != temp && (temp.getParentId().equals(titleCategory.getParentId())),"同一级别下职位名称已经存在");
        titleCategory.setCreateTime(new Date());
        titleCategory.setUpdateTime(new Date());
        titleCategory.setStatus(1);
        //添加
        AssertUtil.isTrue(!(this.save(titleCategory)),"职位记录添加失败");
    }

    @Override
    public void updateTitleCategory(TitleCategory titleCategory) {
        AssertUtil.isTrue(null == this.baseMapper.selectById(titleCategory.getId()),"待更新的记录不存在！");
        checkTitleCategoryParams(titleCategory.getTitleName(),titleCategory.getTitleNum());
        TitleCategory temp = this.findTitleCategoryByTileNum(titleCategory.getTitleNum());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(titleCategory.getId())),"职位号不能重复");
        temp = this.findTitleCategoryByTitleNameAndLevl(titleCategory.getTitleName(),titleCategory.getLevel());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(titleCategory.getId())),"同一级别下职位名已经存在！");
        titleCategory.setUpdateTime(new Date());
        AssertUtil.isTrue(!(this.updateById(titleCategory)),"职位记录更新失败!");
    }

    @Override
    public void deleteDept(Integer id) {
        TitleCategory titleCategory = this.baseMapper.selectById(id);
        AssertUtil.isTrue(null==titleCategory,"待删除的记录不存在！");
        //判断该记录下是否有子级
        AssertUtil.isTrue(this.count(new QueryWrapper<TitleCategory>()
                        .eq("parent_id",titleCategory.getId()) //查看是否有子部门
                        .eq("status",1))>0 //判断是否有数据
                ,"存在子部门，暂不支持删除操作");
        titleCategory.setStatus(0);//status设置为0就表示删除
        AssertUtil.isTrue(!(this.updateById(titleCategory)),"部门删除失败");
    }

    private TitleCategory findTitleCategoryByTileNum(String titleNum) {
        return this.baseMapper.selectOne(new QueryWrapper<TitleCategory>()
                .eq("title_num",titleNum)
                .eq("status",1));
    }

    private TitleCategory findTitleCategoryByTitleNameAndLevl(String titleName, Integer level) {
        return  this.baseMapper.selectOne(new QueryWrapper<TitleCategory>()
                .eq("title_name",titleName)
                .eq("level",level)
                .eq("status",1));
    }

    private void checkTitleCategoryParams(String titleName, String titleNum) {
        AssertUtil.isTrue(StringUtils.isBlank(titleName),"职位名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(titleNum),"职位编号不能为空");
    }
}
