package com.nullpointerworks.graphics.font;

import java.io.IOException;

import com.nullpointerworks.math.IntMath;
import com.nullpointerworks.util.Log;
import com.nullpointerworks.util.file.bytefile.ByteFile;
import com.nullpointerworks.util.file.bytefile.ByteFileParser;

public class FontParser 
{
	/**
	 * write font data to a file in the following format:<br>
	 * <br>
	 * short > img width<br>
	 * short > img height<br>
	 * short > char width<br>
	 * short > char height<br>
	 * byte... > img data. (byte per pixel)<br>
	 * EOF<br>
	 * @throws IOException 
	 * 
	 */
	public static void write(String path, Font font) throws IOException 
	{
		short img_w 	= (short)font.getWidth();
		short img_h 	= (short)font.getHeight();
		short chr_w 	= (short)font.getCharWidth();
		short chr_h 	= (short)font.getCharHeight();
		int[] data 		= font.content();
		
		ByteFile bf = new ByteFile(null);
		
		bf.addByte( (byte) (img_w &0xFF) );
		bf.addByte( (byte)((img_w>>8) &0xFF) );
		bf.addByte( (byte) (img_h &0xFF) );
		bf.addByte( (byte)((img_h>>8) &0xFF) );
		
		bf.addByte( (byte) (chr_w &0xFF) );
		bf.addByte( (byte)((chr_w>>8) &0xFF) );
		bf.addByte( (byte) (chr_h &0xFF) );
		bf.addByte( (byte)((chr_h>>8) &0xFF) );
		
		int i,l;
		
		for (i=0,l=data.length; i<l; i++)
		{
			int avg,color = data[i];

			avg  = color &0xFF;
			avg += (color>>8) &0xFF;
			avg += (color>>16) &0xFF;
			avg /= 3;
			avg = IntMath.clamp(0, avg, 255);
			
			bf.addByte( (byte)avg );
		}
		
		ByteFileParser.write(path, bf);
	}
	
	/**
	 * 
	 */
	public static Font file(String path)
	{
		if (path == null)
		{
			Log.err("NullPointer: The given path string is null");
			return null;
		}
		
		ByteFile bf = ByteFileParser.file(path);
		if (bf.isNull() || bf == null)
		{
			Log.err("NullPointer: The loaded file returned as null");
			return null;
		}
		return new Font(bf);
	}
	
	/**
	 * 
	 */
	public static Font resource(String path)
	{
		if (path == null)
		{
			Log.err("NullPointer: The given path string is null");
			return null;
		}

		// check first char for a '/'
		char c = path.charAt(0);
		if (c == '/') path = path.substring(1, path.length());
		
		ByteFile bf = ByteFileParser.resource(path);
		if (bf.isNull() || bf == null)
		{
			Log.err("NullPointer: The loaded resource returned as null");
			return null;
		}
		
		return new Font(bf);
	}
	
}
