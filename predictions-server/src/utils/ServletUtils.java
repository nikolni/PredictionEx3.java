package utils;


import engine.per.file.engine.api.SystemEngineAccess;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import user.manager.UserManager;
import user.request.UserRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME = "userRequestList";
	private static final String ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME = "userRequestListSize";
	private static final String SIMULATION_NAMES_LIST_ATTRIBUTE_NAME = "simulationNamesList";
	private static final String USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME = "mapOfUserNameToRequestsMap"; 	//Map<String, Map<UserRequest, List<Integer>>>
	private static final String SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME = "simulationNameToSE";
	private static final String THREAD_POOL_SIZE_ATTRIBUTE_NAME = "threadPoolSize";
	private static final String THREAD_POOL_ATTRIBUTE_NAME = "threadPool";
	private static final String EXECUTION_COUNTER_ATTRIBUTE_NAME = "executionsCounter";

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();
	private static final Object allUsersRequestsListLock = new Object();
	private static final Object allUsersRequestsListSizeLock = new Object();
	private static final Object simulationNamesListLock = new Object();
	private static final Object userNameToRequestsListLock = new Object();
	private static final Object simulationNameToSELock = new Object();
	private static final Object threadPoolSizeLock = new Object();
	private static final Object threadPoolLock = new Object();
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

	public static void addRequestToAllUsersRequestsList(ServletContext servletContext, UserRequest userRequest) {

		synchronized (allUsersRequestsListLock) {
			if (servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME, new ArrayList<>());
			}
			((List<UserRequest>) servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME)).add(userRequest);
		}
	}
	public static Integer getUserRequestListSize(ServletContext servletContext) {

		synchronized (allUsersRequestsListSizeLock) {
			if (servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(ALL_USERS_REQUESTS_LIST_SIZE_ATTRIBUTE_NAME, 0);
			}
			return ++((Integer)servletContext.getAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME));
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
	public static void addRequestByUserName(ServletContext servletContext, String userName ,UserRequest userRequest) {
		Map<UserRequest, List<Integer>> userRequestListMap = null;

		synchronized (userNameToRequestsListLock) {
			if (servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME, new HashMap<>());
			}
			Map<String, Map<UserRequest, List<Integer>>> requestsListByUserNameMap =
					servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME);
			if(requestsListByUserNameMap.get(userName) == null){
				userRequestListMap = new HashMap<>();
				requestsListByUserNameMap.put(userName,userRequestListMap);
			}
			userRequestListMap.put(userRequest, null);
		}
	}
	public static void addExecutionByUserNameAndRequestID(ServletContext servletContext, String userName ,Integer requestID) {
		List<Integer> userRequestList = null;

		Map<String, Map<UserRequest, List<Integer>>> requestsListByUserNameMap =
				servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME);
		//if we got here, there is already user request to get.
		Map<UserRequest, List<Integer>> userRequestListMap = requestsListByUserNameMap.get(userName);

		synchronized (userNameToRequestsListLock) {
			if((userRequestListMap.get(((List<UserRequest>) servletContext.getAttribute(ALL_USERS_REQUESTS_LIST_ATTRIBUTE_NAME)).get(requestID -1)))== null){
				userRequestList = new ArrayList<>();
			}
			userRequestList.add((Integer)servletContext.getAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME));
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
	public static void addRunnableToThreadPool(ServletContext servletContext, Runnable runnable) {

		synchronized (threadPoolLock) {
			if (servletContext.getAttribute(THREAD_POOL_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(THREAD_POOL_ATTRIBUTE_NAME, Executors.newFixedThreadPool(
						servletContext.getAttribute(THREAD_POOL_SIZE_ATTRIBUTE_NAME)));
			}
			((ExecutorService)servletContext.getAttribute(THREAD_POOL_ATTRIBUTE_NAME)).submit(runnable);
		}
	}
	public static void setSizeOfThreadPool(ServletContext servletContext, Integer size) {

		synchronized (threadPoolSizeLock) {
			if (servletContext.getAttribute(THREAD_POOL_SIZE_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(THREAD_POOL_SIZE_ATTRIBUTE_NAME, size);
			}
		}
	}
	public static Integer increaseExecutionCounter(ServletContext servletContext) {

		synchronized (executionsCounterLock) {
			if (servletContext.getAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME, 0);
			}
			return ++((Integer)servletContext.getAttribute(EXECUTION_COUNTER_ATTRIBUTE_NAME));
		}
	}


}
