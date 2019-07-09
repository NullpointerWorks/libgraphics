package com.nullpointerworks.graphics.gui.input;

import com.nullpointerworks.graphics.gui.interfaces.UIKeystrokeFilter;

public class PassthroughFilter implements UIKeystrokeFilter
{
	/**
	 * Returns true for every keystroke. 
	 */
	@Override
	public boolean onKeystroke(int keycode)
	{
		return true;
	}
}
