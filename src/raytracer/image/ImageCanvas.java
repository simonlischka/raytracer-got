package raytracer.image;
/**
 * This immutable class represents the canvas for a diagonal line on black background.
 * It is initialized with size parameters. 
 * 
 * The class includes a paint method which is called to display the generated image.
 * It also includes methods to change its size and retrieve the embedded BufferedImage object.
 * 
 * @author Simon Lischka
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public final class ImageCanvas extends Canvas {
	private Dimension size;
	private BufferedImage image;
/**
 * Initializes the BufferedImage object with the size parameters specified methods parameters.
 * Sets the ColorModel to TYPE_INT_RGB.
 * 
 * @param width
 * @param height
 */
	public ImageCanvas(Dimension size) {
		this.size = size;
		image = new BufferedImage((int)size.getWidth(), (int)size.getHeight(),
				BufferedImage.TYPE_INT_RGB);
	}

/**
 * Initializes the BufferedImage object with the size parameters specified methods parameters.
 * Sets the ColorModel to TYPE_INT_RGB.
 * 
 * @param width
 * @param height
 */
	public ImageCanvas(int width, int height) {
		this(new Dimension(width, height));
	}

	public BufferedImage getImage() {
		return image;
	}

/**
 * Changes the size of the ImageCanvas object by modifying the classes
 * size parameter and recreating the BufferedImage-object.
 * @param size
 */
	public void setSize(Dimension size) {
		this.size = size;
		image = new BufferedImage((int)size.getWidth(), (int)size.getHeight(),
				BufferedImage.TYPE_INT_RGB);
	}

/**
 * Generates a black background and red diagonal line. The data is 
 * written into the raster of the classes BufferedImage and then
 * painted.
 * 
 * @param g
 */
	public void paint(final Graphics g) {
		super.paint(g);
		final WritableRaster raster = image.getRaster();
		final ColorModel colorModel = image.getColorModel();
		Object dataRed = colorModel.getDataElements(Color.red.getRGB(), null);
		Object dataBlack = colorModel.getDataElements(Color.black.getRGB(),
				null);
		for (int x = 0; x < size.width; x++) {
			for (int y = 0; y < size.height; y++) {
				raster.setDataElements(x, y, dataBlack);
			}
		}
		int x = 0;
		for (int y = 0; y < size.height; y++) {
			raster.setDataElements(x, y, dataRed);
			x++;
			if (x >= size.width) {
				break;
			}
		}
		g.drawImage(image, 0, 0, null);
	}
}