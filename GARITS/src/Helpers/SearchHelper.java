/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Entities.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daven
 */
public class SearchHelper {

    /**
     * Searches a List of Items for values that match a String. All values of
     * the Item will be searched. If the value is also an Item (for example, a
     * Job can have a Customer), the sub-Item will also be searched.
     *
     * @param array the List of items to be searched.
     * @param searchStr the search query to find.
     * @return a List of Items that have values that partially match the String.
     * @see Item
     */
    public List<? extends Item> searchItems(List<? extends Item> array, String searchStr) {

        ArrayList<Item> results = new ArrayList<>();
        String[] words = searchStr.split("\\s+");
        array.stream().filter((item) -> (itemMatchesString(item, words))).forEach((item) -> {
            results.add(item);
        });

        return results;
    }

    /**
     * Returns whether the Item contains a value that matches any of the words.
     *
     * @param item the Item to be searched.
     * @param words the words from the search query.
     * @return whether the Item contains a value that matches any of the words.
     * @see Item
     */
    public boolean itemMatchesString(Item item, String[] words) {

        for (String key : item.map().keySet()) {
            Object object = item.map().get(key);
            if (object != null) {

                if (object instanceof Item) {
                    if (itemMatchesString((Item) object, words)) {
                        return true;
                    }
                } else {
                    String str = "" + object;
                    if (stringMatches(str, words)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns whether a String matches any of the words.
     *
     * @param first the first String to be tested.
     * @param words an array of Strings to be matched.
     * @return whether the String matches any of the words.
     */
    public boolean stringMatches(String first, String[] words) {
        for (String word : words) {
            if (first.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}