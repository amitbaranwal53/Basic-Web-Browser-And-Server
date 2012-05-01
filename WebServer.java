import java.io.*;
import java.net.*;
import java.util.*;

class WebServer{
	public static void main(String argv[]) throws Exception
	{
		String requesMessageLine;
		String fileName;
		ServerSocket listenSocket = new ServerSocket(9999);
		while(true)
		{
		Socket connectionSocket = listenSocket.accept();
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		requesMessageLine = inFromClient.readLine();
		StringTokenizer tokenizedLine = new StringTokenizer(requesMessageLine);
		if(tokenizedLine.nextToken().equals("GET")){
		fileName = tokenizedLine.nextToken();
		if(fileName.startsWith("/") == true)
			fileName = fileName.substring(1);
		File file = new File(fileName);
		int numOfBytes = (int) file.length();
		FileInputStream inFile = new FileInputStream(fileName);
		byte[] fileInBytes = new byte[numOfBytes];
		inFile.read(fileInBytes);
		outToClient.writeBytes("HTTP/1.0 200 Document Follows\r\n");
		if(fileName.endsWith(".html")) 
			outToClient.writeBytes("Content-Type: text/html\r\n");
		if(fileName.endsWith(".jpg")) 
			outToClient.writeBytes("Content-Type: image/jpeg\r\n");
		if(fileName.endsWith(".gif")) 
			outToClient.writeBytes("Content-Type: image/gif\r\n");
		if(fileName.endsWith(".png")) 
			outToClient.writeBytes("Content-Type: image/png\r\n");
		
		outToClient.writeBytes("Content-Length:" + numOfBytes+"\r\n");
		outToClient.writeBytes("\r\n");
		outToClient.write(fileInBytes,0,numOfBytes);
		//connectionSocket.close();
		}
		else System.out.println("Bad Request Message!!!");
		}
	}
}