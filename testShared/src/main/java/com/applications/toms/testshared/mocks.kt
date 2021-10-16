package com.applications.toms.testshared

import com.applications.toms.domain.MyCharacter

val mockCharacter = MyCharacter(
    id= 0,
    name= "Nombre del personaje",
    description= "Descripcion del personaje",
    thumbnail= "",
    comics= 1,
    events= 1,
    series= 1,
    stories= 1
)

val listOfMocks = listOf(
    mockCharacter.copy(id = 0, name = "Primero"),
    mockCharacter.copy(id = 1, name = "Segundo"),
    mockCharacter.copy(id = 2, name = "Tercero"),
    mockCharacter.copy(id = 3, name = "Cuarto")
)