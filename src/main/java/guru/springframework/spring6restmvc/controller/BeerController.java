package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    @Autowired
    private final BeerService beerService;
    @PatchMapping("{beerId}")
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId,@RequestBody BeerDTO beer){

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){

       if(! beerService.deleteById(beerId)){
           throw new NotFoundException();
       };

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("{beerId}")
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId,@RequestBody BeerDTO beer){

        if(beerService.updateBeerById(beerId, beer).isEmpty()){
            throw new NotFoundException();
        };

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody BeerDTO beer){

        BeerDTO savedBeer =beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString() );

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id){

        log.debug("Get beer by id in controller");

        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

}
