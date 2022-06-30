package os;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Version implements Comparable<Version> {
	private final int[] parts;
	private final String additional;

	public Version(String version) {
		String[] parts = version.split("[-_]");
		String additional = null;
		if (parts.length > 1) {
			additional = parts[1];
		}
		parts = parts[0].split("[.]");

		this.parts = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
		this.additional = additional;
	}

	@Override
	public int compareTo(Version version) {
		int[] parts = version.getParts();
		int maxLength = Math.max(this.parts.length, parts.length);
		for (int i = 0; i < maxLength; i++) {
			int a = this.parts[i], b = parts[i];
			if (a > b) return 1;
			if (a < b) return -1;
		}
		return 0;
	}

	public int[] getParts() {
		return parts;
	}

	public String getAdditional() {
		return additional;
	}

	@Override
	public String toString() {
		return Arrays.stream(this.parts)
				.mapToObj(String::valueOf)
				.collect(Collectors.joining("."))
				+ (this.additional != null ? "-" + this.additional : "");
	}
}
