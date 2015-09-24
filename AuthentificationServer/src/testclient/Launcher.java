package testclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import checkers.LoginChecker;
import messages.LoginAnswer;
import messages.LoginRequest;

/**
 * @author alexandre
 * Launcher.java
 */
public class Launcher {
	public static void main(String[] args) {
		String serverAddress = "127.0.0.1";
		int port = 42666;

		String login;
		String pwd;

		Scanner sc = new Scanner(System.in);

		System.out.println("Launching test client for timeflies atuhentification server...");


		try {
			Socket socket = new Socket(InetAddress.getByName(serverAddress), port);
			System.out.println("Socket : " + socket);
			
			if (socket.isConnected())
				System.out.println("Connection successfull.");
			else {
				System.out.println("Connection failed.");
				return;
			}
			
			System.out.println("Loading streams...");
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			System.out.println("Streams loaded.");
			
			System.out.print("login : ");
			login = sc.nextLine();
			System.out.print("password : ");
			pwd = sha1(sc.nextLine());

			System.out.println("Connection attemp with the following informations : " + login + " " + pwd);

			out.writeObject(new LoginRequest(login, pwd));

			try {
				LoginAnswer ans = (LoginAnswer)in.readObject();
				
				switch (ans.getAnswer()) {
				case SUCCESS :
					System.out.println("Authentification succesfull, your token is : " + ans.getToken());
					break;
				case FAIL :
					System.out.println("Authentification failed, try again.");
					break;
				case ERROR :
					System.out.println("Error : something happen.");
					break;
				default:
					//can't happen
					break;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String sha1(String message){
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(message.getBytes("UTF-8"));

			//converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2*hash.length);
			for(byte b : hash){
				sb.append(String.format("%02x", b&0xff));
			}

			digest = sb.toString();

		} catch (UnsupportedEncodingException ex) {
		} catch (NoSuchAlgorithmException ex) {
		}
		return digest;
	}
}
