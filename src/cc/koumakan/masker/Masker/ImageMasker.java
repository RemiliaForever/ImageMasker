package cc.koumakan.masker.Masker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Remilia Scarlet
 * on 2015/11/17 14:13.
 * <br>
 * 图像掩蔽 RGB依次取Bit
 */
public class ImageMasker {
	public boolean[] content = null;
	private BufferedImage image = null;
	private int kind = 0;
	private Random random;

	/**
	 * 设置算法类型
	 *
	 * @param i 算法类型
	 */
	public void set(int i) {
		kind = i;
		if (kind == 1) random = new Random();
	}

	/**
	 * 从颜色像素中读取信息
	 *
	 * @param color 输入颜色值
	 * @param start 缓冲区偏移
	 */
	private void setContent(Color color, int start) {
		content[start] = (1 == color.getRed() % 2);
		content[start + 1] = (1 == color.getGreen() % 2);
		content[start + 2] = (1 == color.getBlue() % 2);
	}

	/**
	 * 将缓冲区信息写入像素
	 *
	 * @param rgb   原始颜色值
	 * @param start 缓冲区偏移值
	 * @return 载密颜色值
	 */
	private int changeToColor(int rgb, int start) {
		if (content[start]) rgb = rgb | 0x00010000;
		else rgb = rgb & 0xFFFEFFFF;
		if (content[start + 1]) rgb = rgb | 0x00000100;
		else rgb = rgb & 0xFFFFFEFF;
		if (content[start + 2]) rgb = rgb | 0x00000001;
		else rgb = rgb & 0xFFFFFFFE;
		if (kind == 1) {
			if(random.nextBoolean()) rgb = rgb & 0xFFFDFFFF;
			else rgb = rgb | 0x00020000;
			if(random.nextBoolean()) rgb = rgb & 0xFFFFFDFF;
			else rgb = rgb | 0x00000200;
			if(random.nextBoolean()) rgb = rgb & 0xFFFFFFFD;
			else rgb = rgb | 0x00000002;
		}
		return rgb;
	}

	/**
	 * 载入图像文件
	 *
	 * @param file 完整文件路径
	 * @return 是否载入成功
	 */
	public boolean read(String file) {
		try {
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		int height = image.getHeight();
		int width = image.getWidth();
		content = new boolean[height * width * 3];
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				int color = image.getRGB(w, h);
				Color c = new Color(color);
				setContent(c, (h * width + w) * 3);
			}
		}
		return true;
	}

	/**
	 * 根据图像缓存和缓冲区信息输入载密图像
	 *
	 * @param file 目标文件
	 * @return 是否成功
	 */
	public boolean write(String file) {
		if (null == image) return false;

		int height = image.getHeight();
		int width = image.getWidth();

		for (int h = 0; h < height; h++)
			for (int w = 0; w < width; w++)
				image.setRGB(w, h, changeToColor(image.getRGB(w, h), (h * width + w) * 3));
		try {
			String format = file.substring(file.lastIndexOf(".") + 1);
			ImageIO.write(image, format, new File(file));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
