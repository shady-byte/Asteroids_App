package com.example.asteroidapp.dataBase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroids_data_table")
data class AsteroidData(
    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "asteroid_name_column")
    val asteroidName: String,

    @ColumnInfo(name = "absolute_magnitude_column")
    val absoluteMagnitude: Double,

    @ColumnInfo(name = "is_potentially_hazardous_column")
    val isHazardous: Boolean,

    @ColumnInfo(name = "estimated_diameter_column")
    val estimatedDiameter: Double,

    @ColumnInfo(name = "relative_velocity_column")
    val relativeVelocity: Double,

    @ColumnInfo(name = "distance_from_earth_column")
    val distanceFromEarth: Double,

    @ColumnInfo(name = "close_approach_date_column")
    val closeApproachDate: String,
): Parcelable {
    val checkHazardous
        get() = isHazardous
}

