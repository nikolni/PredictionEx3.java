package utils;

import allocation.manager.AllocationsManager;
import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.ServletContext;
import thread.pool.ThreadsPoolManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String THREADS_POOL_MANAGER_ATTRIBUTE_NAME = "threadPoolManager";
	private static final String ALLOCATIONS_MANAGER_ATTRIBUTE_NAME = "allocationsManager";
	private static final String SIMULATION_NAMES_LIST_ATTRIBUTE_NAME = "simulationNamesList";
	private static final String SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME = "simulationNameToSE";

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();
	private static final Object threadPoolManagerLock = new Object();
	private static final Object allocationsManagerLock = new Object();
	private static final Object simulationNamesListLock = new Object();
	private static final Object simulationNameToSELock = new Object();

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static UserManager getUserManager(ServletContext servletContext) {

		synchronized (userManagerLock) {
			if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
			}
		}
		return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
	}
	public static ThreadsPoolManager getThreadPoolManager(ServletContext servletContext) {

		synchronized (threadPoolManagerLock) {
			if (servletContext.getAttribute(THREADS_POOL_MANAGER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(THREADS_POOL_MANAGER_ATTRIBUTE_NAME, new ThreadsPoolManager(1));
			}
		}
		return (ThreadsPoolManager) servletContext.getAttribute(THREADS_POOL_MANAGER_ATTRIBUTE_NAME);
	}
	public static AllocationsManager getAllocationsManager(ServletContext servletContext) {

		synchronized (allocationsManagerLock) {
			if (servletContext.getAttribute(ALLOCATIONS_MANAGER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(ALLOCATIONS_MANAGER_ATTRIBUTE_NAME, new AllocationsManager());
			}
		}
		return (AllocationsManager) servletContext.getAttribute(ALLOCATIONS_MANAGER_ATTRIBUTE_NAME);
	}
	/*public static SystemEngineAccess initEngineAttributeName(ServletContext servletContext){
		synchronized (engineLock) {
			servletContext.setAttribute(ENGINE_ATTRIBUTE_NAME,new SystemEngineAccessImpl());
		}
		return (SystemEngineAccessImpl) servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME);
	}*/

	public static List<String> getSimulationNamesList(ServletContext servletContext) {
		synchronized (simulationNamesListLock) {
			if (servletContext.getAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME, new ArrayList<>());
			}
		}
		return (List<String>) servletContext.getAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME);
	}

	public static void addSimulationNamesToList(ServletContext servletContext,String newSimulationName) {
		synchronized (simulationNamesListLock) {
			if (servletContext.getAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME, new ArrayList<>());
			}
		}
		((List<String>) servletContext.getAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME)).add(newSimulationName);
	}

	//if we got here with simulation name, there must be SystemEngineAccess instance for this simulation.
	public static SystemEngineAccess getSEInstanceBySimulationName(ServletContext servletContext, String simulationName) {
		synchronized (simulationNameToSELock) {
			if (servletContext.getAttribute(SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME, new HashMap<>());
			}
		}
		return ((Map<String, SystemEngineAccess>) servletContext.getAttribute(SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME))
				.get(simulationName);
	}

	public static void addSEInstanceBySimulationName(ServletContext servletContext, String simulationName,SystemEngineAccess systemEngineAccess) {
		synchronized (simulationNameToSELock) {
			if (servletContext.getAttribute(SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME, new HashMap<>());
			}
		}
		((Map<String, SystemEngineAccess>) servletContext.getAttribute(SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME)).
				put(simulationName,systemEngineAccess);
	}
}
