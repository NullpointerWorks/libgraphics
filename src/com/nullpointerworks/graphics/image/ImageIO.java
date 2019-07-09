package com.nullpointerworks.graphics.image;

import com.nullpointerworks.graphics.image.io.ImageLoader;
import com.nullpointerworks.graphics.image.io.ImageSaver;

public class ImageIO
{
	public static final ImageLoader load = new ImageLoader();
	public static final ImageSaver save = new ImageSaver();
}
