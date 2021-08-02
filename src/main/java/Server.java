import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.RandomStringUtils;

public class Server extends Thread{
    public ServerSocket server;
    public BufferedReader br;
    public Server() {
        try {
            server = new ServerSocket(15925);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
        Logger.info("服务器已启动", ServerMain.console);
    }
    @Override
    public void run() {
        while (this.isAlive()) {
            try {
                Socket client = server.accept();
                br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String line = br.readLine();
                PrintStream ps = new PrintStream(client.getOutputStream());
                Gson gson = new Gson();
                JsonObject text = gson.fromJson(line, JsonObject.class);
                String type = text.get("type").getAsString();
                switch (type) {
                    case "login":
                        try {
                            if (SQL.login(text.get("id").getAsString(),text.get("passwd").getAsString())) {
                            ps.println(createJWT(text.get("id").getAsString()+text.get("passwd").getAsString(),"Greenball233","www.phiplayor.org"));
                            } else {
                                ps.println("loginError");
                            }
                            ps.flush();
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "reg":
                        Random r = new Random();
                        String salt = RandomStringUtils.random(r.nextInt(15)+1,"AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789");
                        break;
                    default:
                        ps.println("qwq");
                        ps.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String createJWT(String id, String issuer, String subject) {

//The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

//We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("XiaozhouTATXiaozhouTATXiaozhouTATXiaozhouTATXiaozhouTAT");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

            long expMillis = nowMillis + 4320000000L;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);

//Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


}
