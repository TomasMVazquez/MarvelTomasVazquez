package com.toms.applications.marveltomasvazquez

import com.toms.applications.marveltomasvazquez.data.database.model.*

internal val mockCharacter = CharacterDatabaseItem(
    id= 0,
    name= "Nombre del personaje",
    description= "Descripcion del personaje",
    thumbnail= ThumbnailDatabase("",""),
    comics= ComicsDatabase(0,"", emptyList(),0),
    events= EventsDatabase(0,"", emptyList(),0),
    series= SeriesDatabase(0,"", emptyList(),0),
    stories= StoriesDatabase(0,"", emptyList(),0),
    modified= "",
    resourceURI= "",
    urls= UrlsDatabase(emptyList())
)