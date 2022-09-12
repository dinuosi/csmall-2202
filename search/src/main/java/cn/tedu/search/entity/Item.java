package cn.tedu.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Accessors(chain = true)    // 生成和链式赋值的set方法
@AllArgsConstructor         // 自动生成包含全部参数的构造方法
@NoArgsConstructor          // 自动生成无参构造方法
// SpringData要求我们在"实体类"中使用特定注解标记
// @Document注解标记当前类和ES关联
// indexName指定索引名称,我们这里叫items,当操作这个索引时,如果索引不存在,会自动创建
@Document(indexName = "items")
public class Item  implements Serializable {


    // SpingData标记这个字段为当前类主键
    @Id
    private Long id;
    // SpringData使用@Field标记文档中属性的类型和各种特征
    @Field(type = FieldType.Text,
            analyzer = "ik_max_word",
            searchAnalyzer = "ik_max_word")
    private String title;     //商品名称
    @Field(type = FieldType.Keyword)
    private String category;  //分类
    @Field(type = FieldType.Keyword)
    private String brand;     //品牌
    @Field(type = FieldType.Double)
    private Double price;     //价格
    // 图片地址不会称为搜索条件,所以设置index=false
    // 效果是imgPath字段不会生成索引库,节省空间
    @Field(type = FieldType.Keyword,index = false)
    private String imgPath;   //图片地址

    // images/hjdsf-ahsa-qwezx-jashjdas.png
    // Text和Keyword都是字符串类型,只是Text会分词,而Keyword不会!
}







