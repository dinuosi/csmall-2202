package cn.tedu.search;

import cn.tedu.search.entity.Item;
import cn.tedu.search.repository.ItemRepository;
import org.elasticsearch.common.recycler.Recycler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SearchApplicationTests {

	// 注入SpringData操作Es的持久层对象
	@Autowired
	private ItemRepository itemRepository;

	// 单增
	@Test
	void addOne() {
		// 实例化Item对象,赋值并新增到ES
		Item item=new Item()
				.setId(1L)
				.setTitle("罗技激光无线游戏鼠标")
				.setCategory("鼠标")
				.setBrand("罗技")
				.setPrice(128.0)
				.setImgPath("/1.jpg");
		// 利用自动生成的方法将item新增到ES,索引不存在会自动创建
		itemRepository.save(item);
		System.out.println("ok");
	}

	// 按id查询
	@Test
	void getOne(){
		// SpringData框架自带的按id查询的方法
		// Optional是一个类似包装类的概念,查询的结果封装到了这个类型中
		Optional<Item> optional=itemRepository.findById(1L);
		// 需要使用查询内容时使用optional.get()即可
		System.out.println(optional.get());
	}

	// 批量增
	@Test
	void addList(){
		// 实例化一个List集合
		List<Item> list=new ArrayList<>();
		// 将要新增的Item对象保存到这个List中
		list.add(new Item(2L,"罗技激光有线办公鼠标","鼠标",
				"罗技",89.0,"/2.jpg"));
		list.add(new Item(3L,"雷蛇机械无线游戏键盘","键盘",
				"雷蛇",299.0,"/3.jpg"));
		list.add(new Item(4L,"微软有线静音办公鼠标","鼠标",
				"微软",208.0,"/4.jpg"));
		list.add(new Item(5L,"罗技有线机械背光键盘","键盘",
				"罗技",266.0,"/5.jpg"));
		// 下面使用SpringData提供的方法执行批量新增
		itemRepository.saveAll(list);
		System.out.println("ok");

	}

	// 全查
	@Test
	void getAll(){
		// 利用SpringData的方法从ES中查询所有数据
		Iterable<Item> items=itemRepository.findAll();
//		for(Item item: items){
//			System.out.println(item);
//		}
		items.forEach(item -> System.out.println(item));

	}


	//单条件自定义查询
	@Test
	void queryOne(){
		// 查询 ES中title字段包含"游戏"分词的数据
		Iterable<Item> items=itemRepository.queryItemsByTitleMatches("游戏");
		items.forEach(item -> System.out.println(item));
	}

	// 多条件自定义查询
	@Test
	void queryTwo(){
		Iterable<Item> items=itemRepository
				.queryItemsByTitleMatchesAndBrandMatches("游戏","雷蛇");
		items.forEach(item -> System.out.println(item));
	}

	// 排序查询
	@Test
	void queryOrder(){
		Iterable<Item> items=itemRepository
			.queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc("游戏","罗技");
		items.forEach(item -> System.out.println(item));
	}

	// 分页查询
	@Test
	void queryPage(){
		int pageNum=1;  //页码
		int pageSize=2; //每页条数
		Page<Item> page= itemRepository
			.queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(
					"游戏","罗技", PageRequest.of(pageNum-1,pageSize));
		page.forEach(item -> System.out.println(item));
		// page对象中还包含了一些基本的分页信息
		System.out.println("总页数:"+page.getTotalPages());
		System.out.println("当前页:"+page.getNumber());
		System.out.println("每页条数:"+page.getSize());
		System.out.println("当前页是不是首页:"+page.isFirst());
		System.out.println("当前页是不是末页:"+page.isLast());

	}



}
