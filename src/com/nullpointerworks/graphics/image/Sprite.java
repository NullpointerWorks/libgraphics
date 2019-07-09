package com.nullpointerworks.graphics.image;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.graphics.Color;

public class Sprite extends IntBuffer
{
	private IntBuffer source;
	private float x,y,angle;
	private int chroma;
	
	public Sprite(int w, int h) 
	{
		this(new IntBuffer(w,h, Color.WHITE));
	}
	
	public Sprite(IntBuffer image) 
	{
		super(image.getWidth(), image.getHeight());
		source = image.copy();
		plot(source.content());
		angle = x = y = 0;
		chroma = Color.BLACK;
	}
	
	// ===================================
	
	public void setLocation(float x, float y) 
	{
		this.x = x;
		this.y = y;
	}
	public float x() {return x;}
	public float y() {return y;}
	
	public void setRotation(float angle) 
	{
		this.angle = angle;
	}
	public float angle() {return angle;}
	
	public void setChroma(int chroma) 
	{
		this.chroma = chroma;
	}
	public int chroma() {return chroma;}
}