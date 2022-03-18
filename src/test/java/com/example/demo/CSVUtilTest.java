package com.example.demo;

import com.example.demo.models.Player;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class CSVUtilTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void stream_filtrarJugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<Player>> listFilter = list.parallelStream()
                .filter(player -> player.age >= 35)
                .map(player -> {
                    player.name = player.name.toUpperCase(Locale.ROOT);
                    return player;
                })
                .flatMap(playerA -> list.parallelStream()
                        .filter(playerB -> playerA.club.equals(playerB.club))
                )
                .distinct()
                .collect(Collectors.groupingBy(Player::getClub));

        assert listFilter.size() == 322;
    }


    @Test
    void reactive_filtrarJugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<Player>>> listFilter = listFlux
                .filter(player -> player.age >= 35)
                .map(player -> {
                    player.name = player.name.toUpperCase(Locale.ROOT);
                    return player;
                })
                .buffer(100)
                .flatMap(playerA -> listFlux
                         .filter(playerB -> playerA.stream()
                                 .anyMatch(a ->  a.club.equals(playerB.club)))
                )
                .distinct()
                .collectMultimap(Player::getClub);

        assert listFilter.block().size() == 322;
    }

    @Test
    void reactive_filtrarJugadoresMayoresA34EnClub(){

        String club = "FC Tokyo";
        List<Player> jugadores = CsvUtilFile.getPlayers();
        Flux<Player> listFlux = Flux.fromStream(jugadores.parallelStream()).cache();
        Mono<Map<Integer, Collection<Player>>> filtrado = listFlux
                .filter(player -> player.age > 34 && player.club.equals(club))
                .distinct()
                .collectMultimap(Player::getAge);

        System.out.println(filtrado.block().size());
        filtrado.block().forEach((key, values) -> values.forEach(p -> System.out.println(p)));
        assert filtrado.block().size() == 1;
    }

    @Test
    void reactive_filtrarJugadoresNacionalidadRanking(){
        List<Player> jugadores = CsvUtilFile.getPlayers();
        Flux<Player> fluxList = Flux.fromStream(jugadores.parallelStream()).cache();
        Mono<Map<String, Collection<Player>>> ordenNac = fluxList
                .sort((p1, p2) -> {
                    return p1.getWinners() > p2.getWinners() ?
                            p1.getWinners() : p2.getWinners();
                })
                .distinct()
                .collectMultimap(Player::getNational);

        ordenNac.block().forEach((key, values) -> System.out.println(key));

        assert ordenNac.block().size() == 164;
    }


}
