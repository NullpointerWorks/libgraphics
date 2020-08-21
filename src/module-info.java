/**
 * Creative Commons - Attribution, Share Alike 4.0<br>
 * Nullpointer Works (2020)<br>
 * Using this library makes you subject to the license terms.<br>
 * @version 1.0.0
 * @author Michiel Drost - Nullpointer Works
 */
module libnpw.graphics
{
	requires transitive libnpw.core;
	requires transitive libnpw.math;
	requires libnpw.util;
	requires libnpw.color;
	
	exports com.nullpointerworks.graphics;
	exports com.nullpointerworks.graphics.font;
	exports com.nullpointerworks.graphics.image;
	exports com.nullpointerworks.graphics.image.io;
	exports com.nullpointerworks.graphics.render;
	exports com.nullpointerworks.graphics.render.shader;
}
