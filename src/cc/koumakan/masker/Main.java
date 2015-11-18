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
			imageMasker.read(s);
			System.out.print("What do you want to do(r/w):");
			s = bufferedReader.readLine();
			if (s.equals("r")) {
				System.out.print(StringEncoder.decodeString(imageMasker.content).trim());
			} else if (s.equals("w")) {
				System.out.print("Input Data(<=" + imageMasker.content.length / 8 + "):");
				s = bufferedReader.readLine();

				boolean[] value = StringEncoder.encodeString(s);
					for (int i = 0; i < value.length; i++)
					imageMasker.content[i] = value[i];
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
