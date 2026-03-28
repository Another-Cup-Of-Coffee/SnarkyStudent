package net.anothercupofcoffee.snarkystudent.snarkystudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class SnarkystudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnarkystudentApplication.class, args);
	}
@GetMapping("/api/data")
    public String getData(@RequestParam(value = "course", defaultValue = "Unknown") String course) {
        String selected = course.toLowerCase();

        if (selected.contains("1093") || selected.contains("precalculus")) {
            return "MAT 1093: Precalculus. The gatekeeper. Master the unit circle or prepare to retake this in the summer.";
        } 
        else if (selected.contains("1214") || selected.contains("calculus i")) {
            return "MAT 1214: Calculus I. Welcome to the big leagues. Watch out for the 'Chain Rule'.";
        } 
        else {
            return "Course not in current scope. System scanning limited to Precalc and Calc I.";
        }
    }
}


