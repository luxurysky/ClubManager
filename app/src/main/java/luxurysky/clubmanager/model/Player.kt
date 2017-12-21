package luxurysky.clubmanager.model

import io.realm.RealmObject

/**
 * Created by HWANGJIN on 2017-12-21.
 */
open class Player : RealmObject() {
    var name: String = ""
    var position: String = ""
    var squadNumber: Int = 0
    var height: Int = 0
    var weight: Int = 0
    var photoUrl: String = ""
}