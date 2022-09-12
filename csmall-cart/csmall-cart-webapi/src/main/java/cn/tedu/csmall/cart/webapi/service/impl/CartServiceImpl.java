package cn.tedu.csmall.cart.webapi.service.impl;


import cn.tedu.csmall.cart.service.ICartService;
import cn.tedu.csmall.cart.webapi.mapper.CartMapper;
import cn.tedu.csmall.commons.pojo.cart.dto.CartAddDTO;
import cn.tedu.csmall.commons.pojo.cart.entity.Cart;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public void cartAdd(CartAddDTO cartAddDTO) {
        // 实例化一个Cart对象
        Cart cart=new Cart();
        // 利用工具类,将cartAddDTO中的属性值赋值到cart对象的同名属性中
        BeanUtils.copyProperties(cartAddDTO,cart);
        // 调用cartMapper对象实现新增功能
        cartMapper.insertCart(cart);
        log.info("新增购物车商品成功!{}",cart);
    }

    @Override
    public void deleteUserCart(String userId, String commodityCode) {
        // 直接调用mapper删除购物车商品的方法即可
        cartMapper.deleteCartByUserIdAndCommodityCode(userId,commodityCode);
        log.info("购物车商品删除成功");
    }
}
