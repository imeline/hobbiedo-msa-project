package hobbiedo.user.auth.email.util;

import java.security.SecureRandom;
import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomPasswordUtil {
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String DIGITS = "0123456789";
	private static final String SPECIAL = "!@#$%^&*()-_+[]{}|;,.?";
	private static final String ALL_CHARACTERS = UPPER + LOWER + DIGITS + SPECIAL;
	private static final SecureRandom RANDOM = new SecureRandom();

	public static String generate(int length) {
		StringBuilder sb = new StringBuilder(length);
		atLeastOne(sb);

		IntStream.range(4, length)
			.map(i -> ALL_CHARACTERS.charAt(RANDOM.nextInt(ALL_CHARACTERS.length())))
			.forEach(sb::append);

		return shuffleString(sb.toString());
	}

	private static void atLeastOne(StringBuilder sb) {
		sb.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
		sb.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
		sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
		sb.append(SPECIAL.charAt(RANDOM.nextInt(SPECIAL.length())));
	}

	private static String shuffleString(String input) {
		char[] inputArray = input.toCharArray();
		for (int i = 0; i < inputArray.length; i++) {
			int randomIndex = RANDOM.nextInt(inputArray.length);
			swap(inputArray, i, randomIndex);
		}
		return new String(inputArray);
	}

	private static void swap(char[] characters, int i, int randomIndex) {
		char temp = characters[i];
		characters[i] = characters[randomIndex];
		characters[randomIndex] = temp;
	}
}
