package mvc.views.utility;

import java.time.Instant;
import java.util.Random;

public class IDGenerator {
    public static String generateShortId() {
        String timestampHex = Long.toHexString(Instant.now().getEpochSecond());
        String randomPart = getRandomAlphanumeric(3);
        return timestampHex + randomPart;
    }

    public static String generateRoomId() {
        String timestampHex = Long.toHexString(Instant.now().getEpochSecond());
        String randomPart = getRandomAlphanumeric(5);
        return timestampHex + randomPart;
    }

    private static String getRandomAlphanumeric(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }

        return randomString.toString();
    }

}
