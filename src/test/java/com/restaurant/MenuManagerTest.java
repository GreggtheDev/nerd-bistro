package com.restaurant;

import com.restaurant.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuManagerTest {

    private MenuManager menuManager;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        // Create instances of MenuManager and mock objects
        menuManager = new MenuManager();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock the connect() method in MenuManager to return the mocked connection
        MenuManager spyMenuManager = Mockito.spy(menuManager);
        doReturn(mockConnection).when(spyMenuManager).connect();
        menuManager = spyMenuManager;
    }

    @Test
    void testAddMenuItem() throws SQLException {
        // Create a sample MenuItem object
        MenuItem menuItem = new MenuItem(1, "Pizza", "Delicious cheese pizza", 15, 9.99, Arrays.asList("Cheese", "Tomato Sauce", "Dough"), "Main Course");

        // Mock the behavior of the PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Call the method to test
        menuManager.addMenuItem(menuItem);

        // Verify that the PreparedStatement was set with the correct values
        verify(mockPreparedStatement).setString(1, "Pizza");
        verify(mockPreparedStatement).setString(2, "Delicious cheese pizza");
        verify(mockPreparedStatement).setInt(3, 15);
        verify(mockPreparedStatement).setDouble(4, 9.99);
        verify(mockPreparedStatement).setString(5, "Cheese,Tomato Sauce,Dough");
        verify(mockPreparedStatement).setString(6, "Main Course");

        // Verify that the PreparedStatement executed the SQL command
        verify(mockPreparedStatement).execute();
    }

    @Test
    void testGetMenuItems() throws SQLException {
        // Mock the behavior of the Statement and ResultSet
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Simulate result set behavior
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // First call returns true, second returns false
        when(mockResultSet.getInt("item_id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Pizza");
        when(mockResultSet.getString("description")).thenReturn("Delicious cheese pizza");
        when(mockResultSet.getInt("prep_time")).thenReturn(15);
        when(mockResultSet.getDouble("price")).thenReturn(9.99);
        when(mockResultSet.getString("ingredients")).thenReturn("Cheese,Tomato Sauce,Dough");
        when(mockResultSet.getString("category")).thenReturn("Main Course");

        // Call the method to test
        List<MenuItem> items = menuManager.getMenuItems();

        // Verify the returned list is not null and contains the expected item
        assertNotNull(items);
        assertEquals(1, items.size());
        MenuItem item = items.get(0);
        assertEquals("Pizza", item.getName());
        assertEquals("Delicious cheese pizza", item.getDescription());
        assertEquals(15, item.getPrepTime());
        assertEquals(9.99, item.getPrice());
        assertEquals(Arrays.asList("Cheese", "Tomato Sauce", "Dough"), item.getIngredients());
        assertEquals("Main Course", item.getCategory());
    }

    @Test
    void testRemoveMenuItem() throws SQLException {
        // Mock the behavior of the PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Call the method to test
        menuManager.removeMenuItem("Pizza");

        // Verify that the PreparedStatement was set with the correct value
        verify(mockPreparedStatement).setString(1, "Pizza");

        // Verify that the PreparedStatement executed the SQL command
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetMenuItemById() throws SQLException {
        // Mock the behavior of the PreparedStatement and ResultSet
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("item_id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Pizza");
        when(mockResultSet.getString("description")).thenReturn("Delicious cheese pizza");
        when(mockResultSet.getInt("prep_time")).thenReturn(15);
        when(mockResultSet.getDouble("price")).thenReturn(9.99);
        when(mockResultSet.getString("ingredients")).thenReturn("Cheese,Tomato Sauce,Dough");
        when(mockResultSet.getString("category")).thenReturn("Main Course");

        // Call the method to test
        MenuItem menuItem = menuManager.getMenuItemById(1);

        // Verify that the returned MenuItem is not null and has correct values
        assertNotNull(menuItem);
        assertEquals(1, menuItem.getId());
        assertEquals("Pizza", menuItem.getName());
        assertEquals("Delicious cheese pizza", menuItem.getDescription());
        assertEquals(15, menuItem.getPrepTime());
        assertEquals(9.99, menuItem.getPrice());
        assertEquals(Arrays.asList("Cheese", "Tomato Sauce", "Dough"), menuItem.getIngredients());
        assertEquals("Main Course", menuItem.getCategory());
    }

    @Test
    void testGetMenuItemByName() throws SQLException {
        // Mock the behavior of the PreparedStatement and ResultSet
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("item_id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Pizza");
        when(mockResultSet.getString("description")).thenReturn("Delicious cheese pizza");
        when(mockResultSet.getInt("prep_time")).thenReturn(15);
        when(mockResultSet.getDouble("price")).thenReturn(9.99);
        when(mockResultSet.getString("ingredients")).thenReturn("Cheese,Tomato Sauce,Dough");
        when(mockResultSet.getString("category")).thenReturn("Main Course");

        // Call the method to test
        MenuItem menuItem = menuManager.getMenuItemByName("Pizza");

        // Verify that the returned MenuItem is not null and has correct values
        assertNotNull(menuItem);
        assertEquals(1, menuItem.getId());
        assertEquals("Pizza", menuItem.getName());
        assertEquals("Delicious cheese pizza", menuItem.getDescription());
        assertEquals(15, menuItem.getPrepTime());
        assertEquals(9.99, menuItem.getPrice());
        assertEquals(Arrays.asList("Cheese", "Tomato Sauce", "Dough"), menuItem.getIngredients());
        assertEquals("Main Course", menuItem.getCategory());
    }
}
