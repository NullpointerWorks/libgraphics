/*
 * This is free and unencumbered software released into the public domain.
 * (http://unlicense.org/)
 * Nullpointer Works (2021)
 */
package com.nullpointerworks.graphics.image;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.color.ColorFormat;
import com.nullpointerworks.color.Colorizer;
import com.nullpointerworks.graphics.Image;

public class SpriteSheet extends IntBuffer
{
	private final Colorizer ColorRGB = Colorizer.getColorizer(ColorFormat.RGB);
	private int chroma = ColorRGB.toInt(0, 0, 0);
	
	public SpriteSheet(IntBuffer image) 
	{
		super(image.getWidth(), image.getHeight());
		plot(image.content());
	}
	
	public Sprite getSprite(int x, int y, int w, int h) 
	{
		IntBuffer r = Image.extract(x, y, w, h, this);
		Sprite sub = new Sprite(r);
		sub.setChroma(chroma);
		return sub;
	}
	
	public void setChroma(int chroma) 
	{
		this.chroma = chroma;
	}
	public int chroma() {return chroma;}
}
