/*
 * Creative Commons - Attribution, Share Alike 4.0 
 * Nullpointer Works (2021)
 * Using this library makes you subject to the license terms.
 * 
 * https://creativecommons.org/licenses/by-sa/4.0/
 */
package com.nullpointerworks.graphics.image;

import com.nullpointerworks.core.buffer.IntBuffer;

public class Animation extends ImageStack
{
	private int frame = 0;
	
	public Animation(int w, int h, int length) 
	{
		super(w, h, length);
	}
	
	public Animation(ImageStack stack)
	{
		super(stack.width(), stack.height(), stack.length());
		for (int i=0,l=stack.length(); i<l; i++)
		{
			add(stack.get(i));
		}
	}
	
	/**
	 * returns the current frame number in the animation
	 */
	public int frame() {return frame;}
	
	/**
	 * register a frame to the animation. does not copy the image
	 */
	public void add(IntBuffer frame)
	{
		super.add(frame);
	}
	
	/**
	 * load next frame for drawing
	 */
	public void next() 
	{
		frame = (frame+1) % length();
		select(frame);
	}
	
	/**
	 * move the animation to a specific frame number
	 */
	public void jump(int f) 
	{
		frame = f % length();
		select(frame);
	}
	
	/**
	 * returns a new Animation object with frames as reference to the source animation
	 */
	public Animation copy()
	{
		return new Animation(this);
	}
}
