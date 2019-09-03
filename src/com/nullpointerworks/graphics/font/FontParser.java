package com.nullpointerworks.graphics.font;

import java.io.IOException;
import java.io.InputStream;

import com.nullpointerworks.math.IntMath;
import com.nullpointerworks.util.Log;
import com.nullpointerworks.util.file.bytefile.ByteFile;
import com.nullpointerworks.util.file.bytefile.ByteFileParser;
import com.nullpointerworks.util.file.bytefile.EndOfFileException;

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
	 * @throws IOException 
	 */
	public static Font file(String path) throws IOException
	{
		if (path == null)
		{
			Log.err("FontParser: The given path string is null");
			return null;
		}
		
		ByteFile bf = ByteFileParser.file(path);
		if (bf == null)
		{
			Log.err("FontParser: The loaded file returned as null");
			return null;
		}
		
		try 
		{
			return new Font(bf);
		} 
		catch (EndOfFileException e) 
		{
			Log.err("FontParser: File format read error.");
			return null;
		}
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public static Font stream(InputStream stream) throws IOException
	{
		if (stream == null)
		{
			Log.err("FontParser: The given stream string is null");
			return null;
		}
		
		ByteFile bf = ByteFileParser.stream(stream);
		if (bf == null)
		{
			Log.err("FontParser: The loaded resource returned as null");
			return null;
		}
		
		try 
		{
			return new Font(bf);
		} 
		catch (EndOfFileException e) 
		{
			Log.err("FontParser: File format read error.");
			return null;
		}
	}
	
}
