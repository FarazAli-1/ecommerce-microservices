package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dto.request.OrderRequestDto;
import com.ecommerce.order_service.dto.response.OrderResponseDto;
import com.ecommerce.order_service.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapToOrderEntity(OrderRequestDto orderRequestDto);

    OrderResponseDto mapToOrderResponseDto(Order order);

}
