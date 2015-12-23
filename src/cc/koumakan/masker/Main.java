package cc.koumakan.masker;

import cc.koumakan.masker.Encoder.StringEncoder;
import cc.koumakan.masker.Masker.ImageMasker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Remilia Scarlet
 * on 2015/11/17 14:43.
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("ImageMasker - Version 0.0.1");
		System.out.println("Not Encrypted Image Information Hiding Algorithms");
		System.out.println("PS:Please don't use jpg to hide data.Png is better!\n");

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		ImageMasker imageMasker = new ImageMasker();
		try {
			System.out.print("Source File:");
			String s = bufferedReader.readLine();
			if (!imageMasker.read(s)) return;
			System.out.print("What do you want to do(r/w):");
			s = bufferedReader.readLine();
			if (s.equals("r")) {
				System.out.print(StringEncoder.decodeString(imageMasker.content).trim() + "\n");
			} else if (s.equals("w")) {
				System.out.print("0:least significant bit\n1:????\nSelect the algorithm:");
				s = bufferedReader.readLine();
				switch (s) {
					case "0":
						imageMasker.set(0);
						break;
					case "1":
						imageMasker.set(1);
						break;
					default:
						imageMasker.set(1);
						System.out.print("Change to [1].\n");
				}
				System.out.print("Input Data(<=" + imageMasker.content.length / 8 + "):");
				s = bufferedReader.readLine();

				boolean[] value = StringEncoder.encodeString(s);
				System.arraycopy(value, 0, imageMasker.content, 0, value.length);
				for (int i = value.length; i < imageMasker.content.length; i++) {
					imageMasker.content[i] = false;
				}

				System.out.print("Target File:");
				s = bufferedReader.readLine();
				imageMasker.write(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
