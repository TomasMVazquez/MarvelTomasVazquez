package com.toms.applications.marveltomasvazquez.data

import com.applications.toms.domain.*
import com.toms.applications.marveltomasvazquez.data.database.model.*

fun CharacterDatabaseItem.asDomainModel(): Result{
    return Result(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.asDomainModel(),
        comics = comics.asDomainModel(),
        events = events.asDomainModel(),
        series = series.asDomainModel(),
        stories = stories.asDomainModel(),
        modified = modified,
        resourceURI = resourceURI,
        urls = urls.asDomainModel()
    )
}

fun Result.asDatabaseModel(): CharacterDatabaseItem{
    return CharacterDatabaseItem(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.asDatabaseModel(),
        comics = comics.asDatabaseModel(),
        events = events.asDatabaseModel(),
        series = series.asDatabaseModel(),
        stories = stories.asDatabaseModel(),
        modified = modified,
        resourceURI = resourceURI,
        urls = urls.asDatabaseModel()
    )
}

fun ComicsDatabase.asDomainModel(): Comics {
    return Comics(
        available = available,
        collectionURI = collectionURI,
        items = items.map { it.asDomainModel() },
        returned = returned
    )
}

fun ComicItemDatabase.asDomainModel(): Item {
    return Item(
        name = name,
        resourceURI = resourceURI
    )
}

fun Comics.asDatabaseModel(): ComicsDatabase {
    return ComicsDatabase(
        available = available,
        collectionURI = collectionURI,
        items = items.map { it.asDatabaseModel() },
        returned = returned
    )
}

fun Item.asDatabaseModel(): ComicItemDatabase {
    return ComicItemDatabase(
        name = name,
        resourceURI = resourceURI
    )
}

fun EventsDatabase.asDomainModel(): Events {
    return Events(
        available,
        collectionURI,
        items.map { it.asDomainModel() },
        returned
    )
}

fun EventItemDatabase.asDomainModel(): ItemX {
    return ItemX(
        name = name,
        resourceURI = resourceURI
    )
}

fun Events.asDatabaseModel(): EventsDatabase {
    return EventsDatabase(
        available,
        collectionURI,
        items.map { it.asDatabaseModel() },
        returned
    )
}

fun ItemX.asDatabaseModel(): EventItemDatabase {
    return EventItemDatabase(
        name = name,
        resourceURI = resourceURI
    )
}

fun SeriesDatabase.asDomainModel(): Series {
    return Series(
        available,
        collectionURI,
        items.map { it.asDomainModel() },
        returned
    )
}

fun SerieItemDatabase.asDomainModel(): ItemXX {
    return ItemXX(
        name = name,
        resourceURI = resourceURI
    )
}

fun Series.asDatabaseModel(): SeriesDatabase {
    return SeriesDatabase(
        available,
        collectionURI,
        items.map { it.asDatabaseModel() },
        returned
    )
}

fun ItemXX.asDatabaseModel(): SerieItemDatabase {
    return SerieItemDatabase(
        name = name,
        resourceURI = resourceURI
    )
}

fun StoriesDatabase.asDomainModel(): Stories {
    return Stories(
        available,
        collectionURI,
        items.map { it.asDomainModel() },
        returned
    )
}

fun Stories.asDatabaseModel(): StoriesDatabase {
    return StoriesDatabase(
        available,
        collectionURI,
        items.map { it.asDatabaseModel() },
        returned
    )
}

fun StoryItemDatabase.asDomainModel(): ItemXXX {
    return ItemXXX(
        name = name,
        resourceURI = resourceURI,
        type = type
    )
}

fun ItemXXX.asDatabaseModel(): StoryItemDatabase {
    return StoryItemDatabase(
        name = name,
        resourceURI = resourceURI,
        type = type
    )
}

fun ThumbnailDatabase.asDomainModel(): Thumbnail {
    return Thumbnail(
        extension = extension,
        path = path
    )
}

fun Thumbnail.asDatabaseModel(): ThumbnailDatabase {
    return ThumbnailDatabase(
        extension = extension,
        path = path
    )
}

fun UrlsDatabase.asDomainModel(): List<Url>{
    return items.map{
        Url(
            type = it.type,
            url = it.url
        )
    }
}

fun List<Url>.asDatabaseModel(): UrlsDatabase{
    return UrlsDatabase(
        items = map {
            UrlDatabase(
                it.type,
                it.url
            )
        }
    )
}
