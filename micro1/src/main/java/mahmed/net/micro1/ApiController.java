package mahmed.net.micro1;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

 
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;




@RestController
@RequestMapping("/composite")
public class ApiController {
	
	private final RestTemplate restTemplate;
	private static WebClient webClient;
	private static Logger LOG = LoggerFactory.getLogger(ApiController.class);
	
	
	@Autowired
	public ApiController(RestTemplate restTemplate, WebClient.Builder builder) {
		super();
		this.restTemplate = restTemplate;
		this.webClient = builder.build();
	}



	@GetMapping("/")
	public List<String> getComposite()
	{
		
		//return List.of("hello","world");
		
		try {
			String url1 = "http://localhost:9001/ints/";
		 
			List<Integer> ints = restTemplate
		        .exchange(url1, HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {})
		        .getBody();
		      
		      String url2 = "http://localhost:9002/strings/";
			 
		      List<String> strings = restTemplate
		    .exchange(url2, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {})
		    .getBody();	   
		  
			   strings.addAll( ints.stream()
			  .map(i-> i.toString())
			  .collect(Collectors.toList())
			  );
		  
		  return strings;
		
		
		    } catch (Exception ex) {
		      LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
		      return new ArrayList<String>();
		    }
		  
	
	}
	
	
	
	

	@GetMapping("/stream/")
	public Flux<Integer> getSingleStream()
	{
		
			String url1 = "http://localhost:9001/ints/stream/";
		
		    LOG.debug("Will call the getRecommendations API on URL: {}", url1);

		    // Return an empty result if something goes wrong to make it possible for the composite service to return partial responses
		    return webClient.get().uri(url1).retrieve().bodyToFlux(Integer.class).log(LOG.getName(), Level.FINE);
		  	
	}
	@GetMapping("/stream-combined/")
	public Flux<Tuple2<Integer, String>> getCompositeStream()
	{
		
			String url1 = "http://localhost:9001/ints/stream/";
		
		    LOG.debug("Will call the getRecommendations API on URL: {}", url1);
		    
		    String url2 = "http://localhost:9002/strings/stream/";

		    // Return an empty result if something goes wrong to make it possible for the composite service to return partial responses
		    Flux<Integer> f1 =  webClient.
		    		get().uri(url1)
		    		.retrieve()
		    		.bodyToFlux(Integer.class)
		    		.log(LOG.getName(), Level.FINE);

		    
		    Flux<String> f2 =  webClient.
		    		get()
		    		.uri(url2)
		    		.retrieve()
		    		.bodyToFlux(String.class)
		    		.log(LOG.getName(), Level.FINE);
		    
		    
		   // Flux<CompositeData> zipped =
		    	return 	Flux.zip(f1, f2);
		    		
		   // .log().map(x -> {
		    //	CompositeData result = new CompositeData(x.getT1(),x.getT2());
		     //   System.out.println("Zip output "+result);
		      //  return result;
		    //});
		
		    
		  //  return Mono.zip(
		    	//	(values-> getCompData(values[0], values[1])), 
		    	//	f1,
		    	//	f2);
		    		
		    	//return 	Flux.zip(f1,f2);
		    
		    
		  	
	}
	
	private CompositeData getCompData(Integer id, String name)
	{
		return new CompositeData(id, name);
	}
	
	@GetMapping("/stream-combined-ints/")
	public Flux<Tuple2<Integer, Integer>> getCompositeStreamInts()
	{
		
			String url1 = "http://localhost:9001/ints/stream/";
			String url2 = "http://localhost:9001/ints/stream2/";
		

		    // Return an empty result if something goes wrong to make it possible for the composite service to return partial responses
		    Flux<Integer> f1 =  webClient.
		    		get().uri(url1)
		    		.retrieve()
		    		.bodyToFlux(Integer.class)
		    		.log(LOG.getName(), Level.FINE);

		    
		    Flux<Integer> f2 =  webClient.
		    		get()
		    		.uri(url2)
		    		.retrieve()
		    		.bodyToFlux(Integer.class)
		    		.log(LOG.getName(), Level.FINE);
		    
		    
		   return Flux.zip(f1, f2);
		    
		  
		    
		    
		  	
	}
	
	

}
