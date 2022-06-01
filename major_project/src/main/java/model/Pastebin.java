package model;

import model.POJOs.PastebinResult;

/**
 * The interface Pastebin.
 */
public interface Pastebin {
    /**
     * Post content to pastebin and return the pastebin url.
     *
     * @param text the text to post
     * @return the pastebin result stores the url of the pastebin
     */
    PastebinResult createPastin(String text);
}
