package backend.acornaccounting.backend.util;

import java.text.MessageFormat;

public class PhoneFormatter {
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+1") && phoneNumber.length() == 12) {
            return MessageFormat.format("+1 ({0}) {1} {2} {3}",
                phoneNumber.substring(2, 5),
                phoneNumber.substring(5, 8),
                phoneNumber.substring(8, 10),
                phoneNumber.substring(10, 12));
        }
        throw new IllegalArgumentException("Invalid phone number format");
    }
}

