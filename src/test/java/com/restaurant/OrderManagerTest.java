package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrderManagerTest {

    private OrderManager orderManager;
    private OrderDao orderDaoMock;
    private MenuManager menuManagerMock;

    @Before
    public void setUp() {
        // Create mock instances of OrderDao and MenuManager
        orderDaoMock = Mockito.mock(OrderDao.class);
        menuManagerMock = Mockito.mock(MenuManager.class);

        // Inject the mocks into the OrderManager instance
        orderManager = new OrderManager(orderDaoMock, menuManagerMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlaceOrder_NullOrder() {
        // Act
        orderManager.placeOrder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlaceOrder_EmptyOrderItems() {
        // Arrange
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        when(order.getItems()).thenReturn(new ArrayList<>());

        // Act
        orderManager.placeOrder(order);
    }

    @Test
    public void testPlaceOrder_ValidOrder() {
        // Arrange
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        List<MenuItem> items = new ArrayList<>();
        MenuItem menuItem = Mockito.mock(MenuItem.class); // Mocking the MenuItem object
        items.add(menuItem);
        when(order.getItems()).thenReturn(items);

        // Act
        orderManager.placeOrder(order);

        // Assert
        verify(orderDaoMock, times(1)).addOrder(order);
    }

    @Test
    public void testUpdateOrderStatus() {
        // Arrange
        int orderId = 1;
        Order.Status status = Order.Status.FINISHED;

        // Act
        orderManager.updateOrderStatus(orderId, status);

        // Assert
        verify(orderDaoMock, times(1)).updateOrderStatus(orderId, status);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateOrder_NullOrder() {
        // Act
        orderManager.updateOrder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateOrder_EmptyOrderItems() {
        // Arrange
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        when(order.getItems()).thenReturn(new ArrayList<>());

        // Act
        orderManager.updateOrder(order);
    }

    @Test
    public void testUpdateOrder_ValidOrder() {
        // Arrange
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        List<MenuItem> items = new ArrayList<>();
        MenuItem menuItem = Mockito.mock(MenuItem.class); // Mocking the MenuItem object
        items.add(menuItem);
        when(order.getItems()).thenReturn(items);

        // Act
        orderManager.updateOrder(order);

        // Assert
        verify(orderDaoMock, times(1)).updateOrder(order);
    }

    @Test
    public void testGetOrdersByStatus() {
        // Arrange
        Order.Status status = Order.Status.FINISHED;
        List<Order> orders = new ArrayList<>();
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        when(order.getItems()).thenReturn(new ArrayList<>());
        orders.add(order);

        when(orderDaoMock.getOrdersByStatus(status)).thenReturn(orders);

        // Act
        List<Order> result = orderManager.getOrdersByStatus(status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        when(order.getItems()).thenReturn(new ArrayList<>());
        orders.add(order);

        when(orderDaoMock.getAllOrders()).thenReturn(orders);

        // Act
        List<Order> result = orderManager.getAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteOrder() {
        // Arrange
        int orderId = 1;

        // Act
        orderManager.deleteOrder(orderId);

        // Assert
        verify(orderDaoMock, times(1)).deleteOrder(orderId);
    }

    @Test
    public void testGetCompletedOrderDetails() {
        // Arrange
        List<Order> completedOrders = new ArrayList<>();
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        when(order.getOrderId()).thenReturn(1);
        when(order.getTableId()).thenReturn(101);
        when(order.getItems()).thenReturn(new ArrayList<>());
        when(order.getTotalPrice()).thenReturn(11.98);
        completedOrders.add(order);

        when(orderDaoMock.getOrdersByStatus(Order.Status.FINISHED)).thenReturn(completedOrders);

        // Act
        List<String> orderDetails = orderManager.getCompletedOrderDetails();

        // Assert
        assertNotNull(orderDetails);
        assertEquals(1, orderDetails.size());
        assertTrue(orderDetails.get(0).contains("Order ID: 1"));
        assertTrue(orderDetails.get(0).contains("Table ID: 101"));
    }

    @Test
    public void testGetOrdersByTable() {
        // Arrange
        int tableId = 101;
        List<Order> orders = new ArrayList<>();
        Order order = Mockito.mock(Order.class); // Mocking the Order object
        when(order.getItems()).thenReturn(new ArrayList<>());
        orders.add(order);

        when(orderDaoMock.getOrdersByTable(tableId)).thenReturn(orders);

        // Act
        List<Order> result = orderManager.getOrdersByTable(tableId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
