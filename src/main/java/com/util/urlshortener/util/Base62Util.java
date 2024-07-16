package com.util.urlshortener.util;

/**
 * Base62 is a numeral system or encoding scheme that uses a set of 62 characters to represent data.
 * It is widely used in various applications, including URL shortening, unique ID generation, and more,
 * due to its efficiency in producing short, readable, and unique identifiers.
 * 
 * Base62 is a form of Base encoding that uses a character set composed of 62 characters:
 *      A-Z (26 uppercase letters)
 *      a-z (26 lowercase letters)
 *      0-9 (10 digits)
 * 
 */
public class Base62Util {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE = ALPHABET.length();

    /*
     * Converts a number into a string representation using the Base62 character set.
     */
    public static String encode(long number, int maxUrlLen) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(ALPHABET.charAt((int) (number % BASE)));
            number /= BASE;
        }

        // Ensure the string is exactly 6 characters long
        while (sb.length() < maxUrlLen) {
            sb.append(ALPHABET.charAt(0));  // Append 'A' if the length is less than 6
        }

        return sb.reverse().toString();
    }

    /*
     * Converts a Base62 string back into a numeric value.
     */
    public static long decode(String str) {
        long number = 0;
        for (char c : str.toCharArray()) {
            number = number * BASE + ALPHABET.indexOf(c);
        }
        return number;
    }
}
