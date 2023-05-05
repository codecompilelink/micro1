package mahmed.net.microstrings;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/strings")
public class ApiController {
	
	@GetMapping("/")
	public List<String> strings() {
		
		return List.of("Hi","There");
		
	}
	
	
	@GetMapping("/stream/")
	public Flux<String> getStringStream()
	{		 		
		 List<String> cities = new ArrayList<>(Arrays.asList("Lon\n don", "Paris", "Rome", "Amsterdam"));
		
		  Flux<String> flux = Flux.fromIterable(cities);
		  //https://stackoverflow.com/questions/58328386/why-is-a-fluxstring-being-collapsed-into-a-single-string-when-returned-via-ser
		  return flux.delayElements(Duration.ofSeconds(5)).map(v->  v+"\n")		 
		  .doOnNext(i -> System.out.print("pushing " + i));
		    
	}
	

}
