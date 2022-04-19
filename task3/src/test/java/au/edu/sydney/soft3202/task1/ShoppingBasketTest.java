package au.edu.sydney.soft3202.task1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

public class ShoppingBasketTest {

    @Test
    public void testAddItemThrowsIllegalArgumentOnEmptyItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.addItem("", 1));
        assertTrue(shoppingBasket.getItems().isEmpty());
    }

    @Test
    public void testAddItemThrowsIllegalArgumentOnUnknownItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.addItem("Carrots", 1));
        assertTrue(shoppingBasket.getItems().isEmpty());
    }

    @Test
    public void testAddItemThrowsIllegalArgumentOnZeroItemCount() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.addItem("apples", 0));
        assertTrue(shoppingBasket.getItems().isEmpty());
    }

    @Test
    public void testAddItemApplesInvalidItemStringThrowsIllegalArgument() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.addItem("apples apples", 1));
        assertTrue(shoppingBasket.getItems().isEmpty());
    }

    @Test
    public void testAddItemOnTrimItemStringThrowsIllegalArgument() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.addItem("apples ", 2));
        assertTrue(shoppingBasket.getItems().isEmpty());
    }

    @Test
    public void testShoppingBasketBeginsWithEmptyList() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        List<Pair<String, Integer>> result = shoppingBasket.getItems();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testShoppingBasketBeginsWithNullGetValue() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        assertEquals(null, shoppingBasket.getValue());
    }

    @Test
    public void testAddAppleItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("apples", 1);
        assertEquals(new Pair<String, Integer>("apples", 1), shoppingBasket.getItems().get(0));
    }

    @Test
    public void testAddItemOnMaxIntValue() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        int value = Integer.MAX_VALUE;
        shoppingBasket.addItem("bananas", value);
        List<Pair<String, Integer>> result = shoppingBasket.getItems();
        assertEquals(new Pair<String, Integer>("bananas", value), result.get(0));
    }

    @Test
    public void testAddValidItemCount() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("apples", 1);
        shoppingBasket.addItem("bananas", 2);
        shoppingBasket.addItem("pears", 4);
        shoppingBasket.addItem("oranges", 2);
        List<Pair<String, Integer>> result = shoppingBasket.getItems();
        assertEquals(4, result.size());
    }

    @Test
    public void testAddItemOnDiffertItems() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("apples", 1);
        shoppingBasket.addItem("pears", 2);
        shoppingBasket.addItem("bananas", 4);
        shoppingBasket.addItem("oranges", Integer.MAX_VALUE);
        List<Pair<String, Integer>> result = shoppingBasket.getItems();
        assertEquals(new Pair<String, Integer>("apples", 1), result.get(0));
        assertEquals(new Pair<String, Integer>("pears", 2), result.get(1));
        assertEquals(new Pair<String, Integer>("bananas", 4), result.get(2));
        assertEquals(new Pair<String, Integer>("oranges", Integer.MAX_VALUE), result.get(3));
    }

    @Test
    public void testAddItemIsCaseInsensitive() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("APPLES", 1);
        List<Pair<String, Integer>> result = shoppingBasket.getItems();
        Pair<String, Integer> item = result.get(0);
        assertEquals("apples", item.getKey());
    }

    @Test
    public void testRemoveItemThrowsIllegalArgumentOnEmptyItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("oranges", 2);
        assertEquals(1, shoppingBasket.getItems().size());
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.removeItem("", 1));
        assertEquals(1, shoppingBasket.getItems().size());
        assertEquals("oranges", shoppingBasket.getItems().get(0).getKey());
        assertEquals(2, shoppingBasket.getItems().get(0).getValue().intValue());
    }

    @Test
    public void testRemoveItemThrowsIllegalArgumentOnUnknownItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("oranges", 2);
        assertEquals(1, shoppingBasket.getItems().size());
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.removeItem("oanges", 1));
        assertEquals(1, shoppingBasket.getItems().size());
        assertEquals("oranges", shoppingBasket.getItems().get(0).getKey());
        assertEquals(2, shoppingBasket.getItems().get(0).getValue().intValue());
    }

    @Test
    public void testRemoveItemThrowsIllegalArgumentOnZeroItemCount() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("oranges", 2);
        assertEquals(1, shoppingBasket.getItems().size());
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> shoppingBasket.removeItem("oranges", 0));
        assertEquals(1, shoppingBasket.getItems().size());
        assertEquals("oranges", shoppingBasket.getItems().get(0).getKey());
        assertEquals(2, shoppingBasket.getItems().get(0).getValue().intValue());
    }

    @Test
    public void testRemoveItemReturnsNullOnEmptyList() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        boolean result = shoppingBasket.removeItem("apples", 1);
        assertFalse(result);
    }

    @Test
    public void testRemoveItemReturnsFalseOnUnMatchingItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("bananas", 3);
        shoppingBasket.addItem("pears", 3);
        boolean result = shoppingBasket.removeItem("oranges", 3);
        assertFalse(result);
    }

    @Test
    public void testRemoveItemReturnsFalseOnCountHigherThanAmountOfItemCurrentlyPresent() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("bananas", 3);
        shoppingBasket.addItem("pears", 3);
        boolean result = shoppingBasket.removeItem("pears", 4);
        assertFalse(result);
    }

    @Test
    public void testRemoveItemReturnsOnCountLowerThanAmountOfItemCurrentlyPresent() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("bananas", 3);
        shoppingBasket.addItem("pears", 3);
        boolean result = shoppingBasket.removeItem("pears", 1);
        assertTrue(result);
    }

    @Test
    public void testRemoveItemIsCaseInsensitive() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("oranges", 2);
        shoppingBasket.addItem("bananas", 3);
        boolean result = shoppingBasket.removeItem("OrAnGes", 2);
        assertTrue(result);
        assertEquals(1, shoppingBasket.getItems().size());
        assertEquals("bananas", shoppingBasket.getItems().get(0).getKey());
    }

    @Test
    public void testRemoveItemValidCaseDeductsOneItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        assertEquals(0, shoppingBasket.getItems().size());
        shoppingBasket.addItem("oranges", 2);
        shoppingBasket.addItem("pears", 4);
        shoppingBasket.addItem("oranges", 2);
        assertEquals(3, shoppingBasket.getItems().size());
        boolean result = shoppingBasket.removeItem("oranges", 2);
        assertTrue(result);
        assertEquals(2, shoppingBasket.getItems().size());
    }

    @Test
    public void testRemoveItemValidCaseRemovesMatchItem() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("oranges", 2);
        shoppingBasket.addItem("pears", 4);
        shoppingBasket.addItem("oranges", 2);
        boolean result = shoppingBasket.removeItem("oranges", 2);
        assertTrue(result);
        assertEquals(new Pair<String, Integer>("pears", 4), shoppingBasket.getItems().get(0));
        assertEquals(new Pair<String, Integer>("oranges", 2), shoppingBasket.getItems().get(1));
    }

    @Test
    public void testGetItemsReturnsListOfItems() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("apples", 1);
        shoppingBasket.addItem("pears", 2);
        shoppingBasket.addItem("oranges", 5);
        shoppingBasket.addItem("apples", 3);
        List<Pair<String, Integer>> result = shoppingBasket.getItems();
        Pair<String, Integer> one = new Pair<String,Integer>("apples", 1);
        Pair<String, Integer> two = new Pair<String,Integer>("pears", 2);
        Pair<String, Integer> three = new Pair<String,Integer>("oranges", 5);
        Pair<String, Integer> four = new Pair<String,Integer>("apples", 3);
        List<Pair<String, Integer>> expected = Arrays.asList(one, two, three, four);
        assertEquals(expected, result);
    }

    @Test
    public void testGetItemsReturnsCopyAnyModificationsWillNotModifyExistingBasket() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("pears", 2);
        List<Pair<String, Integer>> result = shoppingBasket.getItems();
        Pair<String, Integer> item = new Pair<String,Integer>("oranges", 5);
        result.add(item);
        List<Pair<String, Integer>> expected = shoppingBasket.getItems();
        assertNotEquals(expected, result);
    }

    @Test
    public void testGetValueReturnsNullOnEmptyBasket() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        assertEquals(0, shoppingBasket.getItems().size());
        Double result = shoppingBasket.getValue();
        assertNull(result);
    }

    @Test
    public void testGetValueForApples() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("apples", 3);
        Double expected = 2.50 * 3;
        assertEquals(expected, shoppingBasket.getValue());
    }

    @Test
    public void testGetValueForOranges() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("oranges", 2);
        Double expected = 1.25 * 2;
        assertEquals(expected, shoppingBasket.getValue());
    }

    @Test
    public void testGetValueForPears() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("pears", 1);
        Double expected = 3.00 * 1;
        assertEquals(expected, shoppingBasket.getValue());
    }

    @Test
    public void testGetValueForBananas(){
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("bananas", 5);
        Double expected = 4.95 * 5;
        assertEquals(expected, shoppingBasket.getValue());
    }

    @Test
    public void testGetValueOnDifferentFruit() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("pears", 5);
        shoppingBasket.addItem("oranges", 2);
        shoppingBasket.addItem("pears", 1);
        shoppingBasket.addItem("apples", 1);
        shoppingBasket.addItem("bananas", 2);
        Double expected = 32.90;
        assertEquals(expected, shoppingBasket.getValue());
    }

    @Test
    public void testGetValueOnRemovingItems() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("bananas", 5);
        shoppingBasket.addItem("pears", 5);
        shoppingBasket.removeItem("bananas", 5);
        Double expected = 15.00;
        assertEquals(expected, shoppingBasket.getValue());
    }

    @Test
    public void testGetValueOnRemovingAllItems() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("pears", 5);
        shoppingBasket.removeItem("pears", 5);
        assertNull(shoppingBasket.getValue());
    }

    @Test
    public void testClearRemovesAllItemsReturnsEmptyList(){
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        assertTrue(shoppingBasket.getItems().isEmpty());
        shoppingBasket.addItem("pears", 2);
        shoppingBasket.addItem("oranges", 5);
        assertEquals(2, shoppingBasket.getItems().size());
        shoppingBasket.clear();
        assertTrue(shoppingBasket.getItems().isEmpty());
    }

    @Test
    public void testClearResultsInNullGetValue() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addItem("pears", 2);
        shoppingBasket.addItem("oranges", 5);
        Double expected = 12.25;
        assertEquals(expected, shoppingBasket.getValue());
        shoppingBasket.clear();
        assertEquals(null, shoppingBasket.getValue());
    }

}
