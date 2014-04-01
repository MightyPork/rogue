package mightypork.rogue.testing;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mightypork.utils.logging.Log;
import mightypork.utils.patterns.subscription.Handleable;
import mightypork.utils.patterns.subscription.MessageBus;
import mightypork.utils.patterns.subscription.clients.DelegatingClient;
import mightypork.utils.patterns.subscription.clients.ToggleableClient;


public class TestMsgbus {
	
	public static void main(String[] args)
	{
		Log.create("runtime", new File("."), 0);
		
		MessageBus bus = new MessageBus();
		
		bus.createChannel(StringMessage.class, StringMessage.Listener.class);
		bus.createChannel(IntMessage.class, IntMessage.Listener.class);
		
		Delegator deleg1 = new Delegator("Deleg1");
		Delegator deleg2 = new Delegator("Deleg2");
		Toggleable togg1 = new Toggleable("Tog1");
		Toggleable togg2 = new Toggleable("Tog2");
		Toggleable plain1 = new Toggleable("Plain1");
		Toggleable plain2 = new Toggleable("Plain2");
		Toggleable plain3 = new Toggleable("Plain3");
		
		PlainInt pint = new PlainInt("Ints");
		PlainBoth pboth = new PlainBoth("Both");
		
		bus.subscribe(deleg1);
		
		deleg1.clients.add(togg1);
		deleg1.clients.add(plain2);
		deleg1.clients.add(deleg2);
		deleg1.clients.add(pint);
		
		deleg2.clients.add(deleg1);
		deleg2.clients.add(togg1);
		deleg2.clients.add(plain3);
		deleg2.clients.add(pboth);
		
		bus.subscribe(plain1);
		
		bus.subscribe(togg2);
		
		bus.broadcast(new StringMessage("<MSG>"));
		bus.broadcast(new IntMessage(7));
		bus.broadcast(new IntMessage(13));
		
		deleg2.delegating = false;
		
		bus.broadcast(new IntMessage(44));
		
		deleg2.delegating = true;
		
		bus.broadcast(new IntMessage(45));
		
	}
	
}


class Delegator extends Plain implements DelegatingClient {
	
	List<Object> clients = new ArrayList<Object>();
	boolean delegating = true;
	
	
	public Delegator(String name) {
		super(name);
	}
	
	
	@Override
	public Collection<Object> getChildClients()
	{
		return clients;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return delegating;
	}
}


class Toggleable extends Plain implements ToggleableClient {
	
	boolean subscribing = true;
	
	
	public Toggleable(String name) {
		super(name);
	}
	
	
	@Override
	public boolean doesSubscribe()
	{
		return subscribing;
	}
}


class Plain implements StringMessage.Listener {
	
	String name;
	
	
	public Plain(String name) {
		this.name = name;
	}
	
	
	@Override
	public void receive(StringMessage message)
	{
		System.out.println(name + " (STR) RECV: " + message.s);
	}
}


class PlainInt implements IntMessage.Listener {
	
	String name;
	
	
	public PlainInt(String name) {
		this.name = name;
	}
	
	
	@Override
	public void receive(IntMessage message)
	{
		System.out.println(name + " (INT) RECV: " + message.i);
	}
}


class PlainBoth implements IntMessage.Listener, StringMessage.Listener {
	
	String name;
	
	
	public PlainBoth(String name) {
		this.name = name;
	}
	
	
	@Override
	public void receive(IntMessage message)
	{
		System.out.println(name + " (both-INT) RECV: " + message.i);
	}
	
	
	@Override
	public void receive(StringMessage message)
	{
		System.out.println(name + " (both-STR) RECV: " + message.s);
	}
}


class StringMessage implements Handleable<StringMessage.Listener> {
	
	String s;
	
	
	StringMessage(String str) {
		this.s = str;
	}
	
	public interface Listener {
		
		public void receive(StringMessage message);
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.receive(this);
	}
}


class IntMessage implements Handleable<IntMessage.Listener> {
	
	int i;
	
	
	IntMessage(int i) {
		this.i = i;
	}
	
	public interface Listener {
		
		public void receive(IntMessage message);
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.receive(this);
	}
}
