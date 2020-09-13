package com.mfuhrmann.assignment.order.view;

import com.mfuhrmann.assignment.user.Email;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
public class GetOrderView {

    private final String id;
    private final Instant orderTimeStamp;
    private final Email email;
    private final List<GetOrderProductView> products;


}
