package com.example.songguessinggame

import java.io.Serializable

class Song : Serializable {
    var songID : Int = 0
    var songTitle : String = ""
    var songArtist : String = ""
    var songLyric : String = ""
    var isCollected : Int = 0
    var isClassic : Int = 1
    var mapLatitude : Double =  0.64
    var mapLongitude : Double =  0.46
   // var mapLongitudeNew : Double =  0.43
}