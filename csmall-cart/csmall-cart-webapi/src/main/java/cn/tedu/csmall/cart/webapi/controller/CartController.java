package cn.tedu.csmall.cart.webapi.controller;

import cn.tedu.csmall.cart.service.ICartService;
import cn.tedu.csmall.commons.pojo.cart.dto.CartAddDTO;
import cn.tedu.csmall.commons.restful.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base/cart")
@Api(tags="购物车模块")
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping("/add")
    @ApiOperation("新增购物车商品")
    public JsonResult cartAdd(CartAddDTO cartAddDTO){
        cartService.cartAdd(cartAddDTO);
        return JsonResult.ok("新增购物车商品完成");
    }

    @GetMapping("/delete")
    @ApiOperation("删除购物车中商品")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户id",name="userId",
                    example = "UU100",required = true),
            @ApiImplicitParam(value = "商品编号",name="commodityCode",
                    example = "PC100",required = true)
    })
    public JsonResult deleteUserCart(String userId,String commodityCode){
        // 调用业务逻辑层删除购物车商品的方法
        cartService.deleteUserCart(userId,commodityCode);
        return JsonResult.ok("删除购物车商品成功");
    }




}
