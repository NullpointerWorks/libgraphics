package com.nullpointerworks.graphics.image.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.util.Log;

public class ImageLoader
{
	/*
	 * read a file into a texture
	 * returns an ImageTexture
	 */
	public IntBuffer file(String path)
	{
		BufferedImage image = null;
		
		// try to load image
		try 
		{
			image = ImageIO.read(new File(path));
		}
		catch (IOException e) 
		{
			Log.err("Could not load image '"+path+"'. - ImageLoader.file()");
			return new IntBuffer(0,0);
		}
		finally 
		{}
		
		return convert(image);
	}
	
	/*
	 * 
	 */
	public IntBuffer resource(String directory)
	{
		// check first char for a '/'
		char c = directory.charAt(0);
		if (c != '/')
			directory = "/"+directory;
		
		// begin loading image
		BufferedImage image = null;
		InputStream stream = this.getClass().getResourceAsStream(directory);
		if (stream!=null)
		try 
		{
			image = ImageIO.read( stream );
		} 
		catch (IOException e) 
		{
			Log.err("Could not load resource '"+directory+"'. - ImageLoader.resource()");
			close(stream);
			return new IntBuffer(0,0);
		}
		finally 
		{
			close(stream);
		}
		
		if (image==null)
		{
			Log.err("InputStream error from reading '"+directory+"'. - ImageLoader.resource()");
			return new IntBuffer(0,0);
		}
		return convert(image);
	}
	
	/*
	 * convert BufferedImage to buffer
	 */
	private IntBuffer convert(BufferedImage image)
	{
		int width, height;
		width = image.getWidth();
		height = image.getHeight();
		int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
		IntBuffer tex = new IntBuffer(width,height);
		tex.plot(pixels);
		return tex;
	}
	
	/*
	 * encapsulate closing the stream
	 */
	private void close(InputStream stream)
	{
		try 
		{
			stream.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
