package utils;

import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.ServletContext;
import thread.pool.ThreadsPoolManager;
import user.UserManager;
import user.request.UserRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String THREADS_POOL_MANAGER_ATTRIBUTE_NAME = "threadPoolManager";
	private static final String ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME = "userRequestList";
	private static final String ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME = "userRequestListSize";
	private static final String SIMULATION_NAMES_LIST_ATTRIBUTE_NAME = "simulationNamesList";
	private static final String USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME = "mapOfUserNameToRequestsMap"; 	//Map<String, Map<UserRequest, List<Integer>>>
	private static final String SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME = "simulationNameToSE";
	private static final String THREAD_POOL_SIZE_ATTRIBUTE_NAME = "threadPoolSize";
	private static final String EXECUTION_COUNTER_ATTRIBUTE_NAME = "executionsCounter";

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();
	private static final Object threadPoolManagerLock = new Object();
	private static final Object allUsersRequestsListLock = new Object();
	private static final Object allUsersRequestsListSizeLock = new Object();
	private static final Object simulationNamesListLock = new Object();
	private static final Object userNameToRequestsMapLock = new Object();
	private static final Object simulationNameToSELock = new Object();
	private static final Object executionsCounterLock = new Object();

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
	/*public static SystemEngineAccess initEngineAttributeName(ServletContext servletContext){
		synchronized (engineLock) {
			servletContext.setAttribute(ENGINE_ATTRIBUTE_NAME,new SystemEngineAccessImpl());
		}
		return (SystemEngineAccessImpl) servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME);
	}*/

	public static void addRequestToAllUsersRequestsList(ServletContext servletContext, UserRequest userRequest) {
		synchronized (allUsersRequestsListLock) {
			if (servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME, new ArrayList<>());
			}
			((List<UserRequest>) servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME)).add(userRequest);
		}
		//if we got here, there must be size initialized, no need to create
		synchronized (allUsersRequestsListSizeLock) {
			//if we got here, there must be size initialized, no need to create
			Integer value = ((Integer) servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME));
			value++;
			servletContext.setAttribute(ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME, value);
		}
	}

	public static List<UserRequest> getAllUsersRequestsList(ServletContext servletContext){
		synchronized (allUsersRequestsListLock) {
			if (servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME, new ArrayList<>());
			}
		}
		return (List<UserRequest>) servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME);
	}

	public static Integer getAllUserRequestsListSize(ServletContext servletContext) {
		synchronized (allUsersRequestsListSizeLock) {
			if (servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME, 1);
			}
		}
		return (Integer)servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME);
	}
	public static void addRequestByUserName(ServletContext servletContext, String userName ,UserRequest userRequest) {
		Map<UserRequest, List<Integer>> userRequestListMap = null;

		synchronized (userNameToRequestsMapLock) {
			if (servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME, new HashMap<>());
			}
			Map<String, Map<UserRequest, List<Integer>>> requestsListByUserNameMap =
					(Map<String, Map<UserRequest, List<Integer>>>) servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME);
			if(requestsListByUserNameMap.get(userName) == null){
				userRequestListMap = new HashMap<>();
				requestsListByUserNameMap.put(userName,userRequestListMap);
			}
			userRequestListMap = requestsListByUserNameMap.get(userName);
			userRequestListMap.put(userRequest, null);
		}
	}
	public static Map<UserRequest, List<Integer>> getRequestsMapByUserName(ServletContext servletContext, String userName) {
		Map<UserRequest, List<Integer>> userRequestListMap;

		synchronized (userNameToRequestsMapLock) {
			if (servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME, new HashMap<>());
			}
			Map<String, Map<UserRequest, List<Integer>>> requestsListByUserNameMap =
					(Map<String, Map<UserRequest, List<Integer>>>) servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME);
			if(requestsListByUserNameMap.get(userName) == null){
				userRequestListMap = new HashMap<>();
				requestsListByUserNameMap.put(userName,userRequestListMap);
			}
			return requestsListByUserNameMap.get(userName);
		}
	}


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

	public static void addExecutionByUserNameAndRequestID(ServletContext servletContext, String userName ,Integer requestID) {
		List<Integer> userRequestList = null;

		Map<String, Map<UserRequest, List<Integer>>> requestsListByUserNameMap =
		 	(Map<String, Map<UserRequest, List<Integer>>>)servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME);
		//if we got here, there is already user request to get.
		Map<UserRequest, List<Integer>> userRequestListMap = requestsListByUserNameMap.get(userName);

		synchronized (userNameToRequestsMapLock) {
			UserRequest userRequest = ((List<UserRequest>)servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME)).get(requestID -1);
			if(userRequestListMap.get(userRequest) == null){
				userRequestList = new ArrayList<>();
			}
			userRequestList.add(getExecutionCounter(servletContext));
		}
	}
	public static Integer getExecutionCounter(ServletContext servletContext) {

		synchronized (executionsCounterLock) {
			if (servletContext.getAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME, 1);
			}
			return ((Integer)servletContext.getAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME));
		}
	}
	public static void increaseExecutionCounter(ServletContext servletContext) {
		synchronized (executionsCounterLock) {
			//if we got here, there must be size initialized, no need to create
			Integer value = ((Integer) servletContext.getAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME));
			value++;
			servletContext.setAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME, value);
		}
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
		((Map<String, SystemEngineAccess>) servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME)).put(simulationName,systemEngineAccess);
	}
}
