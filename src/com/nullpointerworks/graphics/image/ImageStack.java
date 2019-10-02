package com.nullpointerworks.graphics.image;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.color.ColorFormat;
import com.nullpointerworks.color.Colorizer;
import com.nullpointerworks.graphics.Draw;

public class ImageStack extends IntBuffer
{	
	private final Colorizer ColorRGB = Colorizer.getColorizer(ColorFormat.RGB);
	private int length = 0;
	private int width;
	private int height;
	private IntBuffer[] frames;
	
	public ImageStack(int w, int h) 
	{
		this(w,h,8);
	}
	
	public ImageStack(int w, int h, int length) 
	{
		super(w,h);
		this.clear(ColorRGB.toInt(255, 255, 255));
		frames = new IntBuffer[length];
		width = w;
		height = w;
	}
	
	/**
	 * Register a frame to the stack. does not copy the image
	 */
	public void add(IntBuffer frame)
	{
		// increase array size when size has been reached
		if (length >= frames.length)
		{
			IntBuffer[] buff = new IntBuffer[frames.length + 8];
			
			for (int i=0; i<length; i++) buff[i] = frames[i];
			frames = buff;
			
			// free reference
			for (int i=0; i<length; i++) buff[i] = null;
		}
		
		// store frame reference
		frames[length++] = frame;
	}
	
	/**
	 * Returns a new stack object with frames as reference to the source animation
	 */
	public ImageStack copy()
	{
		ImageStack ani = new ImageStack(width,height,length);
		for (int i=0; i<length; i++)
		{
			ani.add( frames[i] );
		}
		return ani;
	}
	
	/**
	 * Write the image data to the stack buffer
	 */
	public void select(int frame) 
	{
		Draw.image(0, 0, frames[frame % length], this);
	}
	
	/**
	 * Returns a frame as an IntBuffer object
	 */
	public IntBuffer get(int frame)
	{
		return frames[frame % length];
	}
	
	/**
	 * Returns the total amount of frames in this stack
	 */
	public int length() {return length;}
	public int width() {return width;}
	public int height() {return height;}
	
	/**
	 * Free allocated memory buffers for garbage collection
	 */
	public void free()
	{
		for (int i=0; i<length; i++)
		{
			// the frames may already be null if a copied stack already cleaned them up
			if (frames[i]!=null) 
			{
				frames[i].free();
				frames[i] = null;
			}
		}
		frames = null;
	}
}
