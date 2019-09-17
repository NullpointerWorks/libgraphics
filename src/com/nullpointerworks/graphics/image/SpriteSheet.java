package com.nullpointerworks.graphics.image;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.color.Colorizer;
import com.nullpointerworks.graphics.Image;

public class SpriteSheet extends IntBuffer
{
	private static int BLACK = Colorizer.toInt(0, 0, 0);
	private int chroma = BLACK;
	
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
