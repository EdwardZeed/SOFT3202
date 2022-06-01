package model.POJOs;

/**
 * A Plain Old Java Object (POJO) class that represents the pastebin URI.
 * This class records the URI of the pastebin.
 */
public class PastebinResult {
    private String URI;

    /**
     * Instantiates a new Pastebin result.
     *
     * @param URI the URI of the pastebin
     */
    public PastebinResult(String URI) {
        this.URI = URI;
    }

    /**
     * Gets uri.
     *
     * @return the URI of the pastebin
     */
    public String getURI() {
        return URI;
    }
}
