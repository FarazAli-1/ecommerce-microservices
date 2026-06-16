package com.ecommerce.order_service.mapper;


import com.ecommerce.order_service.dto.request.OrderItemsRequestDto;
import com.ecommerce.order_service.model.OrderItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    OrderItems mapToOrderItemsEntity(OrderItemsRequestDto orderItemsRequestDto);

    OrderItemsRequestDto mapToOrderItemsDto(OrderItems orderItems);

}
