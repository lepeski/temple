package org.bukkit.util;

import java.util.Collection;
import java.util.List;

public final class StringUtil {

    private StringUtil() {
    }

    public static List<String> copyPartialMatches(String token, Collection<String> originals, List<String> collection) {
        String lowerToken = token.toLowerCase();
        for (String original : originals) {
            if (original.toLowerCase().startsWith(lowerToken)) {
                collection.add(original);
            }
        }
        return collection;
    }
}
