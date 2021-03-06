/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.graphics.image.io;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.util.Log;

public class ImageLoader
{
	/**
	 * read a file into a texture
	 * returns an IntBuffer
	 */
	public IntBuffer file(String path)
	{
		InputStream is = null;
		try 
		{
			File f = new File(path);
			is = new DataInputStream(new FileInputStream(f));
		}
		catch (IOException e) 
		{
			Log.err("Could not load from path - ImageLoader.file()");
			return new IntBuffer(0,0);
		}
		
		IntBuffer res = stream(is);
		
		try 
		{
			is.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return  res;
	}
	
	/**
	 * 
	 */
	public IntBuffer stream(InputStream stream)
	{
		BufferedImage image = null;
		if (stream!=null)
		try 
		{
			image = ImageIO.read( stream );
		} 
		catch (IOException e) 
		{
			Log.err("Could not load from inputstream - ImageLoader.stream()");
			close(stream);
			return new IntBuffer(0,0);
		}
		finally 
		{
			close(stream);
		}
		
		if (image==null)
		{
			Log.err("InputStream error from reading inputstream - ImageLoader.stream()");
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
