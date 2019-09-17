package com.nullpointerworks.graphics.font;

import com.nullpointerworks.core.buffer.FloatBuffer;
import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.color.Colorizer;
import com.nullpointerworks.graphics.Draw;
import com.nullpointerworks.util.Log;
import com.nullpointerworks.util.file.bytefile.ByteFile;
import com.nullpointerworks.util.file.bytefile.ByteFileReader;
import com.nullpointerworks.util.file.bytefile.EndOfFileException;

public class Font extends IntBuffer
{
	public static final int COLUMN_ROW = 0;
	public static final int ROW_COLUMN = 1;
	
	private FloatBuffer mask = null;
	private int foregroundColor = Colorizer.toInt(255, 255, 255);
	private int backgroundColor = Colorizer.toInt(0, 0, 0);
	
	private int charWidth 	= 0;
	private int charHeight 	= 0;
	private int imageWidth 	= 0;
	private int imageHeight = 0;
	
	private int ORIENTATION = COLUMN_ROW;
	private final float inv = 1f / 255f;
	
	public Font() 
	{
		super(0,0);
		mask = new FloatBuffer(0,0, 0f);
	}
	
	/**
	 * 
	 * @throws EndOfFileException 
	 */
	Font(ByteFile bf) throws EndOfFileException
	{
		this();
		
		if (bf==null)
		{
			Log.err("NullPointer: The loaded file is null.");
		}
		
		ByteFileReader bfr = bf.getReader();
		
		imageWidth 	= bfr.getShort();
		imageHeight = bfr.getShort();
		charWidth 	= bfr.getShort();
		charHeight 	= bfr.getShort();
		
		if (imageWidth < 1 || imageHeight < 1)
		{
			Log.err("Loaded font file does not contain valid image data.");
			bfr.free();
			return;
		}
		
		if (charHeight < 1 || charWidth < 1)
		{
			Log.err("Loaded font file does not contain valid character information.");
			bfr.free();
			return;
		}
		
		/*
		 * we know the size of the image, get all its data and make the grey-scale mask
		 */
		this.createBuffer(imageWidth, imageHeight);
		if (mask!=null)mask.free();
		mask = new FloatBuffer(imageWidth, imageHeight, 0f);
		for (int j=0; j<length;j++)
		{
			float val = (float)(bfr.getByte() & 0xFF);
			float ratio = val * inv;
			mask.plot(j, ratio);
		}
		
		/*
		 * done, redraw the image of this font
		 */
		refresh();
		bfr.free();
	}
	
	/*
	 * constructs a copy of the given Font object
	 */
	private Font(Font f)
	{
		super(f.getWidth(), f.getHeight());
		this.plot(f.content());
		charWidth 		= f.charWidth;
		charHeight 		= f.charHeight;
		imageWidth 		= f.imageWidth;
		imageHeight 	= f.imageHeight;
		foregroundColor = f.foregroundColor;
		backgroundColor = f.backgroundColor;
		mask 			= f.mask.copy();
	}
	
	/**
	 * convert a string of text to a sprite
	 */
	public IntBuffer toImage(String text) 
	{
		byte[] string 		= text.getBytes();
		int leng 			= string.length;
		IntBuffer banner 	= new IntBuffer(leng*charWidth, charHeight);
		IntBuffer source;
		int col=0,row=0, i=0;
		
		for (byte chr : string)
		{
			if (ORIENTATION == COLUMN_ROW)
			{
				col = chr / 16;
				row = chr % 16;
			}
			else
			if (ORIENTATION == ROW_COLUMN)
			{
				row = chr / 16;
				col = chr % 16;
			}
			
			source = extract(col*charWidth, row*charHeight);
			Draw.image(i, 0, source, banner);
			
			i+=charWidth;
			source.free();
			source = null;
		}
		string = null;
		return banner;
	}
	
	/**
	 * 
	 */
	public void setCharacterMap(IntBuffer image) 
	{
		imageWidth 	= image.getWidth();
		imageHeight = image.getHeight();
		charWidth 	= (imageWidth/16);
		charHeight 	= (imageHeight/16);
		
		this.createBuffer(imageWidth, imageHeight);
		if (mask!=null)mask.free();
		mask = new FloatBuffer(imageWidth, imageHeight, 0f);
		
		for (int j=0; j<length;j++)
		{
			float col = ratio( image.grab(j) );
			mask.plot(j, col);
		}
		refresh();
	}
	
	/**
	 * 
	 */
	public void setScanOrientation(final int or)
	{
		ORIENTATION = or;
	}
	
	/**
	 * 
	 */
	public void setForegroundColor(int foregroundColor)
	{
		this.foregroundColor = foregroundColor;
		refresh();
	}
	
	/**
	 * 
	 */
	public void setBackgroundColor(int backgroundColor) 
	{
		this.backgroundColor = backgroundColor;
		refresh();
	}
	
	/**
	 * 
	 */
	public int getCharWidth() 
	{
		return charWidth;
	}
	
	/**
	 * 
	 */
	public int getCharHeight() 
	{
		return charHeight;
	}
	
	/**
	 * Returns a reference free copy of this Font object
	 */
	public Font getCopy()
	{
		return new Font(this);
	}
	
	// =======================================================
	
	/**
	 * 
	 */
	private final void refresh()
	{
		float[] ratios = mask.content();
		for (int i=0,l=ratios.length; i<l; i++)
		{
			int c = Colorizer.argb.lerp(backgroundColor, foregroundColor, ratios[i]);
			this.plot(i, c);
		}
		ratios=null;
	} 
	
	/**
	 * 
	 */
	private final IntBuffer extract(int x, int y) 
	{
		IntBuffer sub = new IntBuffer(charWidth, charHeight);
		for (int j=0; j<charHeight; j++)
		for (int i=0; i<charWidth; i++)
		{
			int c = this.grab(x+i, y+j);
			sub.plot(i, j, c);
		}
		return sub;
	}
	
	/**
	 * get a greyscale ratio from the given color in the range [0f, 1f]
	 */
	private float ratio(int c) 
	{
		int r = (c>>16) & 0xFF;
		int g = (c>>8) & 0xFF;
		int b = (c) & 0xFF;
		float f = (float)(b + g + r) * 0.3333f * inv;
		return f;
	}
}
