package com.hp.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 严敏
 * @since 2023-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_title_category")
@ApiModel(value="TitleCategory对象", description="")
public class TitleCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职位id-流水号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "职位编号")
    private String titleNum;

    @ApiModelProperty(value = "职位名称")
    private String titleName;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "0：删除   1 : 正常使用   2：禁用")
    private Integer status;

    @ApiModelProperty(value = "职位层级")
    private Integer level;

    @ApiModelProperty(value="上级名称")
    @TableField(exist = false) //表示该字段在数据库中是不存在的
    private String parentTitleName;

}
