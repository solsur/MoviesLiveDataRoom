package com.cals.movies.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(tableName = "tvShow_entities")
data class TvShowEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    var tvshow_id: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "release_date")
    var release_date: String,

    @ColumnInfo(name = "img_poster")
    var img_poster: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
    ): Parcelable