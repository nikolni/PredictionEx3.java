package utils;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import user.manager.UserManager;
import user.request.UserRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String USER_REQUEST_LIST_ATTRIBUTE_NAME = "userRequestList";
	private static final String SIMULATION_NAMES_LIST_ATTRIBUTE_NAME = "simulationNamesList";
	private static final String USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME = "userNameToRequestsList";

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();
	private static final Object userRequestListLock = new Object();
	private static final Object simulationNamesListLock = new Object();
	private static final Object userNameToRequestsListLock = new Object();


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
	public static List<String> getMapUserNameToRequestsList(ServletContext servletContext) {

		synchronized (userNameToRequestsListLock) {
			if (servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME, new ArrayList<>());
			}
		}
		return (Map<String, List<UserRequest>>) servletContext.getAttribute(USER_NAME_TO_REQUESTS_MAP_ATTRIBUTE_NAME);
	}

}
