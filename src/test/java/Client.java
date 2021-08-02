import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",15925);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println("{\"type\":\"login\",\"id\":\"Greenball233\",\"passwd\":\"aaaa\"}");
            ps.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = br.readLine();
            System.out.println(line);
            parseJWT(line);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void parseJWT(String jwt) {
//This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("XiaozhouTATXiaozhouTATXiaozhouTATXiaozhouTATXiaozhouTAT"))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }
}
