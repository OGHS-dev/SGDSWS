package com.oghs.sgdsws;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author oghs
 */
@SpringBootApplication
public class SGDSWSApplication /*implements CommandLineRunner*/ {

    // @Autowired
    // private BCryptPasswordEncoder passEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SGDSWSApplication.class, args);
    }

    // @Override
    // public void run(String... args) throws Exception {

    //     System.out.println("ADMIN: admin -> " + passEncoder.encode("admin"));
    //     System.out.println("SUPERVISOR1: supervisor1 -> " + passEncoder.encode("supervisor1"));
    //     System.out.println("SUPERVISOR2: supervisor2 -> " + passEncoder.encode("supervisor2"));
    //     System.out.println("AUDITOR1: auditor1 -> " + passEncoder.encode("auditor1"));
    //     System.out.println("AUDITOR2: auditor2 -> " + passEncoder.encode("auditor2"));
    //     System.out.println("REVISOR1: revisor1 -> " + passEncoder.encode("revisor1"));
    //     System.out.println("REVISOR2: revisor2 -> " + passEncoder.encode("revisor2"));
    //     System.out.println("DESARROLLO1: desarrollo1 -> " + passEncoder.encode("desarrollo1"));
    //     System.out.println("DESARROLLO2: desarrollo2 -> " + passEncoder.encode("desarrollo2"));
    //     System.out.println("DESARROLLO3: desarrollo3 -> " + passEncoder.encode("desarrollo3"));
    //     System.out.println("DESARROLLO4: desarrollo4 -> " + passEncoder.encode("desarrollo4"));

    // }

}
