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

import messages.LoginRequest;

/**
 * @author alexandre
 * Launcher.java
 */
public class Launcher {
	public static void main(String[] args) {
		String serverAddress = "localhost";
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
			//XXX Problem here : streams are nerver created.
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			System.out.println("Streams loaded.");
			System.out.print("login : ");
			login = sc.nextLine();
			System.out.print("password : ");
			pwd = sha1(sc.nextLine());

			System.out.println("Connection attemp with the following informations : " + login + " " + pwd);

			out.writeObject(new LoginRequest(login, pwd));


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
