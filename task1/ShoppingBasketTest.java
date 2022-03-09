package au.edu.sydney.soft3202.task1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ShoppingBasketTest {

    @Test
    public void testAddItem() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("apple", 1);
        assertEquals(1, basket.getItems().size());
        assertEquals("apple", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());

        basket.addItem("orange", 1);
        assertEquals(2, basket.getItems().size());
        assertEquals("orange", basket.getItems().get(1).getKey());
        assertEquals(1, basket.getItems().get(1).getValue());

        basket.addItem("pear", 1);
        assertEquals(3, basket.getItems().size());
        assertEquals("pear", basket.getItems().get(2).getKey());
        assertEquals(1, basket.getItems().get(2).getValue());

        basket.addItem("banana", 1);
        assertEquals(4, basket.getItems().size());
        assertEquals("banana", basket.getItems().get(3).getKey());
        assertEquals(1, basket.getItems().get(3).getValue());
    }

    @Test
    public void testAddItemFail(){
        ShoppingBasket basket = new ShoppingBasket();
        assertThrows(IllegalArgumentException.class, () -> basket.addItem("apple", 0));

        assertThrows(IllegalArgumentException.class, () -> basket.addItem(null, 1));

        assertThrows(IllegalArgumentException.class, () -> basket.addItem("1", 1));
    }

    @Test
    public void testRemoveItem() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("apple", 1);
        basket.addItem("orange", 1);
        basket.addItem("pear", 1);
        basket.addItem("banana", 1);

        boolean test1 = basket.removeItem("apple", 1);
        assertTrue(test1);
        assertEquals(3, basket.getItems().size());
        assertEquals("orange", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());
        assertEquals("pear", basket.getItems().get(1).getKey());
        assertEquals(1, basket.getItems().get(1).getValue());
        assertEquals("banana", basket.getItems().get(2).getKey());
        assertEquals(1, basket.getItems().get(2).getValue());

        boolean test2 = basket.removeItem("orange", 1);
        assertTrue(test2);
        assertEquals(2, basket.getItems().size());
        assertEquals("pear", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());
        assertEquals("banana", basket.getItems().get(1).getKey());
        assertEquals(1, basket.getItems().get(1).getValue());

        boolean test3 = basket.removeItem("apple", 1);
        assertFalse(test3);
        assertEquals(2, basket.getItems().size());
        assertEquals("pear", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());
        assertEquals("banana", basket.getItems().get(1).getKey());
        assertEquals(1, basket.getItems().get(1).getValue());

        boolean test4 = basket.removeItem("banana", 3);
        assertFalse(test4);
        assertEquals(2, basket.getItems().size());
        assertEquals("pear", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());
        assertEquals("banana", basket.getItems().get(1).getKey());
        assertEquals(1, basket.getItems().get(1).getValue());
    }

    @Test
    public void testRemoveItemFail(){
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> basket.removeItem(null, 1));


    }

    @Test
    public void testGetItems() {
        ShoppingBasket basket = new ShoppingBasket();
        assertNull(basket.getItems());

        basket.addItem("apple", 1);
        assertEquals(1, basket.getItems().size());
        assertEquals("apple", basket.getItems().get(0).getKey());
        assertEquals(1, basket.getItems().get(0).getValue());

    }

    @Test
    public void testGetValue(){
        ShoppingBasket basket = new ShoppingBasket();

        assertNull(basket.getValue());

        basket.addItem("apple", 1);
        basket.addItem("orange", 1);
        basket.addItem("pear", 1);
        basket.addItem("banana", 1);

        assertEquals(11.7, basket.getValue());
    }

    @Test
    public void testClear(){
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("apple", 1);
        basket.addItem("orange", 1);
        assertEquals(2, basket.getItems().size());
        
        basket.clear();
        assertEquals(0, basket.getItems().size());
    }
}
