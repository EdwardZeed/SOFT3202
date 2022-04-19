package week3;

public class StorageMock implements Storage {
    private String lastCalledAddElementValue = null;
    private String[] returnValueForGetElements = new String[] {};

    @Override
    public void addElement(String element) {
        lastCalledAddElementValue = element;
    }

    @Override
    public String[] getElements() {
        return returnValueForGetElements;
    }

    public String getLastCalledAddElementValue() {
        return lastCalledAddElementValue;
    }

    public void setReturnValueForGetElements(String[] value) {
        this.returnValueForGetElements = value;
    }
}
