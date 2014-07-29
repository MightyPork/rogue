package mightypork.rogue.init;


import java.io.File;

import mightypork.gamecore.core.init.InitTaskWorkdir;


public class RogueSetupWorkdir extends InitTaskWorkdir {

	public RogueSetupWorkdir(File workdir)
	{
		super(workdir, true);
	}


	@Override
	public void init()
	{
		addPath("slot1", "saves/slot_1.ion");
		addPath("slot2", "saves/slot_2.ion");
		addPath("slot3", "saves/slot_3.ion");
	}
}
