package com.nullpointerworks.graphics.gui.elements;

import com.nullpointerworks.core.buffer.IntBuffer;
import com.nullpointerworks.core.input.KeyboardInput;
import com.nullpointerworks.core.input.Mouse;
import com.nullpointerworks.core.input.MouseInput;
import com.nullpointerworks.graphics.gui.UIElement;
import com.nullpointerworks.graphics.gui.interfaces.UIActivityListener;
import com.nullpointerworks.graphics.gui.interfaces.UIEnableListener;
import com.nullpointerworks.graphics.gui.interfaces.UIHoverListener;
import com.nullpointerworks.util.Lock;

public abstract class ButtonElement extends UIElement 
implements UIEnableListener, UIActivityListener, UIHoverListener
{
	private boolean enabled = true;
	private Lock _presslock = new Lock();
	
	@Override
	public void onUpdate(MouseInput mi, float dt) 
	{
		if (!enabled) return;
		
		float ox=0f, oy=0f;
		if (getParent() != null) 
		{
			ox = getParent().getGeometry().getBoundingBox().x;
			oy = getParent().getGeometry().getBoundingBox().y;
		}
		
		float mouse_x = mi.getMouseX() - ox;
		float mouse_y = mi.getMouseY() - oy;
		
		if (!onGeometryTest(mouse_x, mouse_y))
		{
			_presslock.unlock();
			return;
		}
		onHover();
		
		if (!_presslock.isLocked())
		if (mi.isClicked( Mouse.LEFT )) 
		{
			_presslock.lock();
			onPressed();
		}
		
		if (_presslock.isLocked())
		{
			if(mi.isClicked( Mouse.LEFT )) 
			{
				onPressing();
			}
			else
			{
				onRelease();
				_presslock.unlock();
			}
		}
	}
	
	/**
	 * returns the active state of the button
	 */
	public boolean isEnabled()
	{
		return enabled;
	}
	
	/**
	 * enable or disable the button. This triggers enable and disable event
	 */
	public void setEnabled(boolean enable)
	{
		if (!isEnabled())
		if (enable)
		{
			onEnable();
		}
		
		if (isEnabled())
		if (!enable)
		{
			onDisable();
		}
		
		enabled = enable;
	}
	
	@Override
	public void onUpdate(KeyboardInput ki, float dt) { }
	
	@Override
	public void onRender(IntBuffer s, float dx, float dy) { }
	
	@Override
	public void onDispose() { }
}