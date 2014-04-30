package mightypork.gamecore.util.ion;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class IonSequenceWrapper implements IonObjBinary {
	
	private Collection collection = new ArrayList();
	
	
	public IonSequenceWrapper()
	{
		collection = new ArrayList();
	}
	
	
	public IonSequenceWrapper(Collection saved)
	{
		collection = saved;
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		collection.clear();
		in.readSequence(collection);
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		out.writeSequence(collection);
	}
	
	
	public void fill(Collection o)
	{
		o.clear();
		o.addAll(collection);
	}
	
	
	@Override
	public short getIonMark()
	{
		return Ion.SEQUENCE_WRAPPER;
	}
	
}
