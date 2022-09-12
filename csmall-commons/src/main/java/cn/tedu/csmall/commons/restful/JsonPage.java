package cn.tedu.csmall.commons.restful;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

// 通用支持分页查询的结果对象类型
@Data
public class JsonPage<T> implements Serializable {

    // 按照实际需求,定义这个类中的属性
    @ApiModelProperty(value = "当前页码",name = "pageNum")
    private Integer pageNum;
    @ApiModelProperty(value = "每页条数",name = "pageSize")
    private Integer pageSize;
    @ApiModelProperty(value = "总条数",name = "totalCount")
    private Long totalCount;
    @ApiModelProperty(value = "总页数",name = "totalPages")
    private Integer totalPages;
    // 声明一个属性,来承载查询到的分页数据结果
    @ApiModelProperty(value = "分页数据",name = "list")
    private List<T> list;

    // 所有属性写完了,下面要编写将其他框架的分页结果转换成当前类对象的方法
    // SpringDataElasticsearch或PageHelper等具有分页功能的框架,均有类似PageInfo的对象
    // 我们可以分别编写方法,将它们转换成JsonPage对象,我们先只编写PageHelper的转换
    public static <T> JsonPage<T> restPage(PageInfo<T> pageInfo){
        // 下面开始将pageInfo对象的属性赋值给JsonPage对象
        JsonPage<T> result=new JsonPage<>();
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalCount(pageInfo.getTotal());
        result.setTotalPages(pageInfo.getPages());
        result.setList(pageInfo.getList());
        // 返回赋值完毕的JsonPage对象
        return result;
    }


}
