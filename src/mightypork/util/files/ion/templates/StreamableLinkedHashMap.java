package mightypork.util.files.ion.templates;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;

import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.Streamable;


public class StreamableLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Streamable {
	
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
