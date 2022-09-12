package cn.tedu.search.repository;


import cn.tedu.search.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

// Spring 家族下持久层名称都叫repository
@Repository
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
    // 当前接口继承ElasticsearchRepository父接口后
    // 会自动在类中生成基本的增删改查方法,直接可以使用
    // 它自动识别或自动生成的规则,是我们定义的两个泛型ElasticsearchRepository<[实体类名],[主键类型]>

    // SpringData自定义查询
    // 遵循SpringData框架规定的格式的前提下,编写方法名会自动生成查询逻辑
    // query: 表示当前方法是一个查询功能,类似sql中的select
    // Item\Items: 表示查询结果的实体类,带s的返回集合
    // By:标识开始设置条件,类似sql的where
    // Title: 要查询的字段名称
    // Matches: 是要执行的查询操作,这里是分词查询,类似sql的like
    Iterable<Item> queryItemsByTitleMatches(String title);


    // 多条件查询
    // 两个或多个条件之间直接编写And或Or表示查询逻辑
    // 参数名称实际上没有要求必须和字段名称匹配,底层代码是按照参数顺序赋值的
    Iterable<Item> queryItemsByTitleMatchesAndBrandMatches(String title,String brand);

    // 排序查询
    // 默认情况下,ES查询结果按score排序,如果想按其他的规则排序可以加OrderBy
    // 和数据库一样,默认升序排序 Desc结尾会降序
    Iterable<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(
                                    String title, String brand);

    // 分页查询
    // 当查询数据较多时,我们可以利用SpringData的分页功能,按用户要求的页码查询需要的数据
    // 返回值修改为Page类型,这个类型对象除了包含Iterable能够包含的集合信息之外,还包含分页信息
    Page<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(
            String title, String brand, Pageable pageable);
    // select * from xxx  limit [(页码-1)*6],6


}
