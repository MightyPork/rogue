package mightypork.util.files.ion.templates;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;

import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.Ionizable;


public abstract class IonizableHashSet<E> extends HashSet<E> implements Ionizable {
	
	@Override
	public void load(InputStream in) throws IOException
	{
		Ion.readSequence(in, this);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		Ion.writeSequence(out, this);
	}
	
}
