/**
 * @version 1.0.11
 */
module libnpw.graphics
{
	requires transitive java.desktop;
	requires transitive libnpw.core;
	requires transitive libnpw.math;
	requires transitive libnpw.util;
	exports com.nullpointerworks.graphics;
	exports com.nullpointerworks.graphics.colormodel;
	exports com.nullpointerworks.graphics.font;
	exports com.nullpointerworks.graphics.gui;
	exports com.nullpointerworks.graphics.gui.awt;
	exports com.nullpointerworks.graphics.gui.elements;
	exports com.nullpointerworks.graphics.gui.input;
	exports com.nullpointerworks.graphics.gui.interfaces;
	exports com.nullpointerworks.graphics.image;
	exports com.nullpointerworks.graphics.image.io;
	exports com.nullpointerworks.graphics.render;
	exports com.nullpointerworks.graphics.render.shader;
}