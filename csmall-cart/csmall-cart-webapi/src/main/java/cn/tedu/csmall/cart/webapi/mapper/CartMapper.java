package cn.tedu.csmall.cart.webapi.mapper;


import cn.tedu.csmall.commons.pojo.cart.entity.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMapper {
    // 新增购物车中商品的方法
    @Insert("insert into cart_tbl(commodity_code,user_id,price,count) " +
            "values(#{commodityCode},#{userId},#{price},#{count})")
    void insertCart(Cart cart);

    // 删除购物车中商品的方法
    @Delete("delete from cart_tbl where user_id=#{userId} and commodity_code=#{commodityCode}")
    void deleteCartByUserIdAndCommodityCode(@Param("userId") String userid,
                                            @Param("commodityCode") String commodityCode);

}
