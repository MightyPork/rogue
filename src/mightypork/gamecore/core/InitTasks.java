package mightypork.gamecore.core;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mightypork.utils.logging.Log;


public class InitTasks {
	
	/**
	 * Order init tasks so that all dependencies are loaded before thye are
	 * needed by the tasks.
	 * 
	 * @param tasks task list
	 * @return task list ordered
	 */
	public static List<InitTask> inOrder(List<InitTask> tasks)
	{
		List<InitTask> remaining = new ArrayList<>(tasks);
		
		List<InitTask> ordered = new ArrayList<>();
		Set<String> loaded = new HashSet<>();
		
		// resolve task order
		int addedThisIteration = 0;
		do {
			for (Iterator<InitTask> i = remaining.iterator(); i.hasNext();) {
				InitTask task = i.next();
				
				String[] deps = task.getDependencies();
				if (deps == null) deps = new String[] {};
				
				int unmetDepsCount = deps.length;
				
				for (String d : deps) {
					if (loaded.contains(d)) unmetDepsCount--;
				}
				
				if (unmetDepsCount == 0) {
					ordered.add(task);
					loaded.add(task.getName());
					i.remove();
					addedThisIteration++;
				}
			}
		} while (addedThisIteration > 0);
		
		// check if any tasks are left out
		if (remaining.size() > 0) {
			
			// build error message for each bad task
			for (InitTask task : remaining) {
				String notSatisfied = "";
				
				for (String d : task.getDependencies()) {
					if (!loaded.contains(d)) {
						
						if (!notSatisfied.isEmpty()) {
							notSatisfied += ", ";
						}
						
						notSatisfied += d;
					}
				}
				
				Log.w("InitTask \"" + task.getName() + "\" - missing dependencies: " + notSatisfied);
			}
			
			throw new RuntimeException("Some InitTask dependencies could not be satisfied.");
		}
		
		return ordered;
	}
}
