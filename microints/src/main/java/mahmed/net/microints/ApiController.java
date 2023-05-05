package mahmed.net.microints;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ints")
public class ApiController {
	
	@GetMapping("/")
	public List<Integer> getInts()
	{
		sleep(5000);
		return List.of(1,2,3,4,5);
	}
	
	@GetMapping("/stream/")
	public Flux<Integer> getIntsStream()
	{
		return Flux.range(0, 10000)
		.delayElements(Duration.ofSeconds(1))
		.doOnNext(i -> System.out.print("int pushing " + i));
		
	}
	@GetMapping("/stream2/")
	public Flux<Integer> getIntsStream2()
	{
		return Flux.range(0, 10000)
		.delayElements(Duration.ofSeconds(5))
		.doOnNext(i -> System.out.print("int pushing " + i));
		
	}
	
	
	private void sleep(long milisec)
	{
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
