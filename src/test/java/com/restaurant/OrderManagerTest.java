package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.Order;
import com.restaurant.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderManagerTest {

    private OrderManager orderManager;
    private OrderDao mockOrderDao;
    private MenuManager mockMenuManager;

    @BeforeEach
    void setUp() {
        // Mock the dependencies
        mockOrderDao = mock(OrderDao.class);
        mockMenuManager = mock(MenuManager.class);

        // Use a spy for OrderManager so we can inject the mocks
        orderManager = Mockito.spy(new OrderManager());

        // Inject the mocks into the spy
        Mockito.doReturn(mockOrderDao).when(orderManager).orderDao;
        Mockito.doReturn(mockMenuManager).when(orderManager).menuManager;
    }

    @Test
    void testPlaceOrder() {
        Order order = new Order();
        orderManager.placeOrder(order);

        // Verify that addOrder was called with the correct order
        verify(mockOrderDao).addOrder(order);
    }

    @Test
    void testUpdateOrderStatus() {
        int orderId = 1;
        Order.Status status = Order.Status.COMPLETED;

        orderManager.updateOrderStatus(orderId, status);

        // Verify that updateOrderStatus was called with the correct arguments
        verify(mockOrderDao).updateOrderStatus(orderId, status);
    }

    @Test
    void testGetOrdersByStatus() {
        Order.Status status = Order.Status.PENDING;
        Order order = new Order();
        order.setItemsAsString("Pizza,Burger");

        when(mockOrderDao.getOrdersByStatus(status)).thenReturn(Arrays.asList(order));

        List<Order> orders = orderManager.getOrdersByStatus(status);

        // Verify that getOrdersByStatus was called and items were set correctly
        verify(mockOrderDao).getOrdersByStatus(status);
        assertEquals(1, orders.size());
        assertEquals("Pizza,Burger", orders.get(0).getItemsAsString());
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setItemsAsString("Pizza,Burger");

        Order order2 = new Order();
        order2.setItemsAsString("Pasta,Salad");

        when(mockOrderDao.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderManager.getAllOrders();

        // Verify that getAllOrders was called and items were set correctly
        verify(mockOrderDao).getAllOrders();
        assertEquals(2, orders.size());
        assertEquals("Pizza,Burger", orders.get(0).getItemsAsString());
        assertEquals("Pasta,Salad", orders.get(1).getItemsAsString());
    }

    @Test
    void testDeleteOrder() {
        int orderId = 1;

        orderManager.deleteOrder(orderId);

        // Verify that deleteOrder was called with the correct order ID
        verify(mockOrderDao).deleteOrder(orderId);
    }
}

