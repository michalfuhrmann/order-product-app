package com.mfuhrmann.assignment.order.view;

import com.mfuhrmann.assignment.order.OrderDocument;
import com.mfuhrmann.assignment.product.ProductDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class GetOrdersResponse {

    private final List<GetOrderView> orders;
    private final BigDecimal total;

    public GetOrdersResponse(List<OrderDocument> orders) {
        this.orders = orders.stream()
                .map(GetOrdersResponse::mapToView)
                .collect(Collectors.toList());

        this.total = orders.stream()
                .flatMap(orderDocument -> orderDocument.getProductDocuments().stream())
                .map(ProductDocument::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static GetOrderView mapToView(OrderDocument order) {
        return new GetOrderView(order.getId(),
                order.getOrderTimeStamp(),
                order.getEmail(),
                getOrderProductViews(order.getProductDocuments()));
    }

    private static List<GetOrderProductView> getOrderProductViews(List<ProductDocument> productDocuments) {
        return productDocuments.stream()
                .map(GetOrderProductView::new)
                .collect(Collectors.toList());
    }

}
