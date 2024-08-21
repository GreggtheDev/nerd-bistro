package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.Order;
import com.restaurant.MenuManager; // Ensure this path is correct
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
        mockOrderDao = mock(OrderDao.class);
        mockMenuManager = mock(MenuManager.class);

        orderManager = new OrderManager(mockOrderDao, mockMenuManager);
    }

    @Test
    public void testPlaceOrder() {
        Order order = new Order();
        orderManager.placeOrder(order);
        verify(mockOrderDao, times(1)).addOrder(order);
    }

    @Test
    public void testUpdateOrderStatus() {
        int orderId = 1;
        Order.Status newStatus = Order.Status.COMPLETED;
        orderManager.updateOrderStatus(orderId, newStatus);
        verify(mockOrderDao, times(1)).updateOrderStatus(orderId, newStatus);
    }

    @Test
    public void testGetOrdersByStatus() {
        Order.Status status = Order.Status.PENDING;
        Order order1 = new Order();
        order1.setItemsAsString("item1,item2");
        Order order2 = new Order();
        order2.setItemsAsString("item3,item4");

        when(mockOrderDao.getOrdersByStatus(status)).thenReturn(Arrays.asList(order1, order2));
        List<Order> orders = orderManager.getOrdersByStatus(status);

        assertEquals(2, orders.size());
        assertEquals(Arrays.asList("item1", "item2"), orders.get(0).getItems());
        assertEquals(Arrays.asList("item3", "item4"), orders.get(1).getItems());
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        order1.setItemsAsString("item1,item2");
        Order order2 = new Order();
        order2.setItemsAsString("item3,item4");

        when(mockOrderDao.getAllOrders()).thenReturn(Arrays.asList(order1, order2));
        List<Order> orders = orderManager.getAllOrders();

        assertEquals(2, orders.size());
        assertEquals(Arrays.asList("item1", "item2"), orders.get(0).getItems());
        assertEquals(Arrays.asList("item3", "item4"), orders.get(1).getItems());
    }

    @Test
    public void testDeleteOrder() {
        int orderId = 1;
        orderManager.deleteOrder(orderId);
        verify(mockOrderDao, times(1)).deleteOrder(orderId);
    }
}
