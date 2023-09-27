package com.DropOff.app.controller.Courier;

import com.DropOff.app.model.Courier.PackageOrder;
import com.DropOff.app.service.PackageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CourierOrderController {
    @Autowired
    PackageOrderService packageOrderService;

    /**
     * Get all package order for user
     */
    @GetMapping("/package/order/getall")
    public List<PackageOrder> getSenderOrders(){
        return packageOrderService.getSenderOrders();
    }

    /**
     * Get all package order for user
     */
    @GetMapping("package/order/getAllDelivererRouteOrders")
    public List<PackageOrder> getDelivererRouteOrders(@RequestParam int driver_route_id){
        return packageOrderService.getDelivererRouteOrders(Long.valueOf(driver_route_id));
    }

    /**
     * Cancel order
     */
    @PutMapping("/package/order/cancel")
    public String cancelOrder(@RequestBody Map<String,String> req){
        return packageOrderService.cancelOrder(Integer.valueOf(req.get("package_order_id")));
    }

    /**
     * Start order

     */
    @PutMapping("package/order/start")
    public String startOrder(@RequestBody Map<String,String> req){
        Integer pickup_code = Integer.valueOf(req.get("pickup_code"));
        Integer order_id = Integer.valueOf(req.get("order_id"));
        return packageOrderService.startPackageOrder(pickup_code,order_id);
    }

    /**
     * End order
     *

     */
    @PutMapping("package/order/end")
    public String endOrder(@RequestBody Map<String,String> req){
        return packageOrderService.endPackageOrder(Integer.valueOf(req.get("drop_code")),Integer.valueOf(req.get("order_id")));
    }

    /**
     * Record payament of order
     *
     
     */
    @PutMapping("package/order/recordPayment")
    public String recordPayment(@RequestBody Map<String,String> req){
        return packageOrderService.recordPayment(Integer.valueOf(req.get("package_order_id")));
    }

}
