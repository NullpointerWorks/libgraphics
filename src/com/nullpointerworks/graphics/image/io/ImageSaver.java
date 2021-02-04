/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics.image.io;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.nullpointerworks.core.buffer.IntBuffer;

public class ImageSaver 
{
	/**
	 * @throws IOException 
	 * Save an IntBuffer to BMP file.
	 */
	public void file(IntBuffer img, String path) throws IOException
	{
	    int width 	= img.getWidth();
	    int height 	= img.getHeight();
		
		BufferedImage pic = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    WritableRaster out = pic.getRaster();
	    int[] pixel = new int[3];
	    
        for (int y = 0; y < height; y++) 
	    {
        	for (int x = 0; x < width; x++) 
	        {
        		int c = img.grab(x, y);
        		pixel[0] = (c>>16) & 0xFF;
        		pixel[1] = (c>>8) & 0xFF;
        		pixel[2] = (c) & 0xFF;
        		out.setPixel(x, y, pixel);
	        }
	    }
	    ImageIO.write(pic, "bmp", new File(path));
	}
}
