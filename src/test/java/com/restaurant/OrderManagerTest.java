package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.Order;
import com.restaurant.model.MenuManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OrderManagerTest {

    private OrderManager orderManager;
    private OrderDao mockOrderDao;
    private MenuManager mockMenuManager;

    @Before
    public void setUp() {
        // Mock the dependencies
        mockOrderDao = mock(OrderDao.class);
        mockMenuManager = mock(MenuManager.class);

        // Inject the mocks into OrderManager
        orderManager = new OrderManager(mockOrderDao, mockMenuManager);
    }

    @Test
    public void testPlaceOrder() {
        Order order = new Order();

        // Act
        orderManager.placeOrder(order);

        // Verify that orderDao.addOrder was called once with the correct order
        verify(mockOrderDao, times(1)).addOrder(order);
    }

    @Test
    public void testUpdateOrderStatus() {
        int orderId = 1;
        Order.Status newStatus = Order.Status.COMPLETED;

        // Act
        orderManager.updateOrderStatus(orderId, newStatus);

        // Verify that orderDao.updateOrderStatus was called with correct arguments
        verify(mockOrderDao, times(1)).updateOrderStatus(orderId, newStatus);
    }

    @Test
    public void testGetOrdersByStatus() {
        Order.Status status = Order.Status.PENDING;
        Order order1 = new Order();
        order1.setItemsAsString("item1,item2");
        Order order2 = new Order();
        order2.setItemsAsString("item3,item4");

        // Mock the behavior of orderDao.getOrdersByStatus
        when(mockOrderDao.getOrdersByStatus(status)).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<Order> orders = orderManager.getOrdersByStatus(status);

        // Verify the items were correctly set from string
        assertEquals(2, orders.size());
        assertEquals(Arrays.asList("item1", "item2"), orders.get(0).getItems());
        assertEquals(Arrays.asList("item3", "item4"), orders.get(1).getItems());

        // Verify that orderDao.getOrdersByStatus was called with correct argument
        verify(mockOrderDao, times(1)).getOrdersByStatus(status);
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        order1.setItemsAsString("item1,item2");
        Order order2 = new Order();
        order2.setItemsAsString("item3,item4");

        // Mock the behavior of orderDao.getAllOrders
        when(mockOrderDao.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<Order> orders = orderManager.getAllOrders();

        // Verify the items were correctly set from string
        assertEquals(2, orders.size());
        assertEquals(Arrays.asList("item1", "item2"), orders.get(0).getItems());
        assertEquals(Arrays.asList("item3", "item4"), orders.get(1).getItems());

        // Verify that orderDao.getAllOrders was called once
        verify(mockOrderDao, times(1)).getAllOrders();
    }

    @Test
    public void testDeleteOrder() {
        int orderId = 1;

        // Act
        orderManager.deleteOrder(orderId);

        // Verify that orderDao.deleteOrder was called once with the correct orderId
        verify(mockOrderDao, times(1)).deleteOrder(orderId);
    }
}
