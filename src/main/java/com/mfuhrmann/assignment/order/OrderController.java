package com.mfuhrmann.assignment.order;

import com.mfuhrmann.assignment.order.create.CreateOrderRequest;
import com.mfuhrmann.assignment.order.create.CreateOrderResponse;
import com.mfuhrmann.assignment.order.view.GetOrdersResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Api(tags = "orders")
@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("Create Order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order Created"),
            @ApiResponse(code = 404, message = "Product not Found")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) {

        return orderService.createOrder(request);
    }

    @ApiOperation("Get Orders")
    @ApiResponses(
            @ApiResponse(code = 200, message = "Orders retrieved")
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    GetOrdersResponse getOrdersResponse(@RequestParam Instant from, @RequestParam Instant to) {

        return orderService.getOrders(from, to);
    }


}
