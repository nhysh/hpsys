package com.hp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hp.dto.DeptDto;
import com.hp.pojo.Dept;
import com.hp.mapper.DeptMapper;
import com.hp.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hp.utils.AssertUtil;
import com.hp.utils.PageResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 严敏
 * @since 2023-01-02
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    public Map<String, Object> deptList() {
        List<Dept> list = this.baseMapper.selectAllDepts();
        Map<String, Object> map = PageResultUtil.getResult((long)list.size(),list);
        return map;
    }

    @Override
    public void saveDept(Dept dept) {
        checkParam(dept.getDeptName(),dept.getDeptNum());

        AssertUtil.isTrue(null != this.findDeptByDeptNum(dept.getDeptNum()),"部门编号不可重复");
        Dept temp = this.findDeptByDeptNameAndLevel(dept.getDeptName(), dept.getLevel());
        AssertUtil.isTrue(null != temp && (dept.getParentId().equals(temp.getParentId())),"同一级部门名已经存在");
//        dept.setCreateTime(new Date());
//        dept.setUpdateTime(new Date());
        dept.setStatus(1);
        AssertUtil.isTrue(!(this.save(dept)),"添加记录失败");
    }

    @Override
    public void updateDept(Dept dept) {
        AssertUtil.isTrue(null == this.baseMapper.selectById(dept.getId()),"待更新记录不存在");
        checkParam(dept.getDeptName(),dept.getDeptNum());
        Dept temp = this.findDeptByDeptNum(dept.getDeptNum());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(dept.getId())),"部门编号已经存在");
        temp = this.findDeptByDeptNameAndLevel(dept.getDeptName(),dept.getLevel());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(dept.getId()) && (dept.getParentId().equals(temp.getParentId()))),"同一级下部门名不能重复");
//        dept.setUpdateTime(new Date());//修改更新时间
        AssertUtil.isTrue(!(this.updateById(dept)),"记录更新失败!");
    }

    @Override
    public void deleteDept(Integer id) {
        //根据id查询要删除的部门记录
        Dept dept = this.baseMapper.selectById(id);
        AssertUtil.isTrue(null == dept,"待删除的记录不存在！");
        //判断该记录下是否有子级
        AssertUtil.isTrue(this.count(new QueryWrapper<Dept>()
                        .eq("parent_id",dept.getId()) //查看是否有子部门
                        .eq("status",1))>0 //判断是否有数据
                ,"存在子部门，暂不支持删除操作");
        dept.setStatus(0);//status设置为0就表示删除
        AssertUtil.isTrue(!(this.updateById(dept)),"部门删除失败");
    }



    private Dept findDeptByDeptNameAndLevel(String deptName, Integer level) {
        return this.baseMapper.selectOne(new QueryWrapper<Dept>()
                .eq("dept_name",deptName)
                .eq("level",level)
                .eq("status",1));
    }

    private Dept findDeptByDeptNum(String deptNum) {
        return this.baseMapper.selectOne(new QueryWrapper<Dept>().eq("dept_num",deptNum));
    }

    private void checkParam(String deptName, String deptNum) {
        AssertUtil.isTrue(StringUtils.isBlank(deptName),"请输入部门名称");
        AssertUtil.isTrue(StringUtils.isBlank(deptNum),"请输入部门编号");
//        做部门名称的重复验证
//        Dept dept = this.baseMapper.selectOne(new QueryWrapper<Dept>().eq("dept_name",deptName));
//        AssertUtil.isTrue(dept!=null,"部门名称重复");
//        Dept dept2 = this.baseMapper.selectOne(new QueryWrapper<Dept>().eq("dept_num",deptNum));
//        AssertUtil.isTrue(dept2 != null,"部门编号重复");
    }

    @Override
    public List<DeptDto> findAllDept() {
        List<DeptDto> results = new ArrayList<>();
        //查询所有的根节点下的子节点
        List<DeptDto> roots = this.baseMapper.findDeptDtoByParentDeptId(0); //这是一级

        if(!CollectionUtils.isEmpty(roots)){ //集合不为空，且集合中有数据
            //遍历一级节点下的子节点
            for (DeptDto root : roots) {
                //根据二级节点递归遍历所有的子节点
                results.add(findSubDeptDto(root)); //将一级加入到了集合中   二级和三级分类怎么加？
                for (DeptDto child : root.getChildren()) {
                    results.add(findSubDeptDto(child));
                }
            }
        }
        return results;
    }
    //递归遍历所有的子节点
    private DeptDto findSubDeptDto(DeptDto root) {
        List<DeptDto> list = this.baseMapper.findDeptDtoByParentDeptId(root.getId()); //这是一级
        List<DeptDto> children = root.getChildren();
        if(!CollectionUtils.isEmpty(list)){
            for (DeptDto dto : list) {
                children.add(dto);
            }
        }
        return root;
    }
}
