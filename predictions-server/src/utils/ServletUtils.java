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


public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String USER_REQUEST_LIST_ATTRIBUTE_NAME = "userRequestList";
	private static final String SIMULATION_NAMES_LIST_ATTRIBUTE_NAME = "simulationNamesList";
	private static final String USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME = "userNameToRequestsList";
	private static final String SIMULATION_NAME_TO_SE_MAP_ATTRIBUTE_NAME = "simulationNameToSE";

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();
	private static final Object userRequestListLock = new Object();
	private static final Object simulationNamesListLock = new Object();
	private static final Object userNameToRequestsListLock = new Object();
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

	public static List<UserRequest> getUserRequestList(ServletContext servletContext) {

		synchronized (userRequestListLock) {
			if (servletContext.getAttribute(USER_REQUEST_LIST_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_REQUEST_LIST_ATTRIBUTE_NAME, new ArrayList<>());
			}
		}
		return (List<UserRequest>) servletContext.getAttribute(USER_REQUEST_LIST_ATTRIBUTE_NAME);
	}
	public static List<String> getSimulationNamesList(ServletContext servletContext) {

		synchronized (simulationNamesListLock) {
			if (servletContext.getAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME, new ArrayList<>());
			}
		}
		return (List<String>) servletContext.getAttribute(SIMULATION_NAMES_LIST_ATTRIBUTE_NAME);
	}
	public static List<UserRequest> getRequestsListByUserName(ServletContext servletContext, String userName) {
		List<UserRequest> userRequestsList = null;

		synchronized (userNameToRequestsListLock) {
			if (servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME, new HashMap<>());
			}
			Map<String, List<UserRequest>> requestsListByUserNameMap =
					servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME);
			if(requestsListByUserNameMap.get(userName) == null){
				userRequestsList = new ArrayList<>();
				requestsListByUserNameMap.put(userName,userRequestsList);
			}
		}
		return userRequestsList;
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
}
