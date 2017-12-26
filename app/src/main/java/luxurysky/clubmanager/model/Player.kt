package luxurysky.clubmanager.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by HWANGJIN on 2017-12-21.
 */
open class Player : RealmObject() {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var name: String = ""
    var position: String = ""
    var squadNumber: Int = 0
    var height: Int = 0
    var weight: Int = 0
    var photoUrl: String = ""
}