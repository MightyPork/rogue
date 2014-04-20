package mightypork.util.files.ion.templates;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.Ionizable;


public abstract class IonizableHashMap<K, V> extends HashMap<K, V> implements Ionizable {
	
	@Override
	public void load(InputStream in) throws IOException
	{
		Ion.readMap(in, this);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		Ion.writeMap(out, this);
	}
	
}
