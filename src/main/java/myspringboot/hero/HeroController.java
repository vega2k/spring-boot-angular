package myspringboot.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/heros")
public class HeroController {

	private List<Hero> heros = new ArrayList<>();	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	HeroController() {
		buildHeros();
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Hero> getHeros() {
		return this.heros;
	}

	@RequestMapping(value = "/name/{name}",method = RequestMethod.GET)
	public List<Hero> searchHeros(@PathVariable String name) {
		logger.debug(" 검색 " + name);
		return this.heros.stream().filter(hero -> hero.getName().contains(name)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Hero getHero(@PathVariable("id") Long id) {
		return this.heros.stream().filter(hero -> hero.getId() == id).findFirst().orElse(null);
	}
	
	

	@RequestMapping(method = RequestMethod.POST)
	public Hero saveHero(@RequestBody Hero hero) {
		logger.debug(" 등록  요청" + hero);
		Long nextId = 0L;
		if (this.heros.size() != 0) {
			Hero lastHero = this.heros.stream().skip(this.heros.size() - 1).findFirst().orElse(null);
			nextId = lastHero.getId() + 1;
		}

		hero.setId(nextId);
		this.heros.add(hero);
		logger.debug(" 등록  처리 " + hero);
		return hero;

	}

	@RequestMapping(method = RequestMethod.PUT)
	public Hero updateHero(@RequestBody Hero hero) {
		logger.debug("업데이트 " + hero);
		Hero modifiedHero = this.heros.stream().filter(u -> u.getId() == hero.getId()).findFirst().orElse(null);
		modifiedHero.setName(hero.getName());
		return modifiedHero;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean deleteHero(@PathVariable Long id) {
		logger.debug(" 삭제  " + id);
		Hero deleteHero = this.heros.stream().filter(Hero -> Hero.getId() == id).findFirst().orElse(null);
		if (deleteHero != null) {
			this.heros.remove(deleteHero);
			return true;
		} else  {
			return false;
		}
	}

	void buildHeros() {
		Hero hero1 = new Hero(11L,"Mr. Nice");
		Hero hero2 = new Hero(12L,"Narco");
		Hero hero3 = new Hero(13L,"Bombasto");
		Hero hero4 = new Hero(14L,"Celeritas");
		Hero hero5 = new Hero(15L,"Magneta");
		Hero hero6 = new Hero(16L,"RubberMan");
		Hero hero7 = new Hero(16L,"Dynama");
		Hero hero8 = new Hero(18L,"Dr IQ");
		Hero hero9 = new Hero(19L,"Magma");
		Hero hero10 = new Hero(20L,"Tornado");

		heros.add(hero1);
		heros.add(hero2);
		heros.add(hero3);
		heros.add(hero4);
		heros.add(hero5);
		heros.add(hero6);
		heros.add(hero7);
		heros.add(hero8);
		heros.add(hero9);
		heros.add(hero10);
		
	}
}