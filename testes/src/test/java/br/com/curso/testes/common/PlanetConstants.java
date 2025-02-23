package br.com.curso.testes.common;

import br.com.curso.testes.domain.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("Tatooine", "arid", "desert");
    public static final Planet INVALID_PLANET = new Planet("", "", "");

}
