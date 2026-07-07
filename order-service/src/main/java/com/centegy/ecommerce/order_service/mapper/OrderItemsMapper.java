package com.centegy.ecommerce.order_service.mapper;


import com.centegy.ecommerce.order_service.dto.request.OrderItemsRequestDto;
import com.centegy.ecommerce.order_service.dto.response.OrderItemsResponseDto;
import com.centegy.ecommerce.order_service.model.OrderItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    OrderItems mapToOrderItemsEntity(OrderItemsRequestDto orderItemsRequestDto);

    OrderItemsResponseDto mapToOrderItemsDto(OrderItems orderItems);

}
