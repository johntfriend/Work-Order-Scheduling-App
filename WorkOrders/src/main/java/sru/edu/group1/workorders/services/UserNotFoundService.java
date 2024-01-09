package sru.edu.group1.workorders.services;
//class to use when a user in not found in the database (Not fully working having errors using it with try and catch)
@SuppressWarnings("serial")
public class UserNotFoundService extends Exception {
		public UserNotFoundService(String message) {
			super(message);
		}

}


