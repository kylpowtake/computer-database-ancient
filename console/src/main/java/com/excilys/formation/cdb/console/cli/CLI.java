package com.excilys.formation.cdb.console.cli;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import com.excilys.formation.cdb.core.enumeration.ResultCLI;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.core.model.Computer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * Classe gérant les entrées / sorties avec l'utilisateur avec la console.
 * 
 * @author kylian
 *
 */
public abstract class CLI {
	private static final String STARTURI = "http://localhost:8080/computer-database-master/console/";

	private static final String COMPANY = "company";

	private static final String COMPUTER = "computer";

	/**
	 * Le scanner permettant à l'utilisateur de donner des commandes.
	 */
	private static Scanner scanner;

	private static Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);

	private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

	/**
	 * Méthode écrivant un message pour l'utilisateur dans la console.
	 * 
	 * @param message Le message à afficher.
	 */
	public static void Ecriture(String message) {
		System.out.println(message);
	}

	/**
	 * Méthode lisant un message écrit par l'utilisateur dans la console.
	 * 
	 * @return Le message écrit par l'utilisateur.
	 */
	public static String Lecture() {
		scanner = new Scanner(System.in);
		String message = scanner.nextLine();
		return message;
	}

	public static void gestionDiscussion(String message) {
		CLI.Ecriture(message);
		gestionMessage(CLI.Lecture());
	}

	private static ResultCLI testResult(Class<?> classResult, Class<?> classExpected) {
		if (StatusType.class.equals(classResult)) {
			return ResultCLI.BAD_RESULT;
		} else if (JsonProcessingException.class.equals(classResult)) {
			return ResultCLI.JSON_MAPPING_EXCEPTION;
		} else if (classExpected != null && classExpected.equals(classResult)) {
			return ResultCLI.GOOD_CLASS;
		} else {
			return ResultCLI.OTHER;
		}
	}

	private static String findCompany(String argument) {
		if (!stringIsInt(argument)) {
			return "Mauvais paramètres.";
		}
		Class<Company> objectClass = Company.class;
		Object result = SendRequestGet(STARTURI + COMPANY + "/" + argument, objectClass);
		ResultCLI resultCLI = testResult(result.getClass(), objectClass);
		switch (resultCLI) {
		case BAD_RESULT:
			StatusType statusType = (StatusType) result;
			return statusType.getStatusCode() + " " + statusType.getReasonPhrase();
		case JSON_MAPPING_EXCEPTION:
			JsonProcessingException e = (JsonProcessingException) result;
			return e.getLocalizedMessage();
		case OTHER:
			return ("No action done because of unknown error, more information : " + result.getClass());
		case GOOD_CLASS:
			Company company = (Company) result;
			String message = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n plop : " + company.toString()
					+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			return message;
		default:
			return ("No action done by default because of unknown error");
		}
	}

	private static String findComputer(String argument) {
		if (!stringIsInt(argument)) {
			return "Mauvais paramètres.";
		}
		Class<Computer> objectClass = Computer.class;
		Object result = SendRequestGet(STARTURI + COMPUTER + "/" + argument, objectClass);
		ResultCLI resultCLI = testResult(result.getClass(), objectClass);
		switch (resultCLI) {
		case BAD_RESULT:
			StatusType statusType = (StatusType) result;
			return statusType.getStatusCode() + " " + statusType.getReasonPhrase();
		case JSON_MAPPING_EXCEPTION:
			JsonProcessingException e = (JsonProcessingException) result;
			return e.getLocalizedMessage();
		case OTHER:
			return ("No action done because of unknown error, more information : " + result.getClass());
		case GOOD_CLASS:
			Computer computer = (Computer) result;
			String message = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n plop : " + computer.toString()
					+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			return message;
		default:
			return ("No action done by default because of unknown error");
		}
	}

	private static String deleteCompany(String argument) {
		String result = SendRequestDelete(STARTURI + COMPANY + "/" + argument, String.class);
		if ("SUCCESS".equals(result)) {
			String message = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n plop : " + result + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			return message;
		} else {
			return result;
		}
	}

	private static String deleteComputer(String argument) {
		String result = SendRequestDelete(STARTURI + COMPUTER + "/" + argument, String.class);
		if ("SUCCESS".equals(result)) {
			String message = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n plop : " + result + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			return message;
		} else {
			return result;
		}
	}

	private static String getCompanies() {
		Class<Company[]> objectClass = Company[].class;
		Object result = SendRequestGet(STARTURI + COMPANY + "/", objectClass);
		ResultCLI resultCLI = testResult(result.getClass(), objectClass);
		switch (resultCLI) {
		case BAD_RESULT:
			StatusType statusType = (StatusType) result;
			return statusType.getStatusCode() + " " + statusType.getReasonPhrase();
		case JSON_MAPPING_EXCEPTION:
			JsonProcessingException e = (JsonProcessingException) result;
			return e.getLocalizedMessage();
		case OTHER:
			return ("No action done because of unknown error, more information : " + result.getClass() + result.toString());
		case GOOD_CLASS:
			Company[] companiesList = (Company[]) result;
			String message = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			for (Company company : companiesList) {
				message += "plop : " + company.toString();
			}
			message += "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			return message;
		default:
			return ("No action done by default because of unknown error");
		}
	}

	private static String getComputers() {
		Class<Computer[]> objectClass = Computer[].class;
		Object result = SendRequestGet(STARTURI + COMPUTER + "/", objectClass);
		ResultCLI resultCLI = testResult(result.getClass(), objectClass);
		switch (resultCLI) {
		case BAD_RESULT:
			StatusType statusType = (StatusType) result;
			return statusType.getStatusCode() + " " + statusType.getReasonPhrase();
		case JSON_MAPPING_EXCEPTION:
			JsonProcessingException e = (JsonProcessingException) result;
			return e.getLocalizedMessage();
		case OTHER:
			return ("No action done because of unknown error, more information : " + result.getClass());
		case GOOD_CLASS:
			Computer[] computersList = (Computer[]) result;
			String message = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			for (Computer computer : computersList) {
				message += "plop : " + computer.toString();
			}
			message += "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			return message;
		default:
			return ("No action done by default because of unknown error");
		}
	}

	private static Object SendRequestGet(String uri, Class<?> resultClass) {
		WebTarget target = client.target(uri);
		Response response = target.request().get();
		try {
			if (response.getStatus() == 200) {
				response.bufferEntity();
				String resultString = response.readEntity(String.class);
				Object resultObject = mapper.readValue(resultString, resultClass);
				return resultObject;
			} else {
				System.out.println("Response not good :(");
				return response.getStatusInfo();
			}
		} catch (InvalidDefinitionException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(1);
			return e;
		} catch (JsonProcessingException e) {
			return e;
		} finally {
			response.close();
		}
	}

	private static String SendRequestDelete(String uri, Class<?> resultClass) {
		WebTarget target = client.target(uri);
		Response response = target.request().delete();
		try {
			if (response.getStatus() == 200) {
				response.bufferEntity();
				String resultString = response.readEntity(String.class);
				return resultString;
			} else {
				return response.getStatusInfo().toString();
			}
		} finally {
			response.close();
		}
	}

	public static void main(String[] args) {
		System.out.println("Start of main console.");
		gestionDiscussion("Bonjour, que voulez vous faire ?");
		System.out.println("End of main Console.");
	}

	public static void gestionMessage(String message) {
		// les arguments sont séparés de la commande par ' : '.
		String[] messageSplit = message.split(", ");
		if (messageSplit.length == 1) {
			// Commande sans arguments.
			switch (message) {
			case "getCompanies":
				gestionDiscussion(getCompanies());
				break;
			case "getComputers":
				gestionDiscussion(getComputers());
				break;
			default:
				System.out.println("Wrong command given, application stopped.");
				System.exit(1);
				break;
			}
		} else if (messageSplit.length == 2) {
			// commande avec des arguments.
			String commande = messageSplit[0];
			String argument = messageSplit[1];
			switch (commande) {
			case "findCompany":
				gestionDiscussion(findCompany(argument));
				break;
			case "findComputer":
				gestionDiscussion(findComputer(argument));
				break;
			case "deleteCompany":
				gestionDiscussion(deleteCompany(argument));
				break;
			case "deleteComputer":
				gestionDiscussion(deleteComputer(argument));
				break;
			case "createCompany":
				break;
			default:
				System.out.println("Wrong command given, application stopped.");
				System.exit(1);
				break;
			}
		} else if (messageSplit.length > 2) {
			// commande avec des arguments.
			String commande = messageSplit[0];
			String argument1 = messageSplit[1];
			String argument2 = messageSplit[2];
			switch (commande) {
			case "updateCompany":
				break;
			case "createComputer":
				break;
			case "updateComputer":
				break;
			default:
				System.out.println("Wrong command given, application stopped.");
				System.exit(1);
				break;
			}
		} else {
			System.out.println("Wrong command given, application stopped.");
			System.exit(1);
		}
	}

	public static Boolean stringIsInt(String argument) {
		if (argument != null && argument.length() != 0) {
			for (int i = 0; i < argument.length(); i++) {
				char c = argument.charAt(i);
				if (c > '9' || c < '0') {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
