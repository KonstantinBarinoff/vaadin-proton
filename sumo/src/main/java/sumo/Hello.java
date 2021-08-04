package sumo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    
    @GetMapping("hello")    
    public String hello() {
	return 	"People are strange when you\'re a stranger</br>" +
		"Faces look ugly when you're alone</br>" +
		"Women seem wicked when you're unwanted</br>" +
		"Streets are uneven when you're down...";
    }
    

}
