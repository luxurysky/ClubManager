package luxurysky.clubmanager.model

import io.realm.Realm

object DataHelper {
    fun findPlayer(realm: Realm, id: String): Player? {
        return realm.where(Player::class.java)
                .equalTo(Player.FIELD_ID, id)
                .findFirst()
    }

    fun findClub(realm: Realm, id: String): Club? {
        return realm.where(Club::class.java)
                .equalTo(Club.FIELD_ID, id)
                .findFirst()
    }

    fun deletePlayer(realm: Realm, id: String) {
        realm.executeTransactionAsync {
            it.where(Player::class.java)
                    .equalTo(Player.FIELD_ID, id)
                    .findFirst()
                    ?.deleteFromRealm()
        }
    }
}