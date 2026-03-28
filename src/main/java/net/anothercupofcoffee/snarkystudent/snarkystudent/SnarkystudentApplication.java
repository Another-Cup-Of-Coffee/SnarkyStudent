package net.anothercupofcoffee.snarkystudent.snarkystudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
//import net.anothercupofcoffee.snarkystudent.snarkystudent.examples.MinimalWebDriverExample;

=======
>>>>>>> 0b1a4ccddbe7edf63ada0de3632153ce82530a62
@SpringBootApplication
public class SnarkystudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnarkystudentApplication.class, args);
	}
@GetMapping("/api/data")
    public String getData(@RequestParam(value = "course", defaultValue = "Unknown") String course) {
        
        // Use lowercase for everything to avoid bugs
        String selected = course.toLowerCase();

        if (selected.contains("precalculus")) {
            return "MAT 1093: Precalculus. The gatekeeper. Master the unit circle or prepare to retake this in the summer.";
        } 
        else if (selected.contains("calculus i")) { 
            return "MAT 1214: Calculus I. Welcome to the big leagues. Watch out for the 'Chain Rule'.";
        } 
        else if (selected.contains("calculus ii")) {
            return "MAT 1224: Calculus II. The GPA destroyer. Integration by Parts will be your new best friend.";
        } 
        else if (selected.contains("calculus iii")) {
            return "MAT 2214: Calculus III. It's just Calc I in 3D. Start practicing those saddle points.";
        } 
        else {
            return "Snarky System Status: Connected. Scanning for " + course + "... No data found yet.";
        }
    }
}


