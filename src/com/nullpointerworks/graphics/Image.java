package com.nullpointerworks.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.image.io.ImageLoader;
import com.nullpointerworks.graphics.image.io.ImageSaver;
import com.nullpointerworks.math.IntMath;

public class Image 
{
	/**
	 * 
	 * @since 1.0.0
	 */
	public static final ImageLoader load = new ImageLoader();
	
	/**
	 * 
	 * @since 1.0.0
	 */
	public static final ImageSaver save = new ImageSaver();

	/**
	 * Apply a chroma key to the image. this will make all
	 * pixels which match the key in the image translucent
	 */
	public static void chroma(IntBuffer r, int key) 
	{
		int l 		= r.getLength()-1;
		int[] px 	= r.content();
		key 		= key & 0x00FFFFFF;
		for (;l>=0;l--)
		{
			int c = px[l] & 0x00FFFFFF;
			if (c == key)
			{
				px[l] = c;
			}
		}
	}
	
	/**
	 * Apply an opacity over the entire image. Alpha value range [0-255]
	 */
	public static void opacity(IntBuffer r, int alpha) 
	{
		int l = r.getLength()-1;
		int[] px = r.content();
		for (;l>=0;l--)
		{
			int c = px[l];
			int a = (c>>24) & 0xff;
			a = IntMath.lerp(0, a, alpha, 8);
			c = c & 0x00FFFFFF;
			px[l] = c + a;
		}
	}
	
	/**
	 * replace a color with another color in the given image
	 */
	public static void replace(int colorSrc, int colorDest, IntBuffer image)
	{
		int[] content = image.content();
		for (int i=0, l=content.length; i<l; i++)
		{
			int c = content[i];
			if (c==colorSrc)
				content[i] = colorDest;
		}
		image.plot(content);
	}
	
	/**
	 * Extend the size of the given image with the given margin
	 */
	public static IntBuffer margin(IntBuffer image, int marginW, int marginH, int bgColor)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		int pw = marginW+marginW;
		int ph = marginH+marginH;
		IntBuffer res = new IntBuffer(w+pw,h+ph,bgColor);
		Draw.image(marginW, marginH, image, res);
		return res;
	}
	
	/**
	 * Flip the given image either horizontally or vertically. This will replace the given image
	 */
	public static void flip(IntBuffer image, boolean horizontal)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		IntBuffer dest = new IntBuffer(w,h);
		
		for (int j=0; j<h; j++)
		for (int i=0; i<w; i++)
		{
			int c = image.grab(i, j);
			
			if (horizontal)
				dest.plot(w-i-1, j, c);
			else
				dest.plot(i, h-j-1, c);
		}
		
		Draw.image(0, 0, dest, image);
		dest.free();
		dest=null;
	}
	
	/**
	 * Returns an IntBuffer object that is an extraction of the given image.
	 */
	public static IntBuffer extract(int x, int y, int w, int h, IntBuffer pic) 
	{
		IntBuffer sub = new IntBuffer(w,h);
		for (int j=0; j<h; j++)
		for (int i=0; i<w; i++)
		{
			int c = pic.grab(x+i, y+j);
			sub.plot(i, j, c);
		}
		return sub;
	}
	
	/**
	 * Returns a Core IntBuffer object filled with the content from the given AWT BufferedImage
	 */
	public static IntBuffer fromBufferedImage(BufferedImage bi)
	{
		int w = bi.getWidth();
		int h = bi.getHeight();
		IntBuffer ib = new IntBuffer(w,h);
		int[] px = ((DataBufferInt)bi.getRaster().getDataBuffer()).getData();
		int[] sc = ib.content();
		for (int l = w*h - 1; l>=0; l--) sc[l] = px[l];
		return ib;
	}
	
	/**
	 * Returns a AWT BufferedImage object from the given Core IntBuffer object
	 */
	public static BufferedImage toBufferedImage(IntBuffer ib)
	{
		int w = ib.getWidth();
		int h = ib.getHeight();
		BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		int[] px = ((DataBufferInt)bi.getRaster().getDataBuffer()).getData();
		int[] sc = ib.content();
		for (int l = w*h - 1; l>=0; l--) px[l] = sc[l];
		return bi;
	}
	
	/**
	 * Returns a java.io.InputStream object from the given Core IntBuffer object in PNG format
	 */
	public static InputStream toInputStream(IntBuffer ib)
	{
		BufferedImage bi = toBufferedImage(ib);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
        try 
        {
			javax.imageio.ImageIO.write(bi, "png", os);
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	/**
	 * Returns the integer array content form the given AWT BufferedImage
	 */
	public static int[] getContent(BufferedImage bi)
	{
		return ((DataBufferInt)bi.getRaster().getDataBuffer()).getData();
	}
}
